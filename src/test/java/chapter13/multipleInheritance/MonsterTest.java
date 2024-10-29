package chapter13.multipleInheritance;

import org.junit.jupiter.api.Test;

class MonsterTest {

  @Test
  void test1() {
    Monster m = new Monster();
    m.rotateBy(180);
    m.moveVertically(10);
  }
}