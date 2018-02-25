package breakout;
/**
 * @author Zetcode zetcode.com/tutorials/javagamestutorial/breakout/
 * @author Mio Mattila
 * @author Samuli Suutari
 * @author Paula Heino
 */
//Sisaltaa esim. pelilaudan koon ja objektien aloituskoordinaatit
public interface Commons {
 
    public static final int WIDTH = 910;
    public static final int HEIGTH = 740;
    public static final int BOTTOM_EDGE = 800;
    public static final int N_OF_BRICKS = 36;
    public static final int INIT_PADDLE_X = 390;
    public static final int INIT_PADDLE_Y = 650;
    public static final int INIT_BALL_X = 410;
    public static final int INIT_BALL_Y = 200;    
    public int DELAY = 1000;
    public static final int PERIOD = 3;
}