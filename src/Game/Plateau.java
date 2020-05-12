package Game;

import Entity.*;
import Utils.Constants;

import java.io.*;

public class Plateau {
    int idxFruit = 0;
    int larg;

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


        int[][] murs = new int[Integer.parseInt(lst[0])][Integer.parseInt(lst[1])];
        Items[][] items = new Items[Integer.parseInt(lst[0])][Integer.parseInt(lst[1])];
        larg = murs[0].length;

        plateau = new Entity[larg * larg];
        double x;
        double y;
        for (int i = 0; i < larg; i++) {
            t = read.readLine();
            for (int j = 0; j < larg; j++) {
                x = i * 1.0*Constants.SCENE_WIDTH/getLargeur();
                y = j * 1.0*Constants.SCENE_WIDTH/getLargeur();

                if (t.charAt(j) == '1') {
                    plateau[larg * i + j] = new Wall(x, y);
                }
                else if (t.charAt(j) != '0') {
                    if (t.charAt(j) == 'p')
                        plateau[larg * i + j] = new PacGomme(x, y);
                    else
                        plateau[larg * i + j] = new SuperPacGomme(x, y);
                } else {
                    plateau[larg * i + j] = new Entity(x, y, 1, 1);
                }

            }
        }

        int[] parsedLst = new int[lst.length];
        for (int i = 0; i < parsedLst.length; i++) {
            parsedLst[i] = Integer.parseInt(lst[i]);
        }

        plateau[parsedLst[2] * larg + parsedLst[3]] = new Pacman();
        pacman = (Pacman) plateau[parsedLst[2] * larg + parsedLst[3]];
        plateau[parsedLst[4] * larg + parsedLst[5]] = new Inky(parsedLst[2]* 1.0*Constants.SCENE_WIDTH/getLargeur(), parsedLst[3]* 1.0*Constants.SCENE_WIDTH/getLargeur(), 1);
        plateau[parsedLst[6] * larg + parsedLst[7]] = new Pinky(parsedLst[6]* 1.0*Constants.SCENE_WIDTH/getLargeur(), parsedLst[7]* 1.0*Constants.SCENE_WIDTH/getLargeur(), 1);
        plateau[parsedLst[8] * larg + parsedLst[9]] = new Clyde(parsedLst[8]* 1.0*Constants.SCENE_WIDTH/getLargeur(), parsedLst[9]* 1.0*Constants.SCENE_WIDTH/getLargeur(), 1);
        plateau[parsedLst[10] * larg + parsedLst[11]] = new Blinky(parsedLst[10]* 1.0*Constants.SCENE_WIDTH/getLargeur(), parsedLst[11]* 1.0*Constants.SCENE_WIDTH/getLargeur(), 1);

        idxFruit = parsedLst[12] * larg + parsedLst[13];

        read.close();
    }

    public Entity getIndex(int index) {
        return plateau[index];
    }

    public Entity[] getPlateau() {
        return plateau;
    }

    public int getLargeur() {
        return larg;
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
                plateau[i] = null;
                return;
            }
        }
    }

}