package Entity;

import Utils.Sizes;

public class Items extends Entity{

    private int score;

    public Items(int score, double x, double y){
        super(x, y, Sizes.ITEM_DEFAULT_SIZE, Sizes.ITEM_DEFAULT_SIZE);
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}
