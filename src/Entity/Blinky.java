package Entity;

import Game.Plateau;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Blinky extends Ghost {

  //fantôme rouge
  public Blinky(double x, double y, double speed, String skin) {
    super(x, y, speed, skin);
    img = new Image("img/Pacman/"+skin+"/BlinkyGhost.png");
  }

  public void draw(GraphicsContext gc) {
    gc.setStroke(Color.RED);
    super.draw(gc);
  }

  //met à jour la direction du fantôme
  public void tick(Pacman pac, Plateau p) {
    gotoPos = pac.getGridPos();
    path = BreadthFirst(getGridPos(), gotoPos, p);
    changeDir(getDirectionAccordingToPath(path));
    changeDir(super.alterDirection(pac, p));
  }

}
