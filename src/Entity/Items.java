package Entity;

import Utils.Constants;

public class Items extends Entity{

    private int score;

    public Items(int score, double x, double y){
        super(x, y, Constants.ITEM_DEFAULT_SIZE, Constants.ITEM_DEFAULT_SIZE);
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}
