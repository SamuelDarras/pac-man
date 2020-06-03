package Game;


import Entity.*;

public class Partie {
    private Score score;

    private Plateau pla;
    private Pacman pacman;


    public Partie(String levelPath, String wallsColor) throws Exception {
        score = new Score();
        pla = new Plateau(levelPath, wallsColor);
        pacman = pla.getPacman();
    }

    public void tick(double dt) {
        pacman.move(dt / 10000000.0, pla);
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
    /*interface Callback {
        void call();
    }
    private void doSomethingAt(long timing, Callback callback) {
        if (action && total == timing) {
            callback.call();
            action = false;
        }
        if (!action && total >= timing + 5)
            action = true;
    }*/
}

