package Entity;

import Utils.*;
import Entity.*;
import Game.*;

import java.io.BufferedReader;
import java.io.FileReader;

public class Pacman extends Personnage{

	private boolean superPacman = false;

	public Pacman(){
        super(0, 0, 1);
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

	public void manger(Plateau plateau, Partie partie){
        for (Entity e : plateau.getPlateau()) {
            if(e instanceof Items && e.hit(this)) {
                partie.scoreAdd(((Items) e).getScore());
                plateau.remove(e);
            }
        }
    }

    public void move(){
    	super.move();
    }
}
