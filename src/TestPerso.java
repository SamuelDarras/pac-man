import Entity.*;
import Utils.*;

public class TestPerso {
  public static void main(String[] args) {
    Personnage t1 = new Personnage(10, 10, 2);

    for (int i = 0; i < 10; i++) {
      t1.tick();
      System.out.println(t1);
      if (i == 4)
        t1.changeDir(Direction.UP);
    }
  }
}
