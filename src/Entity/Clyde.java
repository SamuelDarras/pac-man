package Entity;

import Game.Plateau;
import Utils.Direction;
import Utils.Position;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Clyde extends Ghost {

  public Clyde(double x, double y, double speed) {
    super(x, y, speed);
    img = new Image("img/ClydeGhost.png");
  }

  public void tick(Pacman pac, Plateau p) {
    if (!pac.superPacman || definedDestination == null) {
      int xoff = 0;
      int yoff = 0;

      int curpac_x = (int) pac.getGridPos().getX();
      int curpac_y = (int) pac.getGridPos().getY();

      Position gotoPos = new Position(1, 1);
      if (((int) getGridPos().getX() - curpac_x) * ((int) getGridPos().getX() - curpac_x) + ((int) getGridPos().getY() - curpac_y) * ((int) getGridPos().getY() - curpac_y) > 8 << 3)
        gotoPos = new Position(curpac_x + xoff, curpac_y + yoff);

      path = BreadthFirst(getGridPos(), gotoPos, p);

      Direction n_dir = getDirectionAccordingToPath(path);
      changeDir(n_dir);
    }
    changeDir(super.alterDirection(pac, p));
  }
}
