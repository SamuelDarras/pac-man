package Utils;

class Position {
  private double x;
  private double y;
  public Position(double _x, double _y) {
    x = _x;
    y = _y;
  }
  public Position(Position p) {
    x = p.getX();
    y = p.getY();
  }

  public double getX() { return x; }
  public double getY() { return y; }
}
