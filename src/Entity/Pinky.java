package Entity;

import Game.Plateau;
import Utils.Direction;
import Utils.Position;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Pinky extends Ghost {
  Image img = new Image("img/PinkyGhost.png");

  public Pinky(double x, double y, double speed) {
    super(x, y, speed);
  }

  public void draw(GraphicsContext gc) {
    super.draw(gc);

    if (getDir() == Direction.LEFT)
      gc.drawImage(img, getPos().getX() + getHitbox()[0], getPos().getY(), -getHitbox()[0], getHitbox()[1]);
    else
      gc.drawImage(img, getPos().getX(), getPos().getY(), getHitbox()[0], getHitbox()[1]);
  }

  public void move(Pacman pac, Plateau p) {
    int xoff = 0;
    int yoff = 0;

    int curpac_x = (int) pac.getGridPos().getX();
    int curpac_y = (int) pac.getGridPos().getY();

    path = BreadthFirst(getGridPos(), pac.getGridPos(), p);

    switch (pac.getDir()) {
      case LEFT:
        if (curpac_x >= 0 && !(p.getCell(curpac_x - 4, curpac_y) instanceof Wall))
          xoff = -4;
        break;
      case RIGHT:
        if (curpac_x + 4 < p.getLargeur() && !(p.getCell(curpac_x + 4, curpac_y) instanceof Wall))
          xoff = 4;
        break;
      case UP:
        if (curpac_y >= 0 && !(p.getCell(curpac_x, curpac_y - 4) instanceof Wall))
          yoff = -4;
        break;
      case DOWN:
        if (curpac_y + 4 < p.getHauteur() && !(p.getCell(curpac_x, curpac_y + 4) instanceof Wall))
          yoff = 4;
        break;
    }

    Position gotoPos = new Position(curpac_x + xoff, curpac_y + yoff);
    path = BreadthFirst(getGridPos(), gotoPos, p);

    Direction n_dir = getDirectionAccordingToPath(path);
    changeDir(n_dir);
  }

  public void AI() {

  }
}
