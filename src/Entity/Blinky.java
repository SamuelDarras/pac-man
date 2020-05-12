package Entity;

public class Blinky extends Ghost {
  public Blinky(double x, double y, double speed) {
    super(x, y, speed);
  }

  public void tick(double dt) {
    AI();
    super.move(dt);
  }

  public void AI() {

  }
}
