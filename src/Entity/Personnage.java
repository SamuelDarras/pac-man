package Entity;

import Game.Plateau;
import Utils.*;

import static Utils.Constants.*;

public class Personnage extends Entity{

  private double baseSpeed;
  private double speed;
  private static final double[] speedLimits = new double[] {0, 4};

  private Direction dir = Direction.RIGHT;

  public Personnage (double _x, double _y, double _baseSpeed) {
    super(_x, _y, PERSONNAGE_WIDTH, PERSONNAGE_HEIGHT);
    baseSpeed = _baseSpeed;
    speed = baseSpeed;
  }

  public void move(double dt, Plateau p) {
    double x = getPos().getX();
    double y = getPos().getY();

    Position<Double> prevPos = getPos().copy();

    switch (dir) {
      case UP:
        y -= speed*dt;
        break;

      case DOWN:
        y += speed*dt;
        break;

      case LEFT:
        x -= speed*dt;
        break;

      case RIGHT:
        x += speed*dt;
        break;

      default:
        break;
    }

    x %= SCENE_WIDTH;
    y %= SCENE_HEIGHT;
    x = x < 0 ? SCENE_WIDTH  : x;
    y = y < 0 ? SCENE_HEIGHT : y;

    if (dir == Direction.RIGHT || dir == Direction.LEFT) {
      y = Math.round( y / WALL_HEIGHT);
      System.out.println(y);
      y *= WALL_HEIGHT;
      y += (WALL_HEIGHT- PERSONNAGE_HEIGHT)/2;
    } else {
      x = Math.round( x / WALL_WIDTH );
      x *= WALL_WIDTH;
      x += (WALL_WIDTH- PERSONNAGE_WIDTH)/2;
    }

    setPos(new Position<>(x, y));

    for (Entity e : p.getPlateau()) {
      if (e instanceof Wall && hit(e))
        setPos(prevPos.copy());
    }

  }

  public void tp(double n_x, double n_y) {
    setPos(new Position<>(n_x, n_y));
  }

  public void changeDir(Direction n_dir) {
    dir = n_dir;
  }

  public void addSpeed(double addAmout) {
    if (speed + addAmout <= speedLimits[1] && speed + addAmout >= speedLimits[0])
    speed += addAmout;
    else {
      if (speed + addAmout > speedLimits[1])
      speed = speedLimits[1];
      if (speed + addAmout < speedLimits[0])
      speed = speedLimits[0];
    }
  }
  public void resetSpeed() {
    speed = baseSpeed;
  }
  public double getSpeed() { return speed; }
  public double getBaseSpeed() { return baseSpeed; }

  public Direction getDir() {
    return dir;
  }

  public String toString() { return "(x: " + getPos().getX() + " ; y: " + getPos().getY() + ") | speed: " + speed + " | Direction: " + dir; }
}
