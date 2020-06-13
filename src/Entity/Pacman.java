package Entity;

import Utils.*;
import Game.*;
import javafx.animation.PauseTransition;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.util.Duration;


public class Pacman extends Personnage{
    private int life = 3;

    private  final Image imR;
    private  final Image imD;
    private  final Image imL;
    private  final Image imU;

	boolean superPacman = false;

	public Pacman(double x, double y, double speed, String skin){
	    super(x, y, speed);
	    imR = new Image("img/Pacman/"+skin+"/pacManR.png");
        imD = new Image("img/Pacman/"+skin+"/pacManD.png");
        imL = new Image("img/Pacman/"+skin+"/pacManL.png");
        imU = new Image("img/Pacman/"+skin+"/pacManU.png");
    }

	public boolean isDead(Plateau p){
        for (Entity e : p.getPlateau()) {
            if (e instanceof Ghost && e.hit(this)) {
                if (!superPacman) {
                    for (Entity ee : p.getPlateau())
                        if (ee instanceof Ghost)
                            ((Ghost) ee).resetPosition();

                    life--;
                    return true;
                } else {
                    ((Ghost) e).dead = true;
                    return false;
                }
            }
        }
        return false;
	}

	public void resetPos() {
	    setPos(originalPos);
    }

	public int getLife() { return life; }
    public boolean getSuperPacMan() { return superPacman; }

    public void setSuperPacMan(boolean bool){ this.superPacman=bool;}

	public void manger(Partie partie){
        for (Entity e : partie.getPlateau().getPlateau()) {
            if(e instanceof Items && e.hit(this)) {
                if (e instanceof SuperPacGomme) {
                    superPacman = true;
                }
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
                gc.drawImage(imR, x, y, Constants.PERSONNAGE_WIDTH, Constants.PERSONNAGE_HEIGHT);
                break;
            case LEFT:
                gc.drawImage(imL, x, y, Constants.PERSONNAGE_WIDTH, Constants.PERSONNAGE_HEIGHT);
                break;
            case UP:
                gc.drawImage(imU, x, y, Constants.PERSONNAGE_WIDTH, Constants.PERSONNAGE_HEIGHT);
                break;
            case DOWN:
                gc.drawImage(imD, x, y, Constants.PERSONNAGE_WIDTH, Constants.PERSONNAGE_HEIGHT);
                break;
            default:
                break;
        }
    }

}
