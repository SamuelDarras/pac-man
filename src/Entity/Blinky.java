package Entity;

public class Blinky extends Ghost {
  public Blinky(double x, double y, double speed) {
    super(x, y, speed);
  }

  public void tick() {
    AI();
    super.move();
  }

  public void AI() {

  }
}
