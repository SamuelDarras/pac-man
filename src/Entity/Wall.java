package Entity;

import Utils.Position;
import Utils.Constants;

public class Wall extends Entity{
    private Position<Double> pos;

    public Wall(double x, double y) {
        super(x, y, Constants.WALL_SIZE, Constants.WALL_SIZE);
    }
    public Wall(Position<Double> p) {
        super(p, Constants.WALL_SIZE, Constants.WALL_SIZE);
    }
}
