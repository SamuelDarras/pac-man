package Entity;

import Game.Plateau;
import Utils.Direction;
import Utils.Position;
import javafx.scene.image.Image;

public class Inky extends Ghost {



    public Inky(double x, double y, double speed, String skin) {
        super(x, y, speed);
        img = new Image("img/"+skin+"/InkyGhost.png");
    }

    public void tick(Pacman pac, Plateau p) {
        int xoff = 0;
        int yoff = 0;

        int curpac_x = (int) pac.getGridPos().getX();
        int curpac_y = (int) pac.getGridPos().getY();

        gotoPos = new Position(curpac_x, curpac_y);

        /* TODO: démerde toi pour faire cette merde :
         * - il faut passer à Inky la postion de Blinky
         * - définir le vecteur de Blinky à deux cases devant Pacman
         * - doubler ce veteur
         */


        path = BreadthFirst(getGridPos(), gotoPos, p);

        Direction n_dir = getDirectionAccordingToPath(path);
        changeDir(n_dir);
        changeDir(super.alterDirection(pac, p));
    }

}
