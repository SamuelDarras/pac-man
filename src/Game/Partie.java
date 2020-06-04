package Game;


import Entity.*;
import Graphics.Window;

public class Partie {
    private Score score;

    private Plateau pla;
    private Pacman pacman;

    private Window win;

    public Partie(String levelPath, String wallsColor, Window window) throws Exception {
        score = new Score();
        pla = new Plateau(levelPath, wallsColor);
        pacman = pla.getPacman();
        win = window;
    }

    public void tick(double dt) {
        pacman.move(dt / 10000000.0, pla);
        if (pacman.isDead(pla)) {
            pacman.resetPos();
        }
        pacman.manger(this);
        for (Entity e : pla.getPlateau()) {
            if (e instanceof Ghost)
                ((Ghost) e).move(dt / 10000000.0, pla);
        }
    }

    public Plateau getPlateau() {
        return pla;
    }

    public Pacman getPacman() {
        return pacman;
    }

    public void scoreAdd(int s) {
        score.scoreAdd(s);
    }

    public Score getScore() {
        return score;
    }
}

