package Entity;

class Clyde extends Personnage {
  public Clyde(double x, double y, double speed) {
    super(x, y, speed);
  }

  public void tick() {
    AI();
    super.move();
  }

  private void AI() {

  }
}
