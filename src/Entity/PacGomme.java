package Entity;

import Utils.Constants;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PacGomme extends Items{

    private boolean afficheScore = false;


    public PacGomme(double x, double y){
        super(10, x, y, Constants.ITEM_DEFAULT_SIZE, Constants.ITEM_DEFAULT_SIZE);
    }

    public boolean isAfficheScore() {
        return afficheScore;
    }

    public void draw(GraphicsContext gc) {
        Color prev = (Color) gc.getFill();

        gc.setFill(Color.WHEAT);
        gc.fillOval(getPos().getX(), getPos().getY(), getHitbox()[0], getHitbox()[1]);

        gc.setFill(prev);
    }
}
