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
public class Brick extends Sprite {
    public int Piste = 100;
    private boolean destroyed;
    private int tyyppi;
    
    /*Konstruktori*/
    public Brick(int x, int y) {
        this.x = x;
        this.y = y;
        Random r = new Random();
        int arvo = r.nextInt(4-1)+1;

        ImageIcon ii = new ImageIcon(getClass().getResource("Kuvat/palikka.png"));
        ImageIcon ii2 = new ImageIcon(getClass().getResource("Kuvat/palikkasin.png"));
        ImageIcon ii3 = new ImageIcon(getClass().getResource("Kuvat/palikkavihr.png"));
        if (arvo == 1) {
            image = ii.getImage();
            this.tyyppi = 1;
        }
        else if (arvo == 2){
            image = ii2.getImage();
            this.tyyppi = 2;
        }
        else if (arvo == 3){
            image = ii3.getImage();
            this.tyyppi = 3;
        }
        else image = ii.getImage();
        asetaKuvanKoko();
        destroyed = false;
    }

    /**
     * Tarkistaa onko tiili rikki.
     * @return true jos tiili on rikki ja false jos tiili on ehja.
     */
    public boolean isDestroyed() {
        return destroyed;
    }
    
    /**
     * Asettaa tiilen destroyed-arvon.
     * @param val true jos tiili menee rikki ja false jos tiilesta tulee ehja.
     */
    public void setDestroyed(boolean val) {
        destroyed = val;
    }
    
    /**
     * Palauttaa tiilen tyypin, joka maarittaa varin ja powerupin.
     * @return tyyppi
     */
    public int annaTyyppi(){
		return this.tyyppi;
    }
    
    /**
     * Palauttaa brickille asetetun Piste
     * @return Piste
     */
    public int annaPiste(){;
    return this.Piste;
    }
}
