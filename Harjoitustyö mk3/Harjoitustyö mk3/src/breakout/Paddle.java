package breakout;

import java.awt.Image;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
/**
 * @author Zetcode zetcode.com/tutorials/javagamestutorial/breakout/
 * @author Mio Mattila
 * @author Samuli Suutari
 * @author Paula Heino
 */
//Alkuperaisen koodin luokka
public class Paddle extends Sprite implements Commons {
    private Image kuva1,kuva2,kuva3,kuva4,kuva5;
    private int dx;

    /*Konstruktori*/
    public Paddle() {
        kuva1 = new ImageIcon(getClass().getResource("Kuvat/paddle1pienempi.png")).getImage();
        kuva2 = new ImageIcon(getClass().getResource("Kuvat/paddle.png")).getImage();
        kuva3 = new ImageIcon(getClass().getResource("Kuvat/paddle1isompi.png")).getImage();
        kuva4 = new ImageIcon(getClass().getResource("Kuvat/paddle2isompi.png")).getImage();
        kuva5 = new ImageIcon(getClass().getResource("Kuvat/paddle3isompi.png")).getImage();
        image=kuva1;
        asetaKuvanKoko();
        resetState();
    }
    
    /**
     * Muuttaa paddlen kuvan ja alueen asetaKuvanKoko()-metodilla
     * @param i jos i on 1, 2, 3 tai 4 kuva muuttuu numeron perusteella.
     */
    public void vaihdaKuva(int i){
        if(i==0) image = kuva1;
        if(i==1) image = kuva2;
        if(i==2) image = kuva3;
        if(i==3) image = kuva4;   
        if(i==4) image = kuva5;
        asetaKuvanKoko();
    }
    
    /**
     * Liikuttaa paddlea x-akselilla. Liikkumisvali on 0 - (WIDTH miinus paddlen leveys)
     */
    public void move() {
        x += dx;
        /*VASEN RAJA*/
        if (x <= 0) {
            x = 0;}
        /*OIKEA RAJA*/
        if (x >= WIDTH - i_width) {
            x = WIDTH - i_width;}
    }

    /**
     * Vaihtaa paddlen dx-arvoa, jos KeyEvent e on vasemman tai oikean nuolinappaimen painallus.
     * @param e Nappaimen painallus
     */
    public void keyPressed(KeyEvent e) {
        //int key = e.getKeyCode();
        
        //Samuli: 1.Vaihdoin "key == KeyEvent.Nappi" suoraan e.getKeyCode(), koska nain taman parempana tapana
        //		  2.Koska vain dx arvo muutetaan, laitoin ehdon ja toiminnan samalle riville.
        if (e.getKeyCode() == KeyEvent.VK_LEFT)  dx = -2;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT)  dx = 2;
    }

    /**
     * Asettaa paddlen dx-arvoksi 0 jos KeyEvent e on vasemmasta tai oikeasta nuolinappaimesta irroitus.
     * @param e Nappaimesta irroitus
     */
    public void keyReleased(KeyEvent e) {
        //int key = e.getKeyCode();
        
      //Samuli: 1.Vaihdoin "key == KeyEvent.Nappi" suoraan e.getKeyCode(), koska nain taman parempana tapana
      // 		2.Koska vain dx arvo muutetaan, laitoin ehdon ja toiminnan samalle riville.
        if (e.getKeyCode() == KeyEvent.VK_LEFT)  dx = 0;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) dx = 0;
    }
    
    /**
     * Asettaa paddlen alkuperaisiin Commonsissa maariteltyihin koordinaatteihin.
     */
    public void resetState() {
        x = INIT_PADDLE_X;
        y = INIT_PADDLE_Y;
    }
}