package Entity;

import Game.Plateau;
import Utils.Direction;
import Utils.Position;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public abstract class Ghost extends Personnage {
    ArrayList<Position<int>> path;

    public Ghost(double x, double y, double baseSpeed) {
        super(x, y, baseSpeed);
    }

    public abstract void AI();

    int curx = -1;
    int cury = -1;
    public Direction getDirectionAccordingToPath(ArrayList<Position<int>> path) {

        if (curx == -1 || cury == -1) {
            curx = (int) getGridPos().getX();
            cury = (int) getGridPos().getY();
        }

        System.out.print(getGridPos());
        for (int i = path.size() - 1; i > 0; i--) {
            System.out.println(" -> [" + path.get(i) + "]");
        }
        System.out.println();

        if ((curx != getPos().getX() || cury != getPos().getY()) && path.size() > 0) {
            int p0x = path.get(path.size() - 1).getX();
            int p0y = path.get(path.size() - 1).getY();

            curx = (int) getGridPos().getX();
            cury = (int) getGridPos().getY();

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

    public void draw(GraphicsContext gc) {
        double curx = getPos().getX() + getHitbox()[0] / 2;
        double cury = getPos().getY() + getHitbox()[1] / 2;

        /*gc.setFill(Color.RED);
        switch (getDir()) {
            case LEFT:
                gc.fillOval(curx - 20, cury, 10, 10);
                break;
            case RIGHT:
                gc.fillOval(curx + 20, cury, 10, 10);
                break;
            case DOWN:
                gc.fillOval(curx, cury + 20, 10, 10);
                break;
            case UP:
                gc.fillOval(curx, cury - 20, 10, 10);
                break;
        }*/
    }

    public ArrayList<Position<int>> BreadthFirst(Position<int> start, Position<int> end, Plateau plat) {
        ArrayList<Position<int>> frontier = new ArrayList<>();
        frontier.add(start);

        ArrayList<Position<int>> came_from_from = new ArrayList<>();
        ArrayList<Position<int>> came_from_to = new ArrayList<>();

        Position<int> current;

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

            for (Position<int> next : getNeighbours(current, platCpy)) {
                System.out.println("\t-> " + next);
                if (!in(came_from_to, next)) {
                    frontier.add(next);
                    came_from_to.add(next.copy());
                    came_from_from.add(current.copy());
                }
            }
            System.out.println();
        }

        current = end;
        ArrayList<Position<int>> path = new ArrayList<>();
        while (!current.equals(start)) {
            path.add(current.copy());
            if (index(came_from_to, current) == -1)
                return new ArrayList<>();
            current = came_from_from.get(index(came_from_to, current));
        }
        return path;
    }

    private ArrayList<Position<int>> getNeighbours(Position<int> current, Plateau plat) {
        ArrayList<Position<int>> ret = new ArrayList<>();
        int curx = current.getX();
        int cury = current.getY();
        if (curx - 1 >= 0 && !(plat.getCell(curx - 1, cury) instanceof Wall))
            ret.add(new Position<int>(curx - 1, cury));
        if (cury - 1 >= 0 && !(plat.getCell(curx, cury - 1) instanceof Wall))
            ret.add(new Position<int>(curx, cury - 1));
        if (curx + 1 < plat.getLargeur() && !(plat.getCell(curx + 1, cury) instanceof Wall))
            ret.add(new Position<int>(curx + 1, cury));
        if (cury + 1 < plat.getHauteur() && !(plat.getCell(curx, cury + 1) instanceof Wall))
            ret.add(new Position<int>(curx, cury + 1));
        return ret;
    }

    private boolean in(ArrayList<Position<int>> a, Position<int> b) {
        for (Position<int> p : a)
            if (b != null && p.equals(b))
                return true;
        return false;
    }

    private int index(ArrayList<Position<int>> a, Position<int> b) {
        for (int i = 0; i < a.size(); i++) {
            if (a.get(i).equals(b))
                return i;
        }
        return -1;
    }
}
