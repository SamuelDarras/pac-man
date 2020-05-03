package Entity;

public class Items extends Entity{

    private int score;

    public Items(int score, double x, double y){
        super(x, y, 1, 1);
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}
