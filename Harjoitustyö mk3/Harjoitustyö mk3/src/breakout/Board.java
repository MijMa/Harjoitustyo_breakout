package breakout;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import breakout.Commons;
/**
 * @author Zetcode zetcode.com/tutorials/javagamestutorial/breakout/
 * @author Mio Mattila
 * @author Samuli Suutari
 * @author Paula Heino
 */
//Alkuperaisen koodin luokka
public class Board extends JPanel implements Commons {

	/*ALUSTUKSET*/
    private Timer timer;
    private String message = "Game Over";
    private Image Background;
    private Powerup powerup;
    private Hp hp1;
    private Hp hp2;
    /*
     *  ArrayListit poweruppeja ja palloja varten, niin etta niita voisi olla enemman kyin yksi mutta
     *  myos niin etta niita voi olla n kappaletta.
     */
    public ArrayList<Powerup> poweruplista = new ArrayList<>();
    public ArrayList<Ball> pallolista = new ArrayList<>();
    public int pallocounter = 1;
    public int nopeus = 0;
 	public int paddlecounter = 0;
    private Paddle paddle;
    private Brick bricks[];
    private boolean ingame = true;
    public int elamat = 3;
    public int Pisteet;
    public String Hiscore = "";
    
    /*KONSTRUKTORI*/
    public Board() {
    	// nuolinappainten kayttoon tarvittava kuulustelija, tarkastaa tapahtuman jokaisella kellonlyomalla
        addKeyListener(new TAdapter());
        setFocusable(true);
        Background = new ImageIcon(getClass().getResource("Kuvat/sky.jpg")).getImage();
        bricks = new Brick[N_OF_BRICKS];
        setDoubleBuffered(true);
        timer = new Timer();
        timer.scheduleAtFixedRate(new ScheduleTask(), DELAY, PERIOD);
       // initBoard();
    }

    
    @Override
    public void addNotify() {
    //Makes this Component displayable by connecting it to a native screen resource.
    //This method is called internally by the toolkit and should not be called directly by programs. 
        super.addNotify();
        gameInit();
    }
    
