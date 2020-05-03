package Game;

import Entity.*;

public class Partie {
    private Score score;


    private boolean action = true;
    boolean quitter = false;
    long total = 0;


    public Partie() {
        score = new Score();
    }

    public void run() throws Exception {
        Plateau p1 = new Plateau("src/levels/level1V2.txt");
        int sec = 0;

        Pacman pactest = new Pacman(10, 10);

        while (!quitter) {
            action = true;
            long start = System.currentTimeMillis();
            //System.out.println(total);
            long finish = System.currentTimeMillis();
            total += finish - start;

            if(total/1000 != sec) {
                sec = (int) total/1000;
                System.out.println(sec);
            }



        }
    }

    public void scoreAdd(int s) {
        score.scoreAdd(s);
    }

    interface Callback {
        void call();
    }
    private void doSomethingAt(long timing, Callback callback) {
        if (action && total >= timing && total <= timing + 100) {
            callback.call();
            action = false;
        }
    }
}

