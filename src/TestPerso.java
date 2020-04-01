import Entity.*;
import Utils.*;

public class TestPerso {
  public static void main(String[] args) {
    Blinky bl = new Blinky(10, 10, 2);

    for (int i = 0; i < 10; i++) {
      bl.tick();
      System.out.println(bl);
    }
  }
}