    /**
     * Lisaa pelialustalle pallon, paddlen, sydamet seka tiilet.
     */
    private void gameInit() {
        /*
         * Alustetaan muuttujat jotka halutaan pelin alkaessa,
         *  pallomuuttujaa ei koskaan nay kentalla silla silta puuttuu collision, kuvanhaku ja liikekomennot
         *  mutta pallolistalla sen sijaan on.
         */
        Ball ball = new Ball(1,1);
        //ekan pallon suunta on alas
        ball.setXDir(0);
        ball.setYDir(1);
        pallolista.add(ball);
        paddle = new Paddle();
        /*ELAMIEN SIJAINTI (x,y) KOORDINAATISSA*/
        /*Samuli:1.Vaihdoin hp1 ja hp2 koordinaatit, koska jarkevampi omasta mielesta elamien tyhjentya oikealta vasemmalle.
         * 		 2.Laiton System.out.println(pallolista) kommentiin, koska tata tulostusta pelin kannalta ei tarvitse.*/
        hp1 = new Hp(20,610);
        hp2 = new Hp(60,610);
        //System.out.println(pallolista);

        /*
         *  luodaan brick-oliot ylemmassa for loopissa on rivien maara ja alemmassa palikoiden maara rivissa,
         *  rivien palikoille annetaan samalla koordinaatit niiden koon mukaan.
         */
        int k = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 9; j++) {
                bricks[k] = new Brick(j * 100 + 1, i * 40 + 1);
                k++;
            }
        }
    }

    /**
     * Hallinnoi drawObjects- ja gameFinished- metodeja. 
     * <p>
     * Pelin ollessa kaynnissa kutsuu drawObjects-metodia, pelin paattyessa gameFinishedia.
     * @param g Grafiikat
     */
    // kuvien maalaamiseen kaytettava metodi pelin alkaessa
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        if (ingame) {
            
            drawObjects(g2d);
        } else {

            gameFinished(g2d);
        }

        Toolkit.getDefaultToolkit().sync();
    }
    
    /**
     * Piirtaa objektit alustaan.
     * <p>
     * Piirtaa taustakuvan, pallot, powerupit, paddlen, tiilet, hiscorestringin, pisteet ja sydamet.
     * @param g2d grafiikat paintComponentista
     */
    /*GRAAFINEN LIITTYMA*/
    /*HUOM! Graphics/Graphics2D metodit lisaavat kuvat ja piirrettavat asiat jarjestyksessa. Taman nakee, kun eri kohdat menevat paallekain.*/	
    private void drawObjects(Graphics2D g2d) {
        
        /*TAUSTAVARI*/
        //	g2d.setColor(Color.BLACK);
        //	g2d.fillRect(0, 0, Commons.WIDTH , Commons.HEIGTH);
        /*HUOM! Tata ei tarvitse, jos on taustakuva asetettua graafiselle liittymalle.*/
    	
        /*LISAYS Samuli: TAUSTAKUVA*/
        /*KUVAN SIJOITUS*/
    	g2d.drawImage(Background, 0, 0, Background.getWidth(null), Background.getHeight(null), this);
        //Sijoittaa kuvan (x,y) koordinaateilla: drawImage(Kuva,x1 koordinaatti piste,y1 koordinaatti piste,x2 koord. piste,y2 koord. piste, ImageObserver null )
        /*HUOM! ImageObserver eli viimeinen "this" arvo voi kayttaa kuvankasittelyyn, mutta emme kayta sita tassa */
        
    	
        /*PALLOT GRAAFISELLE LIITTYMALLE*/
        g2d.setColor(Color.BLACK);
        for (int t = 0; t<pallolista.size(); t++){
            g2d.drawImage(pallolista.get(t).getImage(), pallolista.get(t).getX(), pallolista.get(t).getY(),
            pallolista.get(t).getWidth(), pallolista.get(t).getHeight(), this);
        }

        /*POWER-UPIT GRAAFISELLE LIITTYMALLE*/
        for (int i = 0; i<poweruplista.size(); i++){
            if (poweruplista.get(i) != null){
                g2d.drawImage(poweruplista.get(i).getImage(), poweruplista.get(i).getX(), poweruplista.get(i).getY(),
                        poweruplista.get(i).getWidth(), poweruplista.get(i).getHeight(), this);
            }
        }
        
        /*PADDLE GRAAFISELLE LIITTYMALLE*/
        g2d.drawImage(paddle.getImage(), paddle.getX(), paddle.getY(),
                paddle.getWidth(), paddle.getHeight(), this);
        
        /*BRICKS GRAAFISELLE LIITTYMALLE*/      
        for (int i = 0; i < N_OF_BRICKS; i++) {
            if (!bricks[i].isDestroyed()) {
                g2d.drawImage(bricks[i].getImage(), bricks[i].getX(),
                        bricks[i].getY(), bricks[i].getWidth(),
                        bricks[i].getHeight(), this);
            }
        }
        
        
        /*LISAYS Samuli:PISTEET GRAAFISELLE LIITTYMALLE*/
        g2d.setColor(new Color(255,255,255)); //Asetetaan tuleva vari (R,B,G)=(Red,Blue,Green)
        g2d.setFont(new Font("Arial", Font.BOLD, 20));//Asetetaan tietty fontti,tyyli ja koko tulevalle syotteelle
        //piirretaan aikaisempien ehtojen mukaisesti (String, X koordinaatti, Y koordinaatti) paikkaan.
        g2d.drawString("Pisteet:" + Pisteet , Commons.WIDTH-150,  Commons.HEIGTH-40);
  
        /*LISAYS Samuli:HISCORE GRAAFISELLE LIITTYMALLE*/
        if(Hiscore.equals(""))Hiscore = getHighScore();
        g2d.drawString("Hiscore:" + Hiscore , 0,  Commons.HEIGTH-40);
  
        /*SYDAMET GRAAFISELLE LIITTYMALLE*/
        g2d.drawImage(hp1.getImage(), hp1.getX(), hp1.getY(),hp1.getWidth(), hp1.getHeight(), this);
        g2d.drawImage(hp2.getImage(), hp2.getX(), hp2.getY(),hp2.getWidth(), hp2.getHeight(), this);
    }
    
    
    /**
     * Toteutuu, kun elamat loppuvat, piirtaa viestin (message).
     * @param g2d Grafiikat
     */
    /*METODI pelin lopettamiseksi, asetetaan uusi vari ja fontti pelille ja kirjoitetaan lopputeksti oli se sitten
     * havitty tai voitettu */
    private void gameFinished(Graphics2D g2d) {
        Font font = new Font("Verdana", Font.BOLD, 18);
        FontMetrics metr = this.getFontMetrics(font);
    	g2d.setColor(Color.BLACK);
        g2d.setFont(font);
        g2d.drawString(message, (Commons.WIDTH - metr.stringWidth(message)) / 2,  Commons.WIDTH / 2);
    }
    

    /**
     * @author Zetcode
     */
    //Adapteri joka huomioi nappaimiston tapahtumat ja lahettaa ne paddlelle.
    private class TAdapter extends KeyAdapter {
        @Override
        public void keyReleased(KeyEvent e) {
            paddle.keyReleased(e);
        }
        @Override
        public void keyPressed(KeyEvent e) {
            paddle.keyPressed(e);
        }
    }
    
    /**
     * @author Zetcode
     */
    //Ajastin, joka kutsuu objektien move-metodeja eli liikuttaa niita.
    //Liikuttaa palloja, poweruppeja ja paddlea jokaisella kellonlyomalla
    private class ScheduleTask extends TimerTask {
        @Override
        public void run() {
                for (int t = 0; t<pallolista.size(); t++){
                 pallolista.get(t).move();
                }
                for (int t = 0; t<poweruplista.size(); t++){
                 poweruplista.get(t).move();
                }
             // myos iskuntunnistus ja maalaus suoritetaan kellon mukaan
            paddle.move();
            checkCollision();
            repaint();
        }
    }
      
    /**
     * Pysayttaa pelin.
     */
    private void stopGame() {
        ingame = false;
        timer.cancel();
        /*LISAYS: Tama suorittaa pisteiden laskun pelin loputtua*/
        TarkistaPisteet();
    }
    
    
    /**
     * Hakee hiscoretiedot tekstitiedostosta.
     * lueScore Asetetaan FileReader:ille tiedosto hiscore.txt lukupaikaksi.
     * lukija Lukee String tai char sisallon parametrista lueHiscore, joka on tiedosto tyyppia.
     * tuloste lukija.readLine() String syote tiedostosta.
     * tuloste  Palauttaa String sisalto.
     * @return lukija.readLine() syote tiedostosta
     */ 
    //Samulin lisatty metodi
    public String getHighScore(){
        /*ALUSTUKSET*/
        FileReader lueHiscore = null;
        BufferedReader lukija = null;
        /*Kaytamme try catchia tassa, valtaakseeme virheilta*/
        try{
            /*lueHiscorelle alustetaan hiscore.txt*/
            lueHiscore = new FileReader("hiscore.txt");//MUUTETTU! VANHA:("D:/Eclipse/Workspace/breakout/hiscore.txt") NYT EI TARVITSE MAARATA MISTA TIEDOSTO LUETAAN
            /*kaytamme BufferReader apuna hiscore.txt sisallon lukemiseen, joka lisataan "String tuloste" sisalloksi*/
            lukija = new BufferedReader(lueHiscore);
            String tuloste = lukija.readLine();
            /*Tarkistamme viella, etta hiscore.txt sisaltaa oikella tavalla tallennetu String, jotta sita voi kayttaa*/
            if(tuloste.equals("")|| tuloste.equals("[^|]")){
                /*Jos ei ollut oikeassa muodossa tai tulokset puuttuivat, niin luomme naille pohjan tuleville kerroille*/
                return "Nimi: Ei Pelaajaa | Pisteet: 0";
            }
            /*Jos "String tuloste" oli kunnossa*/
            return tuloste;
        }

        catch (Exception e){
            /*Jos oli jotain vikaa, saamme silti Hiscorelle oikeassa muodossa olevan Stringin*/
            return "Nimi: Ei Pelaajaa | Pisteet: 0";
        }/*Kaytamme finally lukijan sulkemiseen, niin ei oteta kokoajan kayttajan syotett�*/
        finally{
          /*viimeinen tarkistus ettei "lukija" aiheita virheita*/
            try{
                if(lukija != null){
                 lukija.close();
                }
            } catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * Tarkistaa onko nykyinen score korkeampi kuin tallennettu hiscore ja jos on, tallentaa scoren hiscoreksi.
     * <p>
     * nimi Asetetaan String, mika ottaa syotteen nappaimistosta.
     * Hiscore Asetetaan String  muodossa ""String"+" String parametri nimi"+"String"+"String"+Integer parametri Pisteet".
     * pisteFile Asetetaan File muodossa oleva tiedoston sisalto.
     * kirjoitaScore Asetetaan FileWriter:ille tiedosto pisteFile kirjoitus paikaksi.
     * kirjoittaja Kirjoittaa String Hiscore parametrin FileWriter kirjoitaScore sijaintiin.   
     */
    /*LISATTY METODI Samulin*/
    public void TarkistaPisteet(){
        /*Talle if ehdolle on tarkeaa, etta Hiscore String on oikeassa muodossa.
        * String Hiscore palotellaan kahteen osaan missa "Pisteet: " jalkimmainen osa otetaan syotteeksi,
        * joka on  Hiscore:n pisteet. Tata verrataan nykyisiin pisteisiin.*/
        if(Pisteet > Integer.parseInt(Hiscore.split("Pisteet: ")[1])){
            /*Tama String tulostetaan ruudulle kertoen pelaajalle mita tehda.*/
            String nimi = JOptionPane.showInputDialog("Uusi Hiscore! Aseta nimi:");
            /*Lisaamme pelaajan String nimi syotteen String Hiscore:en.*/
            Hiscore = "Nimi: " + nimi + " | "+ "Pisteet: "+ Pisteet;

            /*Tama liitty String Hiscoren tallentamis paikkaan*/
            File pisteFile = new File("hiscore.txt");
            /*Tulostus mista nakee minne hiscore.txt tallentuu.*/
            System.out.println("Hiscore.txt tallennetaan:" + pisteFile.getAbsolutePath());

            /*Tarkistetaan onko tallennus paikka valmiiksi olemassa*/
            if(!pisteFile.exists()){
                /*try catch virheiden varalle*/
                try{
                    pisteFile.createNewFile();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            /*ALUSTUKSET*/
            FileWriter kirjoitaScore = null;
            BufferedWriter kirjoittaja = null;
            try{/*Tama on uuden Hiscoren laittamista varten*/
                kirjoitaScore = new FileWriter(pisteFile);
                kirjoittaja = new BufferedWriter(kirjoitaScore);
                kirjoittaja.write(this.Hiscore);
            }catch(Exception e){
            }finally{
             try{
                /*kirjoittajan sulkeminen kayton jalkeen*/
                if(kirjoittaja != null){
                    kirjoittaja.close();
                }
            }catch(Exception e){}
            }
        }
    }
    
    /**
     * Tarkistaa, osuvatko objektit toisiinsa ja suorittaa toimintoja tormayksen tapahtuessa.
     */
    private void checkCollision() {
        /*POWER-UP VUOROVAIKUTUS PADDLE:n KANSSA*/
    	/*
    	 * vaihtaa paddlen kuvaa isommanksi siihen asti kunnes suurempia kokoja ei enaa ole 
    	 * jolloin se ei enaa mitaan, ja powerup menee paddlen lapi
    	 */
        for (int t = 0; t<poweruplista.size(); t++){
            if ((poweruplista.get(t).getRect()).intersects(paddle.getRect()) && poweruplista.get(t).annaTyyppi() == 2) {
                Ball ballerinot = new Ball(1,1);
                pallolista.add(ballerinot);
                pallocounter++;
                poweruplista.remove(poweruplista.get(t));
            }
            else if ((poweruplista.get(t).getRect()).intersects(paddle.getRect()) && poweruplista.get(t).annaTyyppi() == 3 && nopeus != 2){
                nopeus++;
                timer.cancel();
                timer = new Timer();
                timer.scheduleAtFixedRate(new ScheduleTask(), 0, PERIOD-nopeus);
                poweruplista.remove(poweruplista.get(t));
            }
            else if ((poweruplista.get(t).getRect()).intersects(paddle.getRect())){
                paddlecounter++;
                paddle.vaihdaKuva(paddlecounter);
                poweruplista.remove(poweruplista.get(t));
            }
        }
        // powerup-olioiden poisto niiden poistuessa kentalta
        for (int t = 0; t<poweruplista.size(); t++){
        	if (poweruplista.get(t).getRect().getMaxY() > Commons.BOTTOM_EDGE) {
        		poweruplista.remove(t);
        	}
        }
        /*
         *  Paulalle krediitti elamista, miiolle Arraylisteista
         *  Elamien toiminta ja pallon uudelleensijoitus kun elama on mennyt,
         *  poistetaan pallo joka meni pelikentan yli, katsotaan oliko se viimeinen pallo ArrayListissa,
         *  jos se oli vahennetaan yksi elama ja vaihdetaan sen kuva tyhjaksi.
         *  Sen jalkeen tehdaan pallo ja paddle jotka alkavat uusista asemista ja lisaksi
         *  tehdaan uusi viive kellolle jotta pelaaja saisi aikaa reagoida ja resetataan kellon modifikattorit.
         *  Mikali elamat ovat loppuneet kutsutaan pelin lopetusmetodia.
         */
        for (int t = 0; t<pallolista.size(); t++){
            if (pallolista.get(t).getRect().getMaxY() > Commons.BOTTOM_EDGE) {
                pallolista.remove(t);
                if (pallolista.isEmpty()){
                	paddlecounter = 0;
                    elamat--;
                    if(elamat==2) hp2.tyhjennaSydan();
                    if(elamat==1) hp1.tyhjennaSydan();
                    Ball ball = new Ball(0,1);
                    pallolista.add(ball);
                    pallolista.get(0).resetState();
                    paddle.resetState();
                    paddle.vaihdaKuva(0);
                    timer.cancel();
                    nopeus = 0;
                    timer = new Timer();
                    timer.scheduleAtFixedRate(new ScheduleTask(), DELAY, PERIOD);
                }
                if(elamat == 0){
                    stopGame();
                }
            }
        }
        // powerup-olioiden poisto niiden poistuessa kentalta
        for (int t = 0; t<poweruplista.size(); t++){
        	if (poweruplista.get(t).getRect().getMaxY() > Commons.BOTTOM_EDGE) {
        		poweruplista.remove(t);
        	}
        }
        /*LASKURI, joka tarkistaa kuinka monta BRICKia on jaljella*/
        for (int i = 0, j = 0; i < N_OF_BRICKS; i++) {
            /*Kasvata laskuria yhdella, kun tuhoaa BRICKin.*/
        	if (bricks[i].isDestroyed()) {
                j++;
                
            }
            /*JOS KAIKKI BRICKit on saatu.*/
            if (j == N_OF_BRICKS) {
                message = "VOITIT PELIN!";
                stopGame();
            }
        }
        
        /*PALLON(BALL) JA PELAAJAN(PADDLE) VUOROVAIKUTUS*/
        for (int t = 0; t<pallolista.size(); t++){
            if ((pallolista.get(t).getRect()).intersects(paddle.getRect())) {
                /*ALUSTUKSET SIJAINNILLE OBJEKTILLE JA VUOROVAIUTUS PINNOILLE*/
                int paddleLPos = (int) paddle.getRect().getMinX();
                int ballLPos = (int) pallolista.get(t).getRect().getMinX();
                
                //Paulan lisaama. Nyt ottaa huomioon eri paddlejen leveydet
                //ja adjustaa collisionin sen mukaan.
                int osa = paddle.getWidth()/5;
                //Miinustus kymmenella on ns. offset palloa varten.
                int first = paddleLPos + osa-10;
                int second = paddleLPos + osa*2-10;
                int third = paddleLPos + osa*3-10;
                int fourth = paddleLPos + osa*4-10;
                
                /*
                int first = paddleLPos + 6;
                int second = paddleLPos + 22;
                int third = paddleLPos + 38;
                int fourth = paddleLPos + 54;
                */

                /*EHDOT PELAAJAN(PADDLE) ERI REUNOILLE, kun PALLO(BALL) ON X,Y SEN SIJAINNISSA*/
                if (ballLPos < first) {
                pallolista.get(t).setXDir(-1);
                    pallolista.get(t).setYDir(-1);
                }

                if (ballLPos >= first && ballLPos < second) {
                    pallolista.get(t).setXDir(-1);
                    pallolista.get(t).setYDir(-1 * pallolista.get(t).getYDir());
                }

                if (ballLPos >= second && ballLPos < third) {
                    pallolista.get(t).setXDir(0);
                    pallolista.get(t).setYDir(-1);
                }

                if (ballLPos >= third && ballLPos < fourth) {
                    pallolista.get(t).setXDir(1);
                    pallolista.get(t).setYDir(-1 * pallolista.get(t).getYDir());
                }

                if (ballLPos > fourth) {
                    pallolista.get(t).setXDir(1);
                    pallolista.get(t).setYDir(-1);
                }
            }
        }
        
        /*PALLON(BALL) JA TIILIEN(BRICKS) VUOROVAIKUTUS*/
        for (int u = 0; u<pallolista.size(); u++){
            for (int i = 0; i < N_OF_BRICKS; i++) {
                if ((pallolista.get(u).getRect()).intersects(bricks[i].getRect())) {
                    
                    /*ALUSTUKSET SIJAINNILLE OBJEKTILLE JA VUOROVAIKUTUSPINNOILLE*/
                    int ballLeft = (int) pallolista.get(u).getRect().getMinX();
                    int ballHeight = (int) pallolista.get(u).getRect().getHeight();
                    int ballWidth = (int) pallolista.get(u).getRect().getWidth();
                    int ballTop = (int) pallolista.get(u).getRect().getMinY();

                    Point pointRight = new Point(ballLeft + ballWidth + 1, ballTop);
                    Point pointLeft = new Point(ballLeft - 1, ballTop);
                    Point pointTop = new Point(ballLeft, ballTop - 1);
                    Point pointBottom = new Point(ballLeft, ballTop + ballHeight + 1);

                    /*TOIMINNAT, KUN VUOROVAIKUTUS TAPAHTUU*/
                    if (!bricks[i].isDestroyed()) {
                        if (bricks[i].getRect().contains(pointRight)){
                            pallolista.get(u).setXDir(-1);
                        }else if (bricks[i].getRect().contains(pointLeft)){
                            pallolista.get(u).setXDir(1);
                        }

                        if (bricks[i].getRect().contains(pointTop)) {
                            pallolista.get(u).setYDir(1);
                        }else if (bricks[i].getRect().contains(pointBottom)){
                            pallolista.get(u).setYDir(-1);
                        }
                        /*TIILEN(BRICK) OBJECKTIN POISTO*/
                        bricks[i].setDestroyed(true);
                        /*PISTEIDEN LISAYS TIILEN(BRICK) POISTETTUA*/
                        Pisteet = Pisteet + bricks[i].annaPiste();

                        Random r = new Random();
          				/*TODENNAKOISYYS POWER-UPILLE, TIILEN(BRICK) OBJEKTIN POISTON JALKEEN*/
        				/*
        				 *  Miion krediitti:
        				 *  33.33% mahdollisuus pudottaa powerup, powerup-olio luodaan ja se siirretaan suoraan
        				 *  arraylistiin samoin kuin pallo-oliotkin ja yksittaisella powerup-oliolla ei ole mitaan
        				 *  kelloon sidottuja ominaisuuksia, se saa mm. liikkumisominaisuuden vain jos se on
        				 *  arraylistissa "poweruplista".
        				 */
                        int randarvo = r.nextInt(10001);
                        if (randarvo <= 3333){
                            int t = bricks[i].annaTyyppi();
                            int xx = bricks[i].getX();
                            int yy = bricks[i].getY();
                            powerup = new Powerup(xx,yy,t);
                            powerup.getImage();
                            powerup.setXDir(0);
                            powerup.setYDir(1);
                            poweruplista.add(powerup);
                        }
                    }
                }
            }	
        }
    }
}
