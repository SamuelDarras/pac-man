package Utils;

public class Constants {
    public static int larg;
    public static int haut;

    public static double WALL_HEIGHT, WALL_WIDTH;

    public static int SCENE_HEIGHT      = 700;
    public static int SCENE_WIDTH       = 600;

    public static double PERSONNAGE_SIZE;

    public static void Init(int l, int h) {
        larg = l;
        haut = h;

        WALL_HEIGHT     = (double) SCENE_HEIGHT / (double) haut;
        WALL_WIDTH      = (double) SCENE_WIDTH  / (double) larg;

        PERSONNAGE_SIZE = ((double) SCENE_WIDTH / (double) larg) * .7;

        System.out.println(WALL_WIDTH + " ; " + WALL_HEIGHT + " ; " + PERSONNAGE_SIZE);
    }

    public static int ITEM_DEFAULT_SIZE = (int) PERSONNAGE_SIZE/4;
    public static int FRUIT_SIZE        = 2;
    public static double PACMAN_SPEED   = 1.0*SCENE_WIDTH/400;
    public static double GHOST_SPEED    = PACMAN_SPEED * .9;

}
