package Entity;

import Game.Plateau;
import Utils.Direction;
import Utils.Position;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class Blinky extends Ghost {

  public Blinky(double x, double y, double speed) {
    super(x, y, speed);
    img = new Image("img/BlinkyGhost.png");
  }

  public void tick(Pacman pac, Plateau p) {
    if (!pac.superPacman) {

      path = BreadthFirst(getGridPos(), pac.getGridPos(), p);
      changeDir(getDirectionAccordingToPath(path));
    }
    changeDir(super.alterDirection(pac, p));
  }
}
