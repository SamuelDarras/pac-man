package Entity;

import Game.Plateau;

public class Clyde extends Ghost {
  public Clyde(double x, double y, double speed) {
    super(x, y, speed);
  }

  public void tick(double dt, Plateau p) {
    AI();
    super.move(dt, p);
  }

  public void AI() {

  }
}
