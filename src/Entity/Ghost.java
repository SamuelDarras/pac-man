package Entity;

import Game.Plateau;
import Utils.Direction;
import Utils.Position;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.Random;

public abstract class Ghost extends Personnage {
    ArrayList<Position> path;

    Position definedDestination;

    public Ghost(double x, double y, double baseSpeed) {
        super(x, y, baseSpeed);
    }

    int curx = -1;
    int cury = -1;
    public Direction getDirectionAccordingToPath(ArrayList<Position> path) {


        if (path.size() > 0) {
            int p0x = (int) path.get(path.size() - 1).getX();
            int p0y = (int) path.get(path.size() - 1).getY();

            int curx = (int) getGridPos().getX();
            int cury = (int) getGridPos().getY();

            if (curx > p0x && getDir() != Direction.RIGHT)
                return Direction.LEFT;
            if (curx < p0x && getDir() != Direction.LEFT)
                return Direction.RIGHT;
            if (cury > p0y && getDir() != Direction.DOWN)
                return Direction.UP;
            if (cury < p0y && getDir() != Direction.UP)
                return Direction.DOWN;
        }
        return getDir();
    }

    public void go(Position pos) {
        definedDestination = pos.copy();
    }

    public void draw(GraphicsContext gc) {
    }

    public ArrayList<Position> BreadthFirst(Position start, Position end, Plateau plat) {
        ArrayList<Position> frontier = new ArrayList<>();
        frontier.add(start);

        ArrayList<Position> came_from_from = new ArrayList<>();
        ArrayList<Position> came_from_to = new ArrayList<>();

        Position current;

        Plateau platCpy = plat.simpleCopy();
        int curx = (int) getGridPos().getX();
        int cury = (int) getGridPos().getY();
        switch (getDir()) {
            case UP:
                platCpy.setCell(new Wall(platCpy.getCell(curx, cury + 1).getPos().getX(), platCpy.getCell(curx, cury + 1).getPos().getY()), curx, cury + 1);
                break;
            case DOWN:
                platCpy.setCell(new Wall(platCpy.getCell(curx, cury - 1).getPos().getX(), platCpy.getCell(curx, cury - 1).getPos().getY()), curx, cury - 1);
                break;
            case LEFT:
                platCpy.setCell(new Wall(platCpy.getCell(curx + 1, cury).getPos().getX(), platCpy.getCell(curx + 1, cury).getPos().getY()), curx + 1, cury);
                break;
            case RIGHT:
                platCpy.setCell(new Wall(platCpy.getCell(curx - 1, cury).getPos().getX(), platCpy.getCell(curx - 1, cury).getPos().getY()), curx - 1, cury);
                break;
        }

        while (frontier.size() > 0) {
            current = frontier.get(0);
            frontier.remove(0);

            if (current.equals(end))
                break;

            for (Position next : getNeighbours(current, platCpy)) {
                if (!in(came_from_to, next)) {
                    frontier.add(next);
                    came_from_to.add(next.copy());
                    came_from_from.add(current.copy());
                }
            }
        }

        current = end;
        ArrayList<Position> path = new ArrayList<>();
        while (!current.equals(start)) {
            path.add(current.copy());
            if (index(came_from_to, current) == -1)
                return new ArrayList<>();
            current = came_from_from.get(index(came_from_to, current));
        }
        return path;
    }

    private ArrayList<Position> getNeighbours(Position current, Plateau plat) {
        ArrayList<Position> ret = new ArrayList<>();
        int curx = (int) current.getX();
        int cury = (int) current.getY();
        if (curx - 1 >= 0 && !(plat.getCell(curx - 1, cury) instanceof Wall))
            ret.add(new Position(curx - 1, cury));
        if (cury - 1 >= 0 && !(plat.getCell(curx, cury - 1) instanceof Wall))
            ret.add(new Position(curx, cury - 1));
        if (curx + 1 < plat.getLargeur() && !(plat.getCell(curx + 1, cury) instanceof Wall))
            ret.add(new Position(curx + 1, cury));
        if (cury + 1 < plat.getHauteur() && !(plat.getCell(curx, cury + 1) instanceof Wall))
            ret.add(new Position(curx, cury + 1));
        return ret;
    }

    private boolean in(ArrayList<Position> a, Position b) {
        for (Position p : a)
            if (b != null && p.equals(b))
                return true;
        return false;
    }

    private int index(ArrayList<Position> a, Position b) {
        for (int i = 0; i < a.size(); i++) {
            if (a.get(i).equals(b))
                return i;
        }
        return -1;
    }

    public Direction alterDirection(Pacman pac, Plateau plat) {
        if (curx == -1 || cury == -1) {
            curx = (int) getGridPos().getX();
            cury = (int) getGridPos().getY();
        }

        //System.out.println(getGridPos());
        //System.out.println(new Position(curx, cury));
        if (pac.superPacman && !getGridPos().equals(new Position(curx, cury))) {
            curx = (int) getGridPos().getX();
            cury = (int) getGridPos().getY();
            int x = (int)(Math.random()*plat.getLargeur());
            int y = (int)(Math.random()*plat.getHauteur());
            while (plat.getCell(x, y) instanceof Wall || Math.abs(x - (int) pac.getGridPos().getX()) < 4 || Math.abs(y - (int) pac.getGridPos().getY()) < 4 || getGridPos().equals(new Position(x, y))) {
                x = (int)(Math.random()*plat.getLargeur());
                y = (int)(Math.random()*plat.getHauteur());
            }
            return getDirectionAccordingToPath(BreadthFirst(getGridPos(), new Position(x, y), plat));
        }
        if (definedDestination != null) {
            if (getGridPos().equals(definedDestination)) {
                definedDestination = null;
                return getDir();
            }
            return getDirectionAccordingToPath(BreadthFirst(getGridPos(), definedDestination, plat));
        }
        return getDir();
    }
}
