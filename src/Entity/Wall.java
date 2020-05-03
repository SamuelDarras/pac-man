package Entity;

import Utils.Position;
import Utils.Sizes;

public class Wall extends Entity{
    private Position<Double> pos;

    public Wall(double x, double y) {
        super(x, y, Sizes.WALL_SIZE, Sizes.WALL_SIZE);
    }
    public Wall(Position<Double> p) {
        super(p, Sizes.WALL_SIZE, Sizes.WALL_SIZE);
    }
}
