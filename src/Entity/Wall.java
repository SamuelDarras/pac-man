package Entity;

import Utils.Constants;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Wall extends Entity{

    private Image img;

    public Wall(double x, double y) {
        super(x, y, Constants.WALL_WIDTH, Constants.WALL_HEIGHT);
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public void draw(GraphicsContext gc) {
        if (img != null)
            gc.drawImage(img, getPos().getX(), getPos().getY(), getHitbox()[0], getHitbox()[1]);
        else {
            Color prev = (Color) gc.getFill();

            gc.setFill(Color.DARKBLUE);
            gc.fillRect(getPos().getX(), getPos().getY(), getHitbox()[0], getHitbox()[1]);

            gc.setFill(prev);
        }
    }
}
