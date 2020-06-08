package Entity;

import Game.Plateau;
import Utils.Direction;
import Utils.Position;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Inky extends Ghost {
  Image img = new Image("img/InkyGhost.png");

  public Inky(double x, double y, double speed) {
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

    Position gotoPos = new Position(curpac_x, curpac_y);

    /* TODO: démerde toi pour faire cette merde :
     * - il faut passer à Inky la postion de Blinky
     * - définir le vecteur de Blinky à deux cases devant Pacman
     * - doubler ce veteur
     */


    path = BreadthFirst(getGridPos(), gotoPos, p);

    Direction n_dir = getDirectionAccordingToPath(path);
    changeDir(n_dir);

    super.move(pac, p);
  }
}
