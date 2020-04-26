package Entity;

import Utils.Position;

public class Entity {
    private Position<Double> pos;
    private double[] hitbox;

    public Entity(double x, double y, double w, double h) {
        pos = new Position<>(x, y);
        hitbox = new double[] { w, h };
    }
    public Entity(Position<Double> p, double w, double h) {
        pos = p.copy();
        hitbox = new double[] { w, h };
    }

    public Position<Double> getPos() { return pos; }
    public void setPos(Position<Double> n_pos) { pos = n_pos.copy(); }

    public double[] getHitbox() { return hitbox; }

    public boolean hit(Entity other) {

        double x = pos.getX();
        double y = pos.getY();
        double w = hitbox[0];
        double h = hitbox[1];

        double o_x = other.getPos().getX();
        double o_y = other.getPos().getY();
        double o_w = other.getHitbox()[0];
        double o_h = other.getHitbox()[1];

        return x < o_x + o_w && x + w > o_x && y < o_y + o_h && y + h > o_y;

    }
}
