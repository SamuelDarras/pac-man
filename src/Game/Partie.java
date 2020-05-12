package Game;


import Entity.*;

public class Partie {
    private Score score;

    private Plateau pla;
    private Pacman pacman;


    public Partie(String levelPath) throws Exception {
        score = new Score();
        pla = new Plateau(levelPath);
        pacman = pla.getPacman();
    }

    public void tick(double dt) {
        for (Entity e : pla.getPlateau()) {
            if (e instanceof Ghost)
                ((Ghost) e).move(dt);
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

