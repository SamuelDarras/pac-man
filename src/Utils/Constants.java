package Utils;

public class Constants {
    public static int larg;
    public static int haut;

    public static void Init(int l, int h) {
        larg = l;
        haut = h;
    }

    public static int SCENE_WIDTH       = 600;
    public static int SCENE_HEIGHT      = 700;

    public static double WALL_HEIGHT    = 1;
    public static double WALL_WIDTH     = 1;
    public static int ITEM_DEFAULT_SIZE = 1;
    public static int FRUIT_SIZE        = 2;
    public static double PERSONNAGE_SIZE= 1.0*SCENE_WIDTH/28 * .8;
    public static double PACMAN_SPEED   = 1.0*SCENE_WIDTH/400;
    public static double GHOST_SPEED    = PACMAN_SPEED * .9;

}
