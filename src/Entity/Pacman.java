package Entity;

import Utils.*;
import Entity.*;
import Game.*;

import java.io.BufferedReader;
import java.io.FileReader;

public class Pacman extends Personnage{

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

}
