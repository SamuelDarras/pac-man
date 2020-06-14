package Game;


import Entity.*;

public class Partie {
    private final Score score;

    private final Plateau pla;
    private final Pacman pacman;

    public Partie(String levelPath, String wallsColor, String skin, double volume) throws Exception {
        score = new Score();
        pla = new Plateau(levelPath, wallsColor, skin,volume);
        pacman = pla.getPacman();
    }

    public void tick(double dt) {
        pacman.move(dt / 10000000.0, pla);
        if (pacman.isDead(pla)) {
            pacman.resetPos();
        }
        pacman.manger(this);
        for (Entity e : pla.getPlateau()) {
            if (e instanceof Pinky) {
                ((Pinky) e).tick(this.getPacman(), this.getPlateau());
                ((Pinky) e).move(dt / 10000000.0, pla);
            }
            if (e instanceof Inky) {
                ((Inky) e).tick(this.getPacman(), this.getPlateau());
                ((Inky) e).move(dt / 10000000.0, pla);
            }
            if (e instanceof Clyde) {
                ((Clyde) e).tick(this.getPacman(), this.getPlateau());
                ((Clyde) e).move(dt / 10000000.0, pla);
            }
            if (e instanceof Blinky) {
                ((Blinky) e).tick(this.getPacman(), this.getPlateau());
                ((Blinky) e).move(dt / 10000000.0, pla);
            }

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

