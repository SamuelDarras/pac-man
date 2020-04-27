package Entity;

class Pinky extends Ghost {
  public Pinky(double x, double y, double speed) {
    super(x, y, speed);
  }

  public void tick() {
    AI();
    super.move();
  }

  public void AI() {

  }
}
