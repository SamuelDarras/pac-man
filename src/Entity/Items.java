package Entity;

import Utils.Constants;

public class Items extends Entity{

    private int score;

    public Items(int score, double x, double y){
        super(x, y, Constants.ITEM_DEFAULT_WIDTH, Constants.ITEM_DEFAULT_HEIGHT);
        this.score = score;
    }
    public Items(int score, double x, double y, double w, double h){
        super(x, y, w, h);
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}
