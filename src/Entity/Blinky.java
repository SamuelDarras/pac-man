package Entity;

import Game.Plateau;
import Utils.Direction;
import Utils.Position;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class Blinky extends Ghost {
  Image img = new Image("img/BlinkyGhost.png");

  public Blinky(double x, double y, double speed) {
    super(x, y, speed);
  }

  public void draw(GraphicsContext gc) {
    super.draw(gc);

    if (getDir() == Direction.LEFT)
      gc.drawImage(img, getPos().getX() + getHitbox()[0], getPos().getY(), -getHitbox()[0], getHitbox()[1]);
    else
      gc.drawImage(img, getPos().getX(), getPos().getY(), getHitbox()[0], getHitbox()[1]);
  }

  public void tick(Pacman pac, Plateau p) {
    if (!pac.superPacman) {

      path = BreadthFirst(getGridPos(), pac.getGridPos(), p);
      changeDir(getDirectionAccordingToPath(path));
    }
    changeDir(super.alterDirection(pac, p));
  }
}
