package Game;

import Entity.*;
import Utils.Position;
import javafx.scene.image.Image;

import java.io.BufferedReader;
import java.io.FileReader;

import static Utils.Constants.*;

public class Plateau {
    String wallsColor = "blue";
    int idxFruit = 0;
    int larg;
    int haut;

    Position house = new Position(0, 0);

    Pacman pacman;

    Entity[] plateau;

    private Plateau() {}
    public Plateau(String levelPath, String wallsColor) throws Exception {
            this.wallsColor = wallsColor;
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
                    case 'H':
                        house = new Position(j, i);
                    default:
                        plateau[larg * i + j] = new Entity(x, y, 1, 1);
                        break;
                }

            }

        }

        setWalls("blue");

        /*int[] parsedLst = new int[lst.length];
        for (int i = 0; i < parsedLst.length; i++) {
            parsedLst[i] = Integer.parseInt(lst[i]);
        }*/

//        idxFruit = parsedLst[12] * larg + parsedLst[13];

        read.close();
    }

    public void setWalls(String color) {
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
    }

    public Entity[] getPlateau() {
        return plateau;
    }

    public Entity getCell(int x, int y) {
        int idx = (x%larg + y * larg)%getPlateau().length;
        if (idx < 0)
            idx = 0;
        return getPlateau()[idx];
    }
    public void setCell(Entity e, int x, int y) {
        int idx = (x%larg + y * larg)%getPlateau().length;
        plateau[idx] = e;
    }

    private Image defineWallImage(Entity E, Entity N, Entity W, Entity S) {
        Image img = null;
        
        if ((E instanceof Wall) && !(N instanceof Wall) && (W instanceof Wall) && (S instanceof Wall))
            img = new Image("img/wall/"+wallsColor+"/Wall-"+wallsColor+"-T-down.png");
        if ((E instanceof Wall) && (N instanceof Wall) && (W instanceof Wall) && !(S instanceof Wall))
            img = new Image("img/wall/"+wallsColor+"/Wall-"+wallsColor+"-T-up.png");
        if (!(E instanceof Wall) && (N instanceof Wall) && (W instanceof Wall) && (S instanceof Wall))
            img = new Image("img/wall/"+wallsColor+"/Wall-"+wallsColor+"-T-left.png");
        if ((E instanceof Wall) && (N instanceof Wall) && !(W instanceof Wall) && (S instanceof Wall))
            img = new Image("img/wall/"+wallsColor+"/Wall-"+wallsColor+"-T-right.png");

        if (!(E instanceof Wall) && !(N instanceof Wall) && (W instanceof Wall) && !(S instanceof Wall))
            img = new Image("img/wall/"+wallsColor+"/Wall-"+wallsColor+"-End-right.png");
        if (!(E instanceof Wall) && (N instanceof Wall) && !(W instanceof Wall) && !(S instanceof Wall))
            img = new Image("img/wall/"+wallsColor+"/Wall-"+wallsColor+"-End-down.png");
        if ((E instanceof Wall) && !(N instanceof Wall) && !(W instanceof Wall) && !(S instanceof Wall))
            img = new Image("img/wall/"+wallsColor+"/Wall-"+wallsColor+"-End-left.png");
        if (!(E instanceof Wall) && !(N instanceof Wall) && !(W instanceof Wall) && (S instanceof Wall))
            img = new Image("img/wall/"+wallsColor+"/Wall-"+wallsColor+"-End-up.png");

        if (!(E instanceof Wall) && (N instanceof Wall) && !(W instanceof Wall) && (S instanceof Wall))
            img = new Image("img/wall/"+wallsColor+"/Wall-"+wallsColor+"-Vertical.png");
        if ((E instanceof Wall) && !(N instanceof Wall) && (W instanceof Wall) && !(S instanceof Wall))
            img = new Image("img/wall/"+wallsColor+"/Wall-"+wallsColor+"-Horizontal.png");

        if (!(E instanceof Wall) && (N instanceof Wall) && (W instanceof Wall) && !(S instanceof Wall))
            img = new Image("img/wall/"+wallsColor+"/Wall-"+wallsColor+"-Corner-bottom-right.png");
        if ((E instanceof Wall) && (N instanceof Wall) && !(W instanceof Wall) && !(S instanceof Wall))
            img = new Image("img/wall/"+wallsColor+"/Wall-"+wallsColor+"-Corner-bottom-left.png");
        if ((E instanceof Wall) && !(N instanceof Wall) && !(W instanceof Wall) && (S instanceof Wall))
            img = new Image("img/wall/"+wallsColor+"/Wall-"+wallsColor+"-Corner-top-left.png");
        if (!(E instanceof Wall) && !(N instanceof Wall) && (W instanceof Wall) && (S instanceof Wall))
            img = new Image("img/wall/"+wallsColor+"/Wall-"+wallsColor+"-Corner-top-right.png");

        if ((E instanceof Wall) && (N instanceof Wall) && (W instanceof Wall) && (S instanceof Wall))
            img = new Image("img/wall/"+wallsColor+"/Wall-"+wallsColor+"-T-full.png");

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

    public Plateau simpleCopy() {
        Plateau ret = new Plateau();
        ret.plateau = this.plateau.clone();
        ret.larg = this.larg;
        ret.haut = this.haut;

        return ret;
    }

    public void remove(Entity ent) {
        for (int i = 0; i < plateau.length; i++) {
            if (plateau[i] == ent) {
                plateau[i] = new Entity(plateau[i].getPos(), plateau[i].getHitbox());
                return;
            }
        }
    }

    public Position getHouse() {
        return house;
    }
}