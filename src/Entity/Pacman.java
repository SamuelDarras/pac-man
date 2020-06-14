package Entity;

import Game.Partie;
import Game.Plateau;
import Graphics.Window;
import Utils.Constants;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;

import java.time.LocalTime;


public class Pacman extends Personnage{
    private int life = 3;

    private  final Image imR;
    private  final Image imD;
    private  final Image imL;
    private  final Image imU;
    LocalTime tempSPM;

	boolean superPacman = false;
    AudioClip eatGhost = Window.openAudio("src/music/pacman_eatghost.wav");
    AudioClip death = Window.openAudio("src/music/pacman_death.wav");
    AudioClip chomp = Window.openAudio("src/music/pacman_chomp2.wav");
    AudioClip eatFruit = Window.openAudio("src/music/pacman_eatfruit.wav");

    //créé le pac man avec le skin et le volume sonore choisit
	public Pacman(double x, double y, double speed, String skin,double volume){
	    super(x, y, speed);
        death.setVolume(volume);
        chomp.setVolume(volume);
        eatGhost.setVolume(volume);
        eatFruit.setVolume(volume);
	    imR = new Image("img/Pacman/"+skin+"/pacManR.png");
        imD = new Image("img/Pacman/"+skin+"/pacManD.png");
        imL = new Image("img/Pacman/"+skin+"/pacManL.png");
        imU = new Image("img/Pacman/"+skin+"/pacManU.png");
    }

    //gestion des vies
	public boolean isDead(Plateau p){
        for (Entity e : p.getPlateau()) {
            if (e instanceof Ghost && e.hit(this)) {
                if (!superPacman) {
                    for (Entity ee : p.getPlateau())
                        if (ee instanceof Ghost)
                            ((Ghost) ee).resetPosition();
                    life--;
                    if(!death.isPlaying())
                        death.play();
                    return true;
                } else {
                    if(!eatGhost.isPlaying())
                       eatGhost.play();
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

    //gestion du son, de la récupération des pac-gommes, de la récupération des super pac-gommes et du score
	public void manger(Partie partie){
        for (Entity e : partie.getPlateau().getPlateau()) {
            if(e instanceof Items && e.hit(this)) {
                if(!chomp.isPlaying() && e instanceof PacGomme)
                    chomp.play();
                if(!(eatFruit.isPlaying())&& e instanceof Fruit)
                    eatFruit.play();
                if (e instanceof SuperPacGomme) {
                    chomp.play();
                    superPacman = true;
                    tempSPM= LocalTime.now();
                }
                partie.scoreAdd(((Items) e).getScore());
                partie.getPlateau().remove(e);
            }
        }
    }

    //change d'image suivant quelle direction pac man prends
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

    public LocalTime getSuperPacManTime(){return this.tempSPM;}

    public void stopSound(){
	    chomp.stop();
	    eatGhost.stop();
	    death.stop();
	    eatFruit.stop();
    }

}
