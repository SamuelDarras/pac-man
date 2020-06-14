package Entity;

import Utils.Constants;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class SuperPacGomme extends Items{

    private boolean afficheScore = true;

    public SuperPacGomme(double x, double y){
       super(100, x, y, Constants.ITEM_DEFAULT_WIDTH*2, Constants.ITEM_DEFAULT_HEIGHT*2);
    }

    public boolean isAfficheScore() {
        return afficheScore;
    }

    //dessine les super pac-gommes
    public void draw(GraphicsContext gc) {
        Color prev = (Color) gc.getFill();

        gc.setFill(Color.WHEAT);
        gc.fillOval(getPos().getX(), getPos().getY(), getHitbox()[0], getHitbox()[1]);

        gc.setFill(prev);
    }
}
