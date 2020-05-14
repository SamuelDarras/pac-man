package Entity;

import Utils.Position;
import Utils.Constants;
import javafx.scene.image.Image;

public class Wall extends Entity{

    private Image img;

    public Wall(double x, double y, double w, double h, Image img) {
        super(x, y, w, h);
        this.img = img;
    }
    public Wall(double x, double y, double w, double h) {
        super(x, y, w, h);
    }
    public Wall(Position<Double> p, Image img) {
        super(p, Constants.WALL_WIDTH, Constants.WALL_HEIGHT);
        this.img = img;
    }
    public Wall(Position<Double> p) {
        super(p, Constants.WALL_WIDTH, Constants.WALL_HEIGHT);
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public Image getImg() {
        return img;
    }
}
