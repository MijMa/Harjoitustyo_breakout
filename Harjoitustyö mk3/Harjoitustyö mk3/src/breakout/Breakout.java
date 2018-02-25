package breakout;

import java.awt.EventQueue;
import javax.swing.JFrame;
/**
 * @author Zetcode zetcode.com/tutorials/javagamestutorial/breakout/
 * @author Mio Mattila
 * @author Samuli Suutari
 * @author Paula Heino
 */
//Alkuperaisen koodin luokka
public class Breakout extends JFrame {
    
    /*Konstruktori*/
    public Breakout() {
        
        initUI();
    }
    
    /**
     * Luo uuden Boardin ja asettaa sen koon, titlen, closeoperationin, locationin seka resize- ja visible-ehdot.
     */
    private void initUI() {

        add(new Board());
        setTitle("Breakout");
        
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(Commons.WIDTH, Commons.HEIGTH);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    /**
     * Ohjelman main-luokka. Aloittaa pelin ja asettaa sen nakyvaksi.
     * @param args 
     */
    public static void main(String[] args) {
        
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {                
                Breakout game = new Breakout();
                game.setVisible(true);                
            }
        });
        
    }
    
}
