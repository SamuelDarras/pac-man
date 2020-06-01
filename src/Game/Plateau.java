package Game;

import Entity.*;
import javafx.scene.image.Image;

import java.io.BufferedReader;
import java.io.FileReader;

import static Utils.Constants.*;

public class Plateau {
    int idxFruit = 0;
    int larg;
    int haut;

    Pacman pacman;

    Entity[] plateau;

    public Plateau(String levelPath) throws Exception {
        remplirPlateau(levelPath);
    }

    public void remplirPlateau(String levelPath) throws Exception {
        BufferedReader read = new BufferedReader(new FileReader(levelPath));
        String t = read.readLine();
        String[] lst = t.split("[. ]");

        haut = Integer.parseInt(lst[0]);
        larg = Integer.parseInt(lst[1]);

        System.out.print(haut);
        System.out.println("; " + larg);

        Init(larg, haut);

        plateau = new Entity[larg * haut];
        double x;
        double y;
        for (int i = 0; i < haut; i++) {
            t = read.readLine();
            for (int j = 0; j < larg; j++) {
                x = j * 1.0 * SCENE_WIDTH  / getLargeur();
                y = i * 1.0 * SCENE_HEIGHT / getHauteur();
                switch (t.charAt(j)) {
                    case '1':
                        plateau[larg * i + j] = new Wall(x, y);
                        break;
                    case 'p':
                        plateau[larg * i + j] = new PacGomme(x + WALL_WIDTH / 2 - PERSONNAGE_WIDTH / 4 / 2, y + WALL_HEIGHT / 2 - PERSONNAGE_HEIGHT / 4 / 2);
                        break;
                    case 's':
                        plateau[larg * i + j] = new SuperPacGomme(x + WALL_WIDTH / 2 - PERSONNAGE_WIDTH / 2 / 2, y + WALL_HEIGHT / 2 - PERSONNAGE_HEIGHT / 2 / 2);
                        break;
                    case 'I':
                        plateau[larg * i + j] = new Inky(x, y, GHOST_SPEED);
                        break;
                    case 'P':
                        plateau[larg * i + j] = new Pinky(x, y, GHOST_SPEED);
                        break;
                    case 'B':
                        plateau[larg * i + j] = new Blinky(x, y, GHOST_SPEED);
                        break;
                    case 'C':
                        plateau[larg * i + j] = new Clyde(x, y, GHOST_SPEED);
                        break;
                    case 'M':
                        pacman = new Pacman(x, y, PACMAN_SPEED);
                        plateau[larg * i + j] = pacman;
                        break;
                    default:
                        plateau[larg * i + j] = new Entity(x, y, 1, 1);
                        break;
                }

            }

        }

        for (int i = 0; i < plateau.length; i++) {
            if (plateau[i] instanceof Wall){
                ((Wall) plateau[i]).setImg(defineWallImage(
                        (i + 1 < plateau.length && (i + 1)/larg == i/larg) ? plateau[i + 1] : new Entity(0, 0, 0, 0),
                        i - larg >= 0 ? plateau[i - larg] : new Entity(0, 0, 0, 0),
                        (i - 1 >= 0 && (i - 1)/larg == i/larg) ? plateau[i - 1] : new Entity(0, 0, 0, 0),
                        i + larg < plateau.length ? plateau[i + larg] : new Entity(0, 0, 0, 0)
                ));
            }
        }


        /*int[] parsedLst = new int[lst.length];
        for (int i = 0; i < parsedLst.length; i++) {
            parsedLst[i] = Integer.parseInt(lst[i]);
        }*/

//        idxFruit = parsedLst[12] * larg + parsedLst[13];

        read.close();
    }

    public Entity[] getPlateau() {
        return plateau;
    }

    private Image defineWallImage(Entity E, Entity N, Entity W, Entity S) {
        Image img = null;


        if ((E instanceof Wall) && !(N instanceof Wall) && (W instanceof Wall) && (S instanceof Wall))
            img = new Image("img/walls/Wall-T-S.png");
        if ((E instanceof Wall) && (N instanceof Wall) && (W instanceof Wall) && !(S instanceof Wall))
            img = new Image("img/walls/Wall-T-N.png");
        if (!(E instanceof Wall) && (N instanceof Wall) && (W instanceof Wall) && (S instanceof Wall))
            img = new Image("img/walls/Wall-T-O.png");
        if ((E instanceof Wall) && (N instanceof Wall) && !(W instanceof Wall) && (S instanceof Wall))
            img = new Image("img/walls/Wall-T-E.png");

        if (!(E instanceof Wall) && !(N instanceof Wall) && (W instanceof Wall) && !(S instanceof Wall))
            img = new Image("img/walls/Wall-End-E.png");
        if (!(E instanceof Wall) && (N instanceof Wall) && !(W instanceof Wall) && !(S instanceof Wall))
            img = new Image("img/walls/Wall-End-S.png");
        if ((E instanceof Wall) && !(N instanceof Wall) && !(W instanceof Wall) && !(S instanceof Wall))
            img = new Image("img/walls/Wall-End-O.png");
        if (!(E instanceof Wall) && !(N instanceof Wall) && !(W instanceof Wall) && (S instanceof Wall))
            img = new Image("img/walls/Wall-End-N.png");

        if (!(E instanceof Wall) && (N instanceof Wall) && !(W instanceof Wall) && (S instanceof Wall))
            img = new Image("img/walls/Wall-Vertical.png");
        if ((E instanceof Wall) && !(N instanceof Wall) && (W instanceof Wall) && !(S instanceof Wall))
            img = new Image("img/walls/Wall-Horizontal.png");

        if (!(E instanceof Wall) && (N instanceof Wall) && (W instanceof Wall) && !(S instanceof Wall))
            img = new Image("img/walls/Wall-Angle-NO.png");
        if ((E instanceof Wall) && (N instanceof Wall) && !(W instanceof Wall) && !(S instanceof Wall))
            img = new Image("img/walls/Wall-Angle-NE.png");
        if ((E instanceof Wall) && !(N instanceof Wall) && !(W instanceof Wall) && (S instanceof Wall))
            img = new Image("img/walls/Wall-Angle-SE.png");
        if (!(E instanceof Wall) && !(N instanceof Wall) && (W instanceof Wall) && (S instanceof Wall))
            img = new Image("img/walls/Wall-Angle-SO.png");

        if ((E instanceof Wall) && (N instanceof Wall) && (W instanceof Wall) && (S instanceof Wall))
            img = new Image("img/walls/Wall-X.png");

        if (!(E instanceof Wall) && !(N instanceof Wall) && !(W instanceof Wall) && !(S instanceof Wall))
            img = new Image("img/walls/Wall-O.png");


            return img;
    }

    public int getLargeur() {
        return larg;
    }

    public int getHauteur() {
        return haut;
    }

    public Pacman getPacman() {
        return pacman;
    }

    public int getIdxFruit() {
        return idxFruit;
    }

    public void removeIndex(int index) {
        plateau[index] = null;
    }

    public void setIndex(int idx, Entity e) {
        this.plateau[idx] = e;
    }

    public void remove(Entity ent) {
        for (int i = 0; i < plateau.length; i++) {
            if (plateau[i] == ent) {
                plateau[i] = new Entity(plateau[i].getPos(), plateau[i].getHitbox());
                return;
            }
        }
    }

}