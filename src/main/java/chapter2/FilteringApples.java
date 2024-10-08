package chapter2;

import static constant.Colors.GREEN;

import domain.Apple;
import java.util.ArrayList;
import java.util.List;

public class FilteringApples {

  public static List<Apple> filterGreenApples(List<Apple> inventory) {
    List<Apple> result = new ArrayList<>();
    for (Apple apple : inventory) {
      if (GREEN.equals(apple.getColor())) {
        result.add(apple);
      }
    }
    return result;
  }

}
