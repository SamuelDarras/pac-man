package Entity;

import Game.Plateau;
import Utils.Direction;

public abstract class Ghost extends Personnage{
    private Direction prevdir = Direction.LEFT;

    public Ghost(double x, double y, double baseSpeed) {
        super(x, y, baseSpeed);
    }

    public abstract void tick(double dt, Plateau plateau);

    public abstract void AI();

    public void move(Pacman p) {
        if (p.getDir() != prevdir) {
            changeDir(Direction.values()[(int)(Math.random()*4)]);
            prevdir = p.getDir();
        }
    }
}
