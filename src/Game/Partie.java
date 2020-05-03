package Game;

public class Partie {
    private Score score;
    public Partie() {
        score = new Score();
    }

    public void scoreAdd(int s) {
        score.scoreAdd(s);
    }
}
