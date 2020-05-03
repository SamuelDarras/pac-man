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

    public void scoreAdd(int s) {
        score.scoreAdd(s);
    }

    public void run() throws Exception {
        Plateau p1 = new Plateau("/home/samuel/pacman/src/levels/level1V2.txt");


        int sec = 0;

        while (!quitter) {
            long start = System.currentTimeMillis();
            //System.out.println(total);
            long finish = System.currentTimeMillis();
            total += finish - start;


            if (sec != total / 1000) {
                sec = (int) (total / 1000);
                System.out.println(sec);
                action = true;
            }

            doSomthingAt(1000, () -> {
                if ( !(p1.getIndex(p1.getIdxFruit()) instanceof Fruit) ) {
                    System.out.println("OOF !!");
                    p1.setIndex(p1.getIdxFruit(), new Fruit(200, 10, 10));
                }
            });

            doSomthingAt(3000, () -> quitter = true);

        }
    }

    private void doSomthingAt(long timing, Callback callback) {
        if (action && total == timing) {
            callback.call();
            action = false;
        }
    }
}

interface Callback {
    void call();
}