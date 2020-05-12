package Entity;

import Utils.*;

public class Personnage extends Entity{

  private double baseSpeed;
  private double speed;
  private static final double[] speedLimits = new double[] {0, 4};

  private Direction dir = Direction.RIGHT;

  public Personnage (double _x, double _y, double _baseSpeed) {
    super(_x, _y, Constants.PERSONNAGE_SIZE, Constants.PERSONNAGE_SIZE);
    baseSpeed = _baseSpeed;
    speed = baseSpeed;
  }

  public void move(double dt) {
    double x = getPos().getX();
    double y = getPos().getY();

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

    x %= Constants.SCENE_WIDTH;
    y %= Constants.SCENE_HEIGHT;
    x = x < 0 ? Constants.SCENE_WIDTH : x;
    y = y < 0 ? Constants.SCENE_HEIGHT : y;

    setPos(new Position<>(x, y));
  }

  public void tp(float n_x, float n_y) {
    setPos(new Position(n_x, n_y));
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

  public String toString() { return "(x: " + getPos().getX() + " ; y: " + getPos().getY() + ") | speed: " + speed + " | Direction: " + dir; }
}
