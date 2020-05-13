package Entity;

import Utils.Position;
import Utils.Constants;

public class Wall extends Entity{

    public Wall(double x, double y, double w, double h) {
        super(x, y, w, h);
    }
    public Wall(Position<Double> p) {
        super(p, Constants.WALL_WIDTH, Constants.WALL_HEIGHT);
    }
}
