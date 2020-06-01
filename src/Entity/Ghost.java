package Entity;

import Game.Plateau;

public abstract class Ghost extends Personnage{
    public Ghost(double x, double y, double baseSpeed) {
        super(x, y, baseSpeed);
    }

    public abstract void tick(double dt, Plateau plateau);

    public abstract void AI();

    public void move(Pacman p) {
        changeDir(p.getDir());
    }
}
