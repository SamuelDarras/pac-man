package Entity;

import Game.Plateau;
import Utils.Direction;
import Utils.Position;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Clyde extends Ghost {

    //fantôme orange
    public Clyde(double x, double y, double speed, String skin) {
        super(x, y, speed, skin);
        img = new Image("img/Pacman/"+skin+"/ClydeGhost.png");
    }

    public void draw(GraphicsContext gc) {
        gc.setStroke(Color.YELLOW);
        super.draw(gc);
    }

    public void tick(Pacman pac, Plateau p) {
        int xoff = 0;
        int yoff = 0;

        int curpac_x = pac.getGridPos().getX();
        int curpac_y = pac.getGridPos().getY();

        gotoPos = new Position<>(1, 1);
        if ((getGridPos().getX() - curpac_x) * (getGridPos().getX() - curpac_x) + (getGridPos().getY() - curpac_y) * (getGridPos().getY() - curpac_y) > 8 << 3)
            gotoPos = new Position<>(curpac_x + xoff, curpac_y + yoff);

        path = BreadthFirst(getGridPos(), gotoPos, p);

        Direction n_dir = getDirectionAccordingToPath(path);
        changeDir(n_dir);
        changeDir(super.alterDirection(pac, p));
    }

}
