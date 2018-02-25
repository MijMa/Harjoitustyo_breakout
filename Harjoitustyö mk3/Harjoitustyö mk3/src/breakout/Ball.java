package breakout;

import java.util.Random;

import javax.swing.ImageIcon;
/**
 * @author Zetcode zetcode.com/tutorials/javagamestutorial/breakout/
 * @author Mio Mattila
 * @author Samuli Suutari
 * @author Paula Heino
 */
//Alkuperaisen koodin luokka
public class Ball extends Sprite implements Commons {

    public int xdir;
    public int ydir;

    public Ball(int xdir, int ydir) {

        this.xdir = 1;
        this.ydir = -1;

        ImageIcon ii = new ImageIcon(getClass().getResource("Kuvat/pallopien.png"));
        image = ii.getImage();

        asetaKuvanKoko();

        resetInitState();
    }
    
    /**
     * Liikuttaa palloa.
     * <p>
     * Suuntaan vaikuttavat x, y, xdir ja ydir.
     */
    public void move() {
        
        x += this.xdir;
        y += this.ydir;

        if (x == 0) {
            setXDir(1);
        }

        if (x == WIDTH - i_width) {
            setXDir(-1);
        }

        if (y == 0) {
            setYDir(1);
        }
    }
    
    /**
     * Asettaa pallon alkuperaisiin Commonsissa maariteltyihin koordinaatteihin ja asettaa suunnaksi suoraan alas.
     */
    public void resetState(){
        x = INIT_BALL_X;
        y = INIT_BALL_Y;
        setXDir(0);
        setYDir(1);
    }
    /**
     * Asettaa powerupista saataville palloille random suunnan ylospain 
     * jotta pelaaja saisi reagointiaikaa
     */
    public void resetInitState() {
        Random xdiri = new Random();
        int xx = xdiri.nextInt(3)-1;
        x = INIT_BALL_X;
        y = INIT_BALL_Y;
        this.setXDir(xx);
        this.setYDir(-1);
    }
    /**
     * Asettaa xdirin eli pallon x-suunnan.
     * @param x Haluttu x-suunta
     */    
    public void setXDir(int x) {
         this.xdir = x;
    }

    /**
     * Asettaa ydirin eli pallon y-suunnan.
     * @param y Haluttu y-suunta
     */
    public void setYDir(int y) {
         this.ydir = y;
    }
    
    /**
     * Palauttaa xdirin eli pallon x-suunnan
     * @return xdir Pallon x-suunta
     */
        public int getXDir(){
    	return this.xdir;
    }
        
    /**
     * Palauttaa ydirin eli pallon y-suunnan
     * @return ydir Pallon y-suunta
     */
    public int getYDir() {
        return this.ydir;
    }
}
