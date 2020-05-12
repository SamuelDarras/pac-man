package Entity;

public class Pinky extends Ghost {
  public Pinky(double x, double y, double speed) {
    super(x, y, speed);
  }

  public void tick(double dt) {
    AI();
    super.move(dt);
  }

  public void AI() {

  }
}
