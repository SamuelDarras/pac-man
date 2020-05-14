package Game;

import Entity.*;
import Utils.Constants;
import Utils.Position;
import javafx.scene.image.Image;

import java.io.*;

import static Utils.Constants.*;

public class Plateau {
    int idxFruit = 0;
    int larg;
    int haut;

    Pacman pacman;

    Entity[] plateau;

    public Plateau(String levelPath) throws Exception {
        try {
            remplirPlateau(levelPath);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    public void remplirPlateau(String levelPath) throws Exception {
        BufferedReader read = new BufferedReader(new FileReader(levelPath));
        String t = read.readLine();
        String[] lst = t.split(" ");

        larg = Integer.parseInt(lst[1]);
        haut = Integer.parseInt(lst[0]);

        Init(larg, haut);

        plateau = new Entity[larg * haut];
        double x;
        double y;
        for (int i = 0; i < haut; i++) {
            t = read.readLine();
            System.out.println(t);
            for (int j = 0; j < larg; j++) {
                x = j * 1.0* SCENE_WIDTH/getLargeur();
                y = i * 1.0* SCENE_HEIGHT/getHauteur();

                switch (t.charAt(j)) {
                    case '1':
                        plateau[larg * i + j] = new Wall(x, y, WALL_WIDTH, WALL_HEIGHT);
                        break;
                    case 'p':
                        plateau[larg * i + j] = new PacGomme(x + WALL_WIDTH/2 - PERSONNAGE_SIZE/4/2, y + WALL_HEIGHT/2 - PERSONNAGE_SIZE/4/2);
                        break;
                    case 's':
                        plateau[larg * i + j] = new SuperPacGomme(x + WALL_WIDTH/2 - PERSONNAGE_SIZE/2/2, y + WALL_HEIGHT/2 - PERSONNAGE_SIZE/2/2);
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

                

                /*if (t.charAt(j) == '1') {
                    plateau[larg * j + i] = new Wall(x, y);
                } else if (t.charAt(j) != '0') {
                    if (t.charAt(j) == 'p')
                        plateau[larg * j + i] = new PacGomme(x, y);
                    else
                        plateau[larg * j + i] = new SuperPacGomme(x, y);
                } else {
                    plateau[larg * j + i] = new Entity(x, y, 1, 1);
                }*/

            }

        }

        for (int i = 1; i < plateau.length-1; i++) {
            if (plateau[i] instanceof Wall)
                ((Wall) plateau[i]).setImg(defineWallImage(plateau[i-1], i-larg >= 0 ? plateau[i-larg] : null, plateau[i+1], i+larg < plateau.length ? plateau[i+larg] : null));
        }


        /*int[] parsedLst = new int[lst.length];
        for (int i = 0; i < parsedLst.length; i++) {
            parsedLst[i] = Integer.parseInt(lst[i]);
        }*/

//        idxFruit = parsedLst[12] * larg + parsedLst[13];

        read.close();
    }

    public Entity getIndex(int index) {
        return plateau[index];
    }

    public Entity[] getPlateau() {
        return plateau;
    }

    private Image defineWallImage(Entity l, Entity t, Entity r, Entity d) {
        Image img = null;

        if ( (l instanceof Wall) && !(t instanceof Wall) && !(r instanceof Wall) && !(d instanceof Wall) ) img = new Image("img/walls/Wall-End-E.png");
        if ( !(l instanceof Wall) && (t instanceof Wall) && !(r instanceof Wall) && !(d instanceof Wall) ) img = new Image("img/walls/Wall-End-S.png");
        if ( !(l instanceof Wall) && !(t instanceof Wall) && (r instanceof Wall) && !(d instanceof Wall) ) img = new Image("img/walls/Wall-End-O.png");
        if ( !(l instanceof Wall) && !(t instanceof Wall) && !(r instanceof Wall) && (d instanceof Wall) ) img = new Image("img/walls/Wall-End-N.png");

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