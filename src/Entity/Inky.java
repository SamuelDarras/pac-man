package Entity;

class Inky extends Ghost {
  public Inky(double x, double y, double speed) {
    super(x, y, speed);
  }

  public void tick() {
    AI();
    super.move();
  }

  public void AI() {

  }
}