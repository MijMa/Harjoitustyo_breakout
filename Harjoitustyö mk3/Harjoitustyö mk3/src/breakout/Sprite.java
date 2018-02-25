package breakout;
	
import java.awt.Image;
import java.awt.Rectangle;
/**
 * @author Zetcode zetcode.com/tutorials/javagamestutorial/breakout/
 * @author Mio Mattila
 * @author Samuli Suutari
 * @author Paula Heino
 */
//Alkuperaisen koodin luokka
public class Sprite {

    protected int x;
    protected int y;
    //i_width ja i_height saavat spritea implementoivissa luokissa arvoikseen
    //kuvan todellisen leveyden ja korkeuden
    protected int i_width;
    protected int i_heigth;
    protected Image image;

    /**
     * Asettaa i_width:n ja i_height:n arvot kuvan todellisen leveyden ja korkeuden mukaan.
     */
    public void asetaKuvanKoko(){
        i_width = image.getWidth(null);
        i_heigth = image.getHeight(null);
    }
    
    /**
     * Asettaa x-koordinaatin
     * @param x x-koordinaatin arvo
     */
    public void setX(int x) {
        this.x = x;
    }
    /**
     * Palauttaa x-koordinaatin
     * @return x-koordinaatin arvo
     */
    public int getX() {
        return x;
    }
    /**
     * Asettaa y-koordinaatin
     * @param y y-koordinaatin arvo
     */
    public void setY(int y) {
        this.y = y;
    }
    /**
     * Palauttaa y-koordinaatin
     * @return y-koordinaatin arvo
     */
    public int getY() {
        return y;
    }
    /**
     * Palauttaa kuvan leveyden, jos se on asetettu
     * @return kuvan leveys
     */
    public int getWidth() {
        return i_width;
    }
     /**
     * Palauttaa kuvan korkeuden, jos se on asetettu
     * @return kuvan korkeus
     */
    public int getHeight() {
        return i_heigth;
    }
    /**
     * Palauttaa imageen tallennetun kuvan
     * @return kuva
     */
    public Image getImage() {
        return image;
    }
    /**
     * Luo imagen kokoisen nelion koordinaatteihin (x, y) 
     * @return imagen kokoinen nelio
     */
    Rectangle getRect() {
        return new Rectangle(x, y,
                image.getWidth(null), image.getHeight(null));
    }
}
