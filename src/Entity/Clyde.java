package Entity;

import Game.Plateau;
import Utils.Direction;
import Utils.Position;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Clyde extends Ghost {
  Image img = new Image("img/ClydeGhost.png");

  public Clyde(double x, double y, double speed) {
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
    int xoff = 0;
    int yoff = 0;

    int curpac_x = (int) pac.getGridPos().getX();
    int curpac_y = (int) pac.getGridPos().getY();

    if (pac.getDir() == Direction.RIGHT) {
      while (curpac_x < p.getLargeur() && p.getCell(curpac_x + 4 - xoff, curpac_y) instanceof Wall) xoff++;
    } else if (pac.getDir() == Direction.LEFT) {
      while (curpac_x > 0 && p.getCell(curpac_x - 4 - xoff, curpac_y) instanceof Wall) xoff--;
    } else if (pac.getDir() == Direction.DOWN) {
      while (curpac_y < p.getHauteur() && p.getCell(curpac_x, curpac_y + 4 - yoff) instanceof Wall) yoff++;
    } else if (pac.getDir() == Direction.UP) {
      while (curpac_y > 0 && p.getCell(curpac_x, curpac_y - 4 - yoff) instanceof Wall) yoff--;
    }
    Position gotoPos = new Position(curpac_x + xoff, curpac_y + yoff);
    path = BreadthFirst(getGridPos(), gotoPos, p);

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
