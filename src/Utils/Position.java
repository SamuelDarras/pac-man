package Utils;

public class Position<N> {
  private N x;
  private N y;
  public Position(N _x, N _y) {
    x = _x;
    y = _y;
  }
  public Position(Position<N> p) {
    x = p.getX();
    y = p.getY();
  }

  public void set(Position<N> n_p) {
    x = n_p.getX();
    y = n_p.getY();
  }
  public void setX(N n_x) { x = n_x; }
  public void setY(N n_y) { y = n_y; }

  public Position<N> get() { return this; }
  public N getX() { return x; }
  public N getY() { return y; }

  public Position<N> copy() { return new Position<>(x, y); }

  public String toString() { return "x: " + x + "; y: " + y; }
}
