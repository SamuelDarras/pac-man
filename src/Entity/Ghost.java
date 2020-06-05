package Entity;

import Game.Plateau;
import Utils.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;

public abstract class Ghost extends Personnage{
    Position prevpac;
    Position prevpos;
    ArrayList<Position> path;
    int nextIdx = 0;

    public Ghost(double x, double y, double baseSpeed) {
        super(x, y, baseSpeed);
    }

    public abstract void AI();

    public Direction getDirectionAccordingToPath(ArrayList<Position> path) {
        int curx = (int) getGridPos().getX();
        int cury = (int) getGridPos().getY();
        if (path.size() > 0){
            int p0x = (int) path.get(path.size()-1).getX();
            int p0y = (int) path.get(path.size()-1).getY();

            if (curx > p0x)
                return Direction.LEFT;
            if (curx < p0x)
                return Direction.RIGHT;
            if (cury > p0y)
                return Direction.UP;
            if (cury < p0y)
                return Direction.DOWN;
        }
        return getDir();
    }

    public ArrayList<Position> BreadthFirst(Position start, Position end, Plateau plat) {
        ArrayList<Position> frontier = new ArrayList<>();
        frontier.add(start);

        ArrayList<Position> came_from_from = new ArrayList<>();
        ArrayList<Position> came_from_to = new ArrayList<>();

        Position current;

        while (frontier.size() > 0) {
            current = frontier.get(0);
            frontier.remove(0);

            if (current.equals(end))
                break;

            for (Position next : getNeighbours(current, plat)) {
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
            current = came_from_from.get(index(came_from_to, current));
        }
        return path;
    }

    private ArrayList<Position> getNeighbours(Position current, Plateau plat) {
        ArrayList<Position> ret = new ArrayList<>();
        int curx = (int) current.getX();
        int cury = (int) current.getY();
        if (curx - 1 >= 0 && !(plat.getCell(curx - 1, cury) instanceof Wall)) ret.add(new Position(curx - 1, cury));
        if (cury - 1 >= 0 && !(plat.getCell(curx, cury - 1) instanceof Wall)) ret.add(new Position(curx, cury - 1));
        if (curx + 1 < plat.getLargeur() && !(plat.getCell(curx + 1, cury) instanceof Wall)) ret.add(new Position(curx + 1, cury));
        if (cury + 1 < plat.getHauteur() && !(plat.getCell(curx, cury + 1) instanceof Wall)) ret.add(new Position(curx, cury + 1));
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
}
