package Entity;

import Utils.*;

public class Personnage {

  private double x;
  private double y;

  private double baseSpeed;
  private double speed;
  private static final double[] speedLimits = new double[] {0, 4};

  private Direction dir = Direction.RIGHT;

  public Personnage (double _x, double _y, double _baseSpeed) {
    x = _x;
    y = _y;
    baseSpeed = _baseSpeed;
    speed = baseSpeed;
  }

  public void tick() {
    move();
  }

  public void move() {
    switch (dir) {
      case UP:
        y -= speed;
        break;

      case DOWN:
        y += speed;
        break;

      case LEFT:
        x -= speed;
        break;

      case RIGHT:
        x += speed;
        break;

      default:
        break;
    }
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

  public double getX() {  return x;}
  public double getY() {  return y;}

  public String toString() {
    return "(x: " + x + " ; y: " + y + ") | speed: " + speed + " | Direction: " + dir;
  }
}
