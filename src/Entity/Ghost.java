package Entity;

import Game.Plateau;
import Utils.Direction;
import Utils.Position;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;

public abstract class Ghost extends Personnage {
    Image img;
    Image alteredImg;

    Position<Integer> gotoPos;
    ArrayList<Position<Integer>> path;

    boolean frightened = false;
    boolean dead = false;
    boolean alreadyDied = false;


    private final String skin;

    public Ghost(double x, double y, double baseSpeed, String skin) {
        super(x, y, baseSpeed);
        this.skin = skin;
    }

    int curx = -1;
    int cury = -1;

    //donne la direction à prendre selon le chemin calculé
    public Direction getDirectionAccordingToPath(ArrayList<Position<Integer>> path) {

        if (path.size() > 0) {
            int p0x = path.get(path.size() - 1).getX();
            int p0y = path.get(path.size() - 1).getY();

            int curx = getGridPos().getX();
            int cury = getGridPos().getY();

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
    }

    //algorithme de recherche de chemin
    public ArrayList<Position<Integer>> BreadthFirst(Position<Integer> start, Position<Integer> end, Plateau plat) {
        ArrayList<Position<Integer>> frontier = new ArrayList<>();
        frontier.add(start);

        ArrayList<Position<Integer>> came_from_from = new ArrayList<>();
        ArrayList<Position<Integer>> came_from_to = new ArrayList<>();

        Position<Integer> current;

        Plateau platCpy = plat.simpleCopy();
        int curx = getGridPos().getX();
        int cury = getGridPos().getY();
        if (getNeighbours(getGridPos(), plat).size() >= 2) {
            switch (getDir()) {
                case UP     -> platCpy.setCell(new Wall(platCpy.getCell(curx, cury + 1).getPos().getX(), platCpy.getCell(curx, cury + 1).getPos().getY()), curx, cury + 1);
                case DOWN   -> platCpy.setCell(new Wall(platCpy.getCell(curx, cury - 1).getPos().getX(), platCpy.getCell(curx, cury - 1).getPos().getY()), curx, cury - 1);
                case LEFT   -> platCpy.setCell(new Wall(platCpy.getCell(curx + 1, cury).getPos().getX(), platCpy.getCell(curx + 1, cury).getPos().getY()), curx + 1, cury);
                case RIGHT  -> platCpy.setCell(new Wall(platCpy.getCell(curx - 1, cury).getPos().getX(), platCpy.getCell(curx - 1, cury).getPos().getY()), curx - 1, cury);
            }
        }

        while (frontier.size() > 0) {
            current = frontier.get(0);
            frontier.remove(0);

            if (current.equals(end))
                break;

            for (Position<Integer> next : getNeighbours(current, platCpy)) {
                if (!in(came_from_to, next)) {
                    frontier.add(next);
                    came_from_to.add(next.copy());
                    came_from_from.add(current.copy());
                }
            }
        }

        current = end;
        ArrayList<Position<Integer>> path = new ArrayList<>();
        while (!current.equals(start)) {
            path.add(current.copy());
            if (index(came_from_to, current) == -1)
                return new ArrayList<>();
            current = came_from_from.get(index(came_from_to, current));
        }
        return path;
    }

    public ArrayList<Position<Integer>> getNeighbours(Position<Integer> current, Plateau plat) {
        ArrayList<Position<Integer>> ret = new ArrayList<>();
        int curx = current.getX();
        int cury = current.getY();
        if (curx - 1 >= 0 && !(plat.getCell(curx - 1, cury) instanceof Wall))
            ret.add(new Position<>(curx - 1, cury));
        if (cury - 1 >= 0 && !(plat.getCell(curx, cury - 1) instanceof Wall))
            ret.add(new Position<>(curx, cury - 1));
        if (curx + 1 < plat.getLargeur() && !(plat.getCell(curx + 1, cury) instanceof Wall))
            ret.add(new Position<>(curx + 1, cury));
        if (cury + 1 < plat.getHauteur() && !(plat.getCell(curx, cury + 1) instanceof Wall))
            ret.add(new Position<>(curx, cury + 1));
        return ret;
    }

    private boolean in(ArrayList<Position<Integer>> a, Position<Integer> b) {
        for (Position<Integer> p : a)
            if (b != null && p.equals(b))
                return true;
        return false;
    }

    private int index(ArrayList<Position<Integer>> a, Position<Integer> b) {
        for (int i = 0; i < a.size(); i++) {
            if (a.get(i).equals(b))
                return i;
        }
        return -1;
    }

    //modifie la direction selon l'état du jeu
    public Direction alterDirection(Pacman pac, Plateau plat) {
        if (curx == -1 || cury == -1) {
            curx = getGridPos().getX();
            cury = getGridPos().getY();
        }


        if (pac.superPacman && !frightened && !alreadyDied) {
            frightened = true;
        }
        if (!pac.superPacman) {
            frightened = false;
            dead = false;
            alreadyDied = false;
        }

        alteredImg = null;
        if (frightened)
            alteredImg = new Image("img/Pacman/" + skin + "/DeadGhost.png");
        if (dead)
            alteredImg = new Image("img/Pacman/classic/EatenGhost.png");

        if (dead) {
            if (getGridPos().equals(plat.getHouse())) {
                dead = false;
                alreadyDied = true;
                frightened = false;
                resetSpeed();
                resetPosition();
                return getDir();
            }
            addSpeed(getSpeed());
            gotoPos = plat.getHouse();
            return getDirectionAccordingToPath(BreadthFirst(getGridPos(), gotoPos, plat));
        }
        if (frightened && !getGridPos().equals(new Position<>(curx, cury))) {
            curx = getGridPos().getX();
            cury = getGridPos().getY();
            int x = (int) (Math.random() * plat.getLargeur());
            int y = (int) (Math.random() * plat.getHauteur());
            while (plat.getCell(x, y) instanceof Wall || Math.abs(x - pac.getGridPos().getX()) < 4 || Math.abs(y - pac.getGridPos().getY()) < 4 || getGridPos().equals(new Position<>(x, y))) {
                x = (int) (Math.random() * plat.getLargeur());
                y = (int) (Math.random() * plat.getHauteur());
            }
            gotoPos = new Position<>(x, y);
            return getDirectionAccordingToPath(BreadthFirst(getGridPos(), gotoPos, plat));
        }
        resetSpeed();
        return getDirectionAccordingToPath(BreadthFirst(getGridPos(), pac.getGridPos(), plat));
    }
}
