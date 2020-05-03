package Entity;

public class SuperPacGomme extends Items{

    private boolean afficheScore = true;

    public SuperPacGomme(double x, double y){
       super(100, x, y);
    }

    public boolean isAfficheScore() {
        return afficheScore;
    }
}
