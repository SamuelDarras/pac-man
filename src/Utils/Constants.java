package Utils;

public class Constants {
    public static int larg;
    public static int haut;


    public static int SCENE_HEIGHT      = 700;
    public static int SCENE_WIDTH       = 700;

    public static double WALL_HEIGHT, WALL_WIDTH;
    public static double PERSONNAGE_WIDTH, PERSONNAGE_HEIGHT;
    public static double ITEM_DEFAULT_WIDTH, ITEM_DEFAULT_HEIGHT;

    public static double PACMAN_SPEED;
    public static double GHOST_SPEED;

    public static void Init(int l, int h) {
        larg = l;
        haut = h;

        WALL_HEIGHT     = (double) SCENE_HEIGHT / (double) haut;
        WALL_WIDTH      = (double)  SCENE_WIDTH / (double) larg;

        PERSONNAGE_WIDTH  = ((double)  SCENE_WIDTH / (double) larg) * .99;
        PERSONNAGE_HEIGHT = ((double) SCENE_HEIGHT / (double) haut) * .99;

        ITEM_DEFAULT_WIDTH  =  PERSONNAGE_WIDTH / 4;
        ITEM_DEFAULT_HEIGHT =  PERSONNAGE_HEIGHT / 4;
        PACMAN_SPEED      = 1.0*SCENE_WIDTH/400;

        GHOST_SPEED       = PACMAN_SPEED * .5;
    }

    public static int FRUIT_SIZE = 30;
    public static String[] FRUIT_NAME = {"cherry","strawberry", "orange", "apple"};
    public static int[] FRUIT_SCORE = {100,300,500,700};


}
