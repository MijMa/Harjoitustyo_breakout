package breakout;

import java.awt.Image;
import javax.swing.ImageIcon;
/**
 * @author Mio Mattila
 * @author Samuli Suutari
 * @author Paula Heino
 */
//Paulan luoma luokka
public class Hp extends Sprite{
    private Image kuva2;
    
    /*Konstruktori*/
    public Hp(int x, int y){
        this.x = x;
        this.y = y;
        ImageIcon ii = new ImageIcon(getClass().getResource("Kuvat/sydan.png")); 
        image = ii.getImage();
        ImageIcon ii2 = new ImageIcon(getClass().getResource("Kuvat/sydantyhja.png"));
        kuva2 = ii2.getImage();
        asetaKuvanKoko();
    }
	
    /**
     * Asettaa x-koordinaatin.
     * @param x x-koordinaatti
     */
    public void asetaX(int x){
        this.x = x;
    }

    /**
     * Asettaa y-koordinaatin.
     * @param y y-koordinaatti
     */
    public void asetaY(int y){
        this.y = y;
    }

    /**
     * Asettaa kuvaksi tyhjan sydamen.
     */
    public void tyhjennaSydan(){
        image = kuva2;
    }
}
