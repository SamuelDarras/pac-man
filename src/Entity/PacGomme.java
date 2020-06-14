package Entity;

import Utils.Constants;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PacGomme extends Items{

    public PacGomme(double x, double y){
        super(10, x, y, Constants.ITEM_DEFAULT_WIDTH, Constants.ITEM_DEFAULT_HEIGHT);
    }

    //dessine les pac-gommes
    public void draw(GraphicsContext gc) {
        Color prev = (Color) gc.getFill();

        gc.setFill(Color.WHEAT);
        gc.fillOval(getPos().getX(), getPos().getY(), getHitbox()[0], getHitbox()[1]);

        gc.setFill(prev);
    }
}
