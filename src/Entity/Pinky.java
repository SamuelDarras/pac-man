package Entity;

import Game.Plateau;
import Utils.Direction;
import Utils.Position;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class Pinky extends Ghost {

    public Pinky(double x, double y, double speed, String skin) {
        super(x, y, speed);
        img = new Image("img/"+skin+"/PinkyGhost.png");
    }

    public void tick(Pacman pac, Plateau p) {

        path = new ArrayList<>();
        int xoff = 0;
        int yoff = 0;

        int curpac_x = (int) pac.getGridPos().getX();
        int curpac_y = (int) pac.getGridPos().getY();

        path = BreadthFirst(getGridPos().copy(), pac.getGridPos().copy(), p);

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

        gotoPos = new Position(curpac_x + xoff, curpac_y + yoff);
        path = BreadthFirst(getGridPos().copy(), gotoPos.copy(), p);

        Direction n_dir = getDirectionAccordingToPath(path);
        changeDir(n_dir);
        changeDir(super.alterDirection(pac, p));
    }


}
