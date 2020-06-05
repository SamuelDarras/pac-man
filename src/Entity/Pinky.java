package Entity;

import Game.Plateau;
import Utils.Direction;
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
    if (getDir() == Direction.LEFT)
      gc.drawImage(img, getPos().getX() + getHitbox()[0], getPos().getY(), -getHitbox()[0], getHitbox()[1]);
    else
      gc.drawImage(img, getPos().getX(), getPos().getY(), getHitbox()[0], getHitbox()[1]);
  }

  public void move(Pacman pac, Plateau p) {
    path = BreadthFirst(getGridPos(), pac.getGridPos(), p);

        /*System.out.print("" + getGridPos() + ": ");
        for (Position t : path) {
            System.out.print("[" + t + "] -> ");
        }
        System.out.println("\n" + getDir());*/

    changeDir(getDirectionAccordingToPath(path));
  }

  public void AI() {

  }
}
