package Game;

import Entity.Fruit;

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


        while (!quitter) {
            long start = System.currentTimeMillis();
            //System.out.println(total);
            long finish = System.currentTimeMillis();
            total += finish - start;

            if(total/1000 != sec) {
                sec = (int) total/1000;
                System.out.println(sec);
            }

            action = true;

            doSomethingAt(1000, () -> {
                if ( !(p1.getIndex(p1.getIdxFruit()) instanceof Fruit) ) {
                    p1.setIndex(p1.getIdxFruit(), new Fruit(200, 10, 10));
                }
            });

            doSomethingAt(3000, () -> {
                quitter = true;
            });

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

