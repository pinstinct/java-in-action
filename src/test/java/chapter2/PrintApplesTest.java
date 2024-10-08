package chapter2;

import static chapter2.PrintApples.prettyPrintApple;

import chapter2.PrintApples.AppleFancyFormatter;
import chapter2.PrintApples.AppleSimpleFormatter;
import constant.Colors;
import domain.Apple;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class PrintApplesTest {

  List<Apple> inventory = Arrays.asList(
      new Apple(80, Colors.GREEN),
      new Apple(155, Colors.GREEN),
      new Apple(120, Colors.RED)
  );

  @Test
  void test1() {
    prettyPrintApple(inventory, new AppleFancyFormatter());
    System.out.println("===");
    prettyPrintApple(inventory, new AppleSimpleFormatter());
  }
}