package breakout;

import javax.swing.ImageIcon;
/**
 * @author Mio Mattila
 * @author Samuli Suutari
 * @author Paula Heino
 */
//Mion luoma luokka
public class Powerup extends Sprite implements Commons {

    private int xdir;
    private int ydir;
    private int tyyppi;

    public Powerup(int x, int y, int tyyppi) {
        xdir = 0;
        ydir = 1;
        this.x = x;
        this.y = y;
        this.tyyppi = tyyppi;
        ImageIcon ii = new ImageIcon(getClass().getResource("Kuvat/alustanmuutos2.gif"));
        ImageIcon ii2 = new ImageIcon(getClass().getResource("Kuvat/pallonlisays2.gif"));
        ImageIcon ii3 = new ImageIcon(getClass().getResource("Kuvat/nopeutus2.gif"));
        if (tyyppi == 1) image = ii.getImage();
        else if (tyyppi == 2) image = ii2.getImage();
        else if (tyyppi == 3) image = ii3.getImage();
        asetaKuvanKoko();
    }
    
    /**
     * Palauttaa powerupin tyypin.
     * @return tyyppi
     */
    public int annaTyyppi(){
            return this.tyyppi;
    }
    
    /**
     * Asettaa xdirin eli powerupin x-suunnan.
     * @param x Haluttu x-suunta
     */        
    public void setXDir(int x) {
       xdir = x;
    }

    /**
     * Asettaa ydirin eli powerupin y-suunnan.
     * @param y Haluttu y-suunta
     */        
    public void setYDir(int y) {
        ydir = y;
    }

    /**
     * Palauttaa ydirin eli powerupin y-suunnan
     * @return ydir Powerupin y-suunta
     */   
    public int getYDir() {
       return ydir;
    }
   
    /**
     * Palauttaa xdirin eli powerupin x-suunnan
     * @return xdir Powerupin x-suunta
     */    
    public int getXDir(){
   	return xdir;
    }
    
    /**
     * Liikuttaa poweruppia.
     * <p>
     * Suuntaan vaikuttavat x, y, xdir ja ydir.
     */   
    public void move() {

        x += xdir;
        y += ydir;

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
}
