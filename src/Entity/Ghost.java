package Entity;

import Game.Plateau;
import Utils.Constants;
import Utils.Direction;
import Utils.Position;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public abstract class Ghost extends Personnage {
    Image img;
    Image alteredImg;

    Position gotoPos;
    ArrayList<Position> path;

    boolean frightened = false;
    boolean dead = false;
    boolean alreadyDied = false;

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

    public void draw(GraphicsContext gc) {
        Image toDraw = alteredImg == null ? img : alteredImg;
        if (getDir() == Direction.LEFT)
            gc.drawImage(toDraw, getPos().getX() + getHitbox()[0], getPos().getY(), -getHitbox()[0], getHitbox()[1]);
        else
            gc.drawImage(toDraw, getPos().getX(), getPos().getY(), getHitbox()[0], getHitbox()[1]);
        if (path != null && path.size() > 0) {
            Position prev = path.get(0).copy();
            for (Position pos : path) {
                gc.setStroke(Color.RED);
                gc.setLineWidth(5);
                gc.strokeLine((int) prev.getX()*Constants.WALL_WIDTH + Constants.WALL_WIDTH*.5, (int) prev.getY()*Constants.WALL_HEIGHT + Constants.WALL_HEIGHT*.5, (int) pos.getX()*Constants.WALL_WIDTH + Constants.WALL_WIDTH*.5, (int) pos.getY()*Constants.WALL_HEIGHT + Constants.WALL_HEIGHT*.5);
                prev = pos.copy();
            }
        }
        if (gotoPos != null) {
            gc.setFill(Color.RED);
            gc.fillOval((int) gotoPos.getX() * Constants.WALL_WIDTH, (int) gotoPos.getY() * Constants.WALL_HEIGHT, 10, 10);
        }
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
        if (getNeighbours(getGridPos(), plat).size() > 1) {
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


        if (pac.superPacman && !frightened && !alreadyDied) {
            frightened = true;
        }
        if (!pac.superPacman) {
            frightened = false;
            alreadyDied = false;
        }

        alteredImg = null;
        if (frightened)
            alteredImg = new Image("img/DeadGhost.png");
        if (dead)
            alteredImg = new Image("img/EatenGhost.png");

        if (dead) {
            if (getGridPos().equals(plat.getHouse())) {
                dead = false;
                alreadyDied = true;
                frightened = false;
                resetSpeed();
                return Direction.LEFT;
            }
            addSpeed(getSpeed());
            gotoPos = plat.getHouse();
            return getDirectionAccordingToPath(BreadthFirst(getGridPos(), gotoPos, plat));
        }
        if (frightened && !getGridPos().equals(new Position(curx, cury))) {
            curx = (int) getGridPos().getX();
            cury = (int) getGridPos().getY();
            int x = (int)(Math.random()*plat.getLargeur());
            int y = (int)(Math.random()*plat.getHauteur());
            while (plat.getCell(x, y) instanceof Wall || Math.abs(x - (int) pac.getGridPos().getX()) < 4 || Math.abs(y - (int) pac.getGridPos().getY()) < 4 || getGridPos().equals(new Position(x, y))) {
                x = (int)(Math.random()*plat.getLargeur());
                y = (int)(Math.random()*plat.getHauteur());
            }
            gotoPos = new Position(x, y);
            return getDirectionAccordingToPath(BreadthFirst(getGridPos(), gotoPos, plat));
        }

        return null;
    }
}
