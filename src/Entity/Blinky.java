package Entity;

import Game.Plateau;
import javafx.scene.image.Image;

public class Blinky extends Ghost {

  public Blinky(double x, double y, double speed) {
    super(x, y, speed);
    img = new Image("img/BlinkyGhost.png");
  }

  public void tick(Pacman pac, Plateau p) {
    if (!pac.superPacman || definedDestination == null) {
      path = BreadthFirst(getGridPos(), pac.getGridPos(), p);
      changeDir(getDirectionAccordingToPath(path));
    }
    changeDir(super.alterDirection(pac, p));
  }
}
