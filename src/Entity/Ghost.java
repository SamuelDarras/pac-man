package Entity;

import Game.Plateau;
import Utils.Direction;
import Utils.Position;
import javafx.geometry.Pos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.ArrayList;
import java.util.logging.Level;

public abstract class Ghost extends Personnage{
    int gridx;
    int gridy;

    int pac_prev_gridx;
    int pac_prev_gridy;
    private Direction prevdir = Direction.LEFT;

    public Ghost(double x, double y, double baseSpeed) {
        super(x, y, baseSpeed);
    }

    public abstract void AI();

    public void move(Pacman pac, Plateau plat) {

        if (    (int) getGridPos().getX() == gridx || (int) getGridPos().getY() == gridy ||
                (int) getGridPos().getX() != gridx || (int) getGridPos().getY() != gridy) {
        //if (true) {
            pac_prev_gridx = (int) pac.getGridPos().getX();
            pac_prev_gridy = (int) pac.getGridPos().getY();

            gridx = (int) getGridPos().getX();
            gridy = (int) getGridPos().getY();

            gridx %= plat.getLargeur();
            gridy %= plat.getHauteur();

            plat.getCell(gridx, gridy);
            ArrayList<Direction> possibles = new ArrayList<>();

            if ( gridx-1 >= 0 &&  !(plat.getCell(gridx-1, gridy) instanceof Wall))
                possibles.add(Direction.LEFT);
            if (gridx+1 < plat.getLargeur() && !(plat.getCell(gridx+1, gridy) instanceof Wall))
                possibles.add(Direction.RIGHT);
            if (gridy+1 < plat.getHauteur() && !(plat.getCell(gridx, gridy+1) instanceof Wall))
                possibles.add(Direction.UP);
            if (gridy-1 >= 0 && !(plat.getCell(gridx, gridy-1) instanceof Wall))
                possibles.add(Direction.DOWN);

/*            switch (getDir()) {
                case UP:
                    if (gridy+2 < plat.getHauteur() && !(plat.getCell(gridx, gridy+2) instanceof Wall))
                        possibles.add(Direction.UP);
                    if (gridy+1 < plat.getHauteur() && gridx-1 >= 0 && !(plat.getCell(gridx-1, gridy+1) instanceof Wall))
                        possibles.add(Direction.LEFT);
                    if (gridy+1 < plat.getHauteur() && gridx+1 < plat.getLargeur() && !(plat.getCell(gridx+1, gridy+1) instanceof Wall))
                        possibles.add(Direction.RIGHT);
                    break;
                case DOWN:
                    if (gridy-2 >= 0 && !(plat.getCell(gridx, gridy-2) instanceof Wall))
                        possibles.add(Direction.DOWN);
                    if (gridy-1 >= 0 && gridx-1 >= 0 && !(plat.getCell(gridx-1, gridy-1) instanceof Wall))
                        possibles.add(Direction.LEFT);
                    if (gridy-1 >= 0 && gridx+1 < plat.getLargeur() && !(plat.getCell(gridx+1, gridy-1) instanceof Wall))
                        possibles.add(Direction.RIGHT);
                    break;
                case LEFT:
                    if (gridx-2 >= 0 && !(plat.getCell(gridx-2, gridy) instanceof Wall))
                        possibles.add(Direction.LEFT);
                    if (gridy+1 < plat.getHauteur() && gridx-1 >= 0 && !(plat.getCell(gridx-1, gridy+1) instanceof Wall))
                        possibles.add(Direction.UP);
                    if (gridy-1 >= 0 && gridx-1 >= 0 && !(plat.getCell(gridx-1, gridy-1) instanceof Wall))
                        possibles.add(Direction.DOWN);
                    break;
                case RIGHT:
                    if (gridx+2 < plat.getLargeur() && !(plat.getCell(gridx+2, gridy) instanceof Wall))
                        possibles.add(Direction.RIGHT);
                    if (gridy+1 < plat.getHauteur() && gridx+1 >= plat.getLargeur() && !(plat.getCell(gridx+1, gridy+1) instanceof Wall))
                        possibles.add(Direction.UP);
                    if (gridy-1 >= 0 && gridx+1 >= plat.getLargeur() && !(plat.getCell(gridx+1, gridy-1) instanceof Wall))
                        possibles.add(Direction.DOWN);
                    break;
            }*/

            if (possibles.size() < 1)
                return;

            System.out.println();

            Direction best = mostLikely(pac);

            for (Direction d : possibles)
                System.out.println(d);

            if (possibles.contains(best)) {
                changeDir(best);
                System.out.println("\t-> " + best);
                return;
            }
            changeDir(possibles.get( (int) Math.random()*possibles.size() ));

        }
    }
    private Direction mostLikely(Pacman p) {
        double dx = p.getPos().getX() - getPos().getX();
        double dy = p.getPos().getY() - getPos().getY();

        if (Math.abs(dy) >= Math.abs(dx)) {
            if (dy >= 0)
                return Direction.DOWN;
            return Direction.UP;
        }
        if (Math.abs(dx) >= Math.abs(dy)) {
            if (dx >= 0)
                return Direction.RIGHT;
            return Direction.LEFT;
        }

        return getDir();
    }

    /*public ArrayList<Position> path(Position start, Position dest, Plateau p) {
        Position current = start.copy();
        ArrayList<Position> tested = new ArrayList<>();
        ArrayList<Position> neighbours = new ArrayList<>();

        while (current.getX() != dest.getX() && current.getY() != dest.getY()) {
            neighbours.clear();
            if ( gridx-1 >= 0 &&  !(p.getCell(gridx-1, gridy) instanceof Wall))
                neighbours.add(new Position(gridx-1, gridy));
            if (gridx+1 < p.getLargeur() && !(p.getCell(gridx+1, gridy) instanceof Wall))
                neighbours.add(new Position(gridx+1, gridy));
            if (gridy+1 < p.getHauteur() && !(p.getCell(gridx, gridy+1) instanceof Wall))
                neighbours.add(new Position(gridx, gridy+1));
            if (gridy-1 >= 0 && !(p.getCell(gridx, gridy-1) instanceof Wall))
                neighbours.add(new Position(gridx, gridy-1));

                ArrayList<int[]> scores = new ArrayList();
            for (Position pos : neighbours) {
            }
        }
    }*/
}
