package Entity;

public class Inky extends Ghost {
  public Inky(double x, double y, double speed) {
    super(x, y, speed);
  }

  public void tick(double dt) {
    AI();
    super.move(dt);
  }

  public void AI() {

  }
}
