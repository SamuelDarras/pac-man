package Entity;

public abstract class Ghost extends Personnage{
    public Ghost(double x, double y, double baseSpeed) {
        super(x, y, baseSpeed);
    }

    public abstract void tick(double dt);

    public abstract void AI();
}
