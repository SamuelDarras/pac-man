package Entity;

public class PacGomme extends Items{

    private boolean afficheScore = false;


    public PacGomme(double x, double y){
        super(10, x, y);
    }

    public boolean isAfficheScore() {
        return afficheScore;
    }
}
