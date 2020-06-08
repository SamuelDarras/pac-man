package Entity;

import Utils.*;
import Game.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;



public class Pacman extends Personnage{
    private int life = 3;

    private Position originalPos;

    private static final Image imR = new Image("img/pacManR.png");
    private static final Image imD = new Image("img/pacManD.png", false);
    private static final Image imL = new Image("img/pacManL.png", false);
    private static final Image imU = new Image("img/pacManU.png", false);

	boolean superPacman = false;

	public Pacman(double x, double y, double speed){
	    super(x, y, speed);
        originalPos = new Position(x, y);
    }

	public boolean isDead(Plateau p){
        for (Entity e : p.getPlateau()) {
            if (e instanceof Ghost && e.hit(this)) {
                if (!superPacman)
                    life--;
                else
                    ((Ghost) e).go(p.getHouse());
                return true;
            }
        }
        return false;
	}

	public void resetPos() {
	    setPos(originalPos);
    }

	public int getLife() { return life; }

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
