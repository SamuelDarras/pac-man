package Entity;

import Utils.Position;

public class Wall extends Entity{
    private Position<Double> pos;

    public Wall(double x, double y) {
        super(x, y, 1, 1);
    }
    public Wall(Position<Double> p) {
        super(p, 1, 1);
    }
}
