package chapter2;

import domain.Apple;
import java.util.List;

public class PrintApples {

  static void prettyPrintApple(List<Apple> inventory, AppleFormatter formatter) {
    for (Apple apple : inventory) {
      String output = formatter.accept(apple);
      System.out.println(output);
    }
  }

  interface AppleFormatter {
    String accept(Apple apple);
  }

  static class AppleFancyFormatter implements AppleFormatter {

    @Override
    public String accept(Apple apple) {
      String characteristic = apple.getWeight() > 150 ? "heavy" : "light";
      return "A " + characteristic + " " + apple.getColor().toString().toLowerCase() + " apple";
    }
  }

  static class AppleSimpleFormatter implements AppleFormatter {

    @Override
    public String accept(Apple apple) {
      return "An apple of " + apple.getWeight() + "g";
    }
  }
}
