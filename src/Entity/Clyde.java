package Entity;

public class Clyde extends Ghost {
  public Clyde(double x, double y, double speed) {
    super(x, y, speed);
  }

  public void tick(double dt) {
    AI();
    super.move(dt);
  }

  public void AI() {

  }
}
