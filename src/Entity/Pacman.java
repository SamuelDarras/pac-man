package Entity;

import Utils.*;
import Entity.*;
import Game.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.BufferedReader;
import java.io.FileReader;

import static Utils.Constants.PERSONNAGE_SIZE;

public class Pacman extends Personnage{

    private static final Image imR = new Image("img/pacManR.png");
    private static final Image imD = new Image("img/pacManD.png", false);
    private static final Image imL = new Image("img/pacManL.png", false);
    private static final Image imU = new Image("img/pacManU.png", false);

	private boolean superPacman = false;

	public Pacman(){
        super(0, 0, Constants.PACMAN_SPEED);
	}
	public Pacman(double x, double y, double speed){
	    super(x, y, speed);
    }

	public boolean isDead(Plateau p){
        for (Entity e : p.getPlateau()) {
            if (e.hit(this))
                return true;
        }
        return false;
	}

	public void manger(Partie partie){
        for (Entity e : partie.getPlateau().getPlateau()) {
            if(e instanceof Items && e.hit(this)) {
                partie.scoreAdd(((Items) e).getScore());
                partie.getPlateau().remove(e);
            }
        }
    }

    public void draw(GraphicsContext gc) {
        double x = getPos().getX();
        double y = getPos().getY();

	    switch (getDir()) {
            case RIGHT:
                gc.drawImage(imR, x, y, PERSONNAGE_SIZE, PERSONNAGE_SIZE);
                break;
            case LEFT:
                gc.drawImage(imL, x, y, PERSONNAGE_SIZE, PERSONNAGE_SIZE);
                break;
            case UP:
                gc.drawImage(imU, x, y, PERSONNAGE_SIZE, PERSONNAGE_SIZE);
                break;
            case DOWN:
                gc.drawImage(imD, x, y, PERSONNAGE_SIZE, PERSONNAGE_SIZE);
                break;
            default:
                break;
        }
    }

}
