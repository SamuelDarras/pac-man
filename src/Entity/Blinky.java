package Entity;

import Game.Plateau;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Blinky extends Ghost {
  Image img = new Image("img/BlinkyGhost.png");

  public Blinky(double x, double y, double speed) {
    super(x, y, speed);
  }

  public void tick(double dt, Plateau p) {
    AI();
    super.move(dt, p);
  }

  public void draw(GraphicsContext gc) {
    gc.drawImage(img, getPos().getX(), getPos().getY(), getHitbox()[0], getHitbox()[1]);
  }

  public void AI() {

  }
}
