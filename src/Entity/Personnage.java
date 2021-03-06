package Entity;

import Game.Plateau;
import Utils.*;

import static Utils.Constants.*;

public class Personnage extends Entity{

  private final double baseSpeed;
  private double speed;
  private static final double[] speedLimits = new double[] {0, 8};

  Position<Double> originalPos;

  private Direction dir = Direction.RIGHT;

  public Personnage (double _x, double _y, double _baseSpeed) {
    super(_x, _y, PERSONNAGE_WIDTH, PERSONNAGE_HEIGHT);
    originalPos = new Position<>(_x, _y);
    baseSpeed = _baseSpeed;
    speed = baseSpeed;
  }

  //mouvement de pac-man avec implémentation d'un rail invisible permettant de mieux controler ses mouvements
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

    x = x < -getHitbox()[0] ? SCENE_WIDTH  : x;
    y = y < -getHitbox()[1] ? SCENE_HEIGHT : y;

    if (dir == Direction.RIGHT || dir == Direction.LEFT) {
      y = Math.round( y / WALL_HEIGHT);
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

  public void changeDir(Direction n_dir) {
    if (n_dir == null) {
      dir = getDir();
      return;
    }
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
  public Position<Integer> getGridPos() {
    return new Position<>((int) Math.round(getPos().getX()/WALL_WIDTH), (int) Math.round(getPos().getY()/WALL_HEIGHT));
  }

  public void resetSpeed() {
    speed = baseSpeed;
  }

  public double getSpeed() { return speed; }

  public void resetPosition() {
    setPos(originalPos);
  }

  public Direction getDir() {
      return dir;
  }

  public String toString() { return "(x: " + getPos().getX() + " ; y: " + getPos().getY() + ") | speed: " + speed + " | Direction: " + dir; }
}
