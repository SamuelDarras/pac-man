package Entity;

import Game.Plateau;
import Utils.Direction;
import Utils.Position;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Inky extends Ghost {



    public Inky(double x, double y, double speed, String skin) {
        super(x, y, speed, skin);
        img = new Image("img/Pacman/"+skin+"/InkyGhost.png");
    }

    public void draw(GraphicsContext gc) {
        gc.setStroke(Color.BLUE);
        super.draw(gc);
    }

    public void tick(Pacman pac, Plateau p) {
        int gotoX = pac.getGridPos().getX();
        int gotoY = pac.getGridPos().getY();
        for (Entity e : p.getPlateau()) {
            if (e instanceof Blinky) {
                gotoX = pac.getGridPos().getX() - ((Blinky) e).getGridPos().getX();
                gotoY = pac.getGridPos().getY() - ((Blinky) e).getGridPos().getY();
                gotoX += gotoX;
                gotoY += gotoY;
            }
        }

        gotoPos = new Position<>(gotoX, gotoY);
        if (p.getCell(gotoPos.getX(), gotoPos.getY()) instanceof Wall)
            path = BreadthFirst(getGridPos(), gotoPos, p);
        else
            path = BreadthFirst(getGridPos(), pac.getGridPos(), p);


        Direction n_dir = getDirectionAccordingToPath(path);
        changeDir(n_dir);
        changeDir(super.alterDirection(pac, p));
    }

}
