package Entity;

public class Fruit extends Items{

    private boolean afficheScore = true;
    private enum Fruits {cerise,fraise, pomme, orange}
    private Fruits typeFruit;

    public Fruit(int score, double x, double y){
        super(score, x, y);
    }

    public boolean isAfficheScore() {
        return afficheScore;
    }

    public Fruits GetFruits(){
        return typeFruit;
    }
}
