package Entity;

import Game.Plateau;
import Utils.Direction;
import Utils.Position;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Pinky extends Ghost {

    //fantôme rose
    public Pinky(double x, double y, double speed, String skin) {
        super(x, y, speed, skin);
        img = new Image("img/Pacman/" + skin + "/PinkyGhost.png");
    }

    public void draw(GraphicsContext gc) {
        gc.setStroke(Color.PINK);
        super.draw(gc);
    }

    //met à jour la direction du fantôme
    public void tick(Pacman pac, Plateau p) {

        path = new ArrayList<>();
        int xoff = 0;
        int yoff = 0;

        int curpac_x = pac.getGridPos().getX();
        int curpac_y = pac.getGridPos().getY();

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

        gotoPos = new Position<>(curpac_x + xoff, curpac_y + yoff);
        path = BreadthFirst(getGridPos(), gotoPos, p);

        Direction n_dir = getDirectionAccordingToPath(path);
        if (path.size() == 0) {
            n_dir = getDirectionAccordingToPath(super.getNeighbours(getGridPos(), p));
        }
        changeDir(n_dir);
        changeDir(super.alterDirection(pac, p));
    }

}
