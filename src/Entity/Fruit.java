package Entity;

import Utils.Constants;
import com.sun.webkit.graphics.WCGraphicsContext;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Random;

public class Fruit extends Items{

    private boolean afficheScore = true;
    private Image img;
    private String typeFruit;

    public Fruit(int score,double x, double y,String typeFruit){
        super(score, x, y);
        this.typeFruit=typeFruit;

        setHitbox(Constants.FRUIT_SIZE, Constants.FRUIT_SIZE);
    }
    public void setImg(Image img) {
        this.img = img;
    }

    public boolean isAfficheScore() {
        return afficheScore;
    }

    public String GetFruits(){
        return typeFruit;
    }
    public void draw(GraphicsContext gc){
        gc.drawImage(img, getPos().getX(), getPos().getY(), getHitbox()[0], getHitbox()[1]);
    }
}
