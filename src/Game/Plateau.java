package Game;

import Entity.*;

import java.io.*;

public class Plateau {
    int idxFruit = 0;

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
        int larg = murs[0].length;

        plateau = new Entity[murs[0].length * larg];

        for (int i = 0; i < murs[0].length; i++) {
            t = read.readLine();
            for (int j = 0; j < larg; j++) {
                if (t.charAt(j) == '1') {
                    plateau[larg * i + j] = new Wall(i, j);
                }
                if (t.charAt(j) != '0') {
                    if (t.charAt(j) == 'p')
                        plateau[larg * i + j] = new PacGomme(i, j);
                    else
                        plateau[larg * i + j] = new SuperPacGomme(i, j);
                } else {
                    plateau[larg * i + j] = null;
                }

            }
        }

        int[] parsedLst = new int[lst.length];
        for (int i = 0; i < parsedLst.length; i++) {
            parsedLst[i] = Integer.parseInt(lst[i]);
        }

        plateau[parsedLst[2] * larg + parsedLst[3]] = new Pacman();
        plateau[parsedLst[4] * larg + parsedLst[5]] = new Inky(parsedLst[2], parsedLst[3], 1);
        plateau[parsedLst[6] * larg + parsedLst[7]] = new Pinky(parsedLst[6], parsedLst[7], 1);
        plateau[parsedLst[8] * larg + parsedLst[9]] = new Clyde(parsedLst[8], parsedLst[9], 1);
        plateau[parsedLst[10] * larg + parsedLst[11]] = new Blinky(parsedLst[10], parsedLst[11], 1);

        idxFruit = parsedLst[12] * larg + parsedLst[13];

        read.close();
    }

    public Entity getIndex(int index) {
        return plateau[index];
    }

    public Entity[] getPlateau() {
        return plateau;
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