package Entity;

import Utils.Constants;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Fruit extends Items{

    private Image img;

    public Fruit(int score, double x, double y){
        super(score, x, y);

        setHitbox(Constants.FRUIT_SIZE, Constants.FRUIT_SIZE);

    }
    public void setImg(Image img) {
        this.img = img;
    }

    public void draw(GraphicsContext gc){
        gc.drawImage(img, getPos().getX(), getPos().getY(), getHitbox()[0], getHitbox()[1]);
    }
}
