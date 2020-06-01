package Entity;

import Game.Plateau;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Pinky extends Ghost {
  Image img = new Image("img/PinkyGhost.png");

  public Pinky(double x, double y, double speed) {
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
