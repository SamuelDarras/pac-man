package Game;

import Entity.*;
import Utils.Constants;
import Utils.Position;
import javafx.scene.image.Image;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import static Utils.Constants.*;

public class Plateau {
    String wallsColor = "blue";
    String skin = "classic";
    List<Integer> idxFruit;
    List<Integer> idxPG;
    List<Integer> idxSPG;
    LocalTime[] ltdebut;
    double volume;
    int larg;
    int haut;

    Position<Integer> house = new Position<>(0, 0);

    Pacman pacman;

    Entity[] plateau;

    private Plateau() {}
    public Plateau(String levelPath, String wallsColor, String skin,double volume) throws Exception {
        this.wallsColor = wallsColor;
        this.skin = skin;
        this.volume=volume;
        remplirPlateau(levelPath);
    }

    //permet de remplir le tableau plateau avec les entites correspondant grace aux informations du .txt des levels
    public void remplirPlateau(String levelPath) throws Exception {
        BufferedReader read = new BufferedReader(new FileReader(levelPath));
        String t = read.readLine();
        String[] lst = t.split("[. ]");

        haut = Integer.parseInt(lst[0]);
        larg = Integer.parseInt(lst[1]);

        System.out.print(haut);

        larg = Integer.parseInt(lst[1]);
        Init(larg, haut);
        idxFruit = new ArrayList<>();
        idxPG = new ArrayList<>();
        idxSPG = new ArrayList<>();


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
                        idxPG.add(larg * i + j);
                        break;
                    case 's':
                        plateau[larg * i + j] = new SuperPacGomme(x + WALL_WIDTH / 2 - PERSONNAGE_WIDTH / 2 / 2, y + WALL_HEIGHT / 2 - PERSONNAGE_HEIGHT / 2 / 2);
                        idxSPG.add(larg * i + j);
                        break;
                    case 'I':
                        plateau[larg * i + j] = new Inky(x, y, GHOST_SPEED, skin);
                        break;
                    case 'P':
                        plateau[larg * i + j] = new Pinky(x, y, GHOST_SPEED, skin);
                        break;
                    case 'B':
                        plateau[larg * i + j] = new Blinky(x, y, GHOST_SPEED, skin);
                        break;
                    case 'C':
                        plateau[larg * i + j] = new Clyde(x, y, GHOST_SPEED, skin);
                        break;
                    case 'M':
                        pacman = new Pacman(x, y, PACMAN_SPEED, skin,volume);
                        plateau[larg * i + j] = pacman;
                        break;
                    case 'H':
                        house = new Position<>(j, i);
                        plateau[larg * i + j] = new Entity(x, y, 1, 1);
                        break;
                    case'F':
                        plateau[larg * i + j] = new Entity(x, y, 1, 1);
                        idxFruit.add(larg * i + j);
                    default:
                        plateau[larg * i + j] = new Entity(x, y, 1, 1);
                        break;
                }

            }

        }

        setWalls();
        ltdebut=new LocalTime[idxFruit.size()];

        read.close();
    }

    //permet l'affichage des murs sur le programme
    public void setWalls() {
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

    // permet de définir l'image des murs en fonction des murs autour d'eux
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

        if (!(E instanceof Wall) && !(N instanceof Wall) && !(W instanceof Wall) && !(S instanceof Wall))
            img = new Image("img/wall/"+wallsColor+"/Wall-"+wallsColor+"-Block.png");


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

    //permet de creer une copie simplifiée du plateau
    public Plateau simpleCopy() {
        Plateau ret = new Plateau();
        ret.plateau = this.plateau.clone();
        ret.larg = this.larg;
        ret.haut = this.haut;

        return ret;
    }

    //enleve l'entity correspondant dans le plateau et le remplace par du vide
    public void remove(Entity ent) {
        for (int i = 0; i < plateau.length; i++) {
            if (plateau[i] == ent) {
                plateau[i] = new Entity(plateau[i].getPos(), plateau[i].getHitbox());
                return;
            }
        }
    }

    public Position<Integer> getHouse() {
        return house;
    }

    //permet la gestion des fruits dans le temps
    public void setFruit(){
        for (Integer integer : idxFruit) {
            if (!(plateau[integer] instanceof Fruit)) {
                if (ltdebut[idxFruit.indexOf(integer)]==null) {
                    ltdebut[idxFruit.indexOf(integer)] = LocalTime.now();
                }
                if(ChronoUnit.SECONDS.between(ltdebut[idxFruit.indexOf(integer)],LocalTime.now())>=10) {
                    Random random = new Random();
                    int temp = random.nextInt(Constants.FRUIT_NAME.length);
                    String typeFruit = Constants.FRUIT_NAME[temp];
                    int score = Constants.FRUIT_SCORE[temp];
                    double x = plateau[integer].getPos().getX();
                    double y = plateau[integer].getPos().getY();
                    plateau[integer] = new Fruit(score, x, y);
                    ((Fruit) plateau[integer]).setImg(new Image("img/" + typeFruit + ".png"));
                    ltdebut[idxFruit.indexOf(integer)]=null;
                }
            }
        }
    }

    //permet de savoir si il reste des pac gommes dans le plateau
    public boolean isAvailablePG(){
        boolean available=false;
        for (Entity entity : plateau) {
            if (entity instanceof PacGomme || entity instanceof SuperPacGomme) {
                available = true;
                break;
            }
        }
        return available;
    }

    //permet de replacer les pac gommes et les super pac gommes
    public void refillPG(){
        for (Integer integer : idxPG) {
            plateau[integer] = new PacGomme(plateau[integer].getPos().getX(), plateau[integer].getPos().getY());
        }
        for(Integer integer : idxSPG){
            plateau[integer] = new SuperPacGomme(plateau[integer].getPos().getX(), plateau[integer].getPos().getY());
        }
    }
}