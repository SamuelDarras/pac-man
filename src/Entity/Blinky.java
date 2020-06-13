package Entity;

import Game.Plateau;
import javafx.scene.image.Image;

public class Blinky extends Ghost {


  public Blinky(double x, double y, double speed, String skin) {
    super(x, y, speed);
    img = new Image("img/"+skin+"/BlinkyGhost.png");
  }

  public void tick(Pacman pac, Plateau p) {
    gotoPos = pac.getGridPos();
    path = BreadthFirst(getGridPos(), gotoPos, p);
    changeDir(getDirectionAccordingToPath(path));
    changeDir(super.alterDirection(pac, p));
  }

}
