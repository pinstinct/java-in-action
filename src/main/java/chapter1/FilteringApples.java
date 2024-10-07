package chapter1;

import static constant.Colors.GREEN;

import domain.Apple;
import java.util.ArrayList;
import java.util.List;

public class FilteringApples {

  /**
   * 두 메서드는 한 줄의 if문 코드만 다르다.
   * */

  public static List<Apple> filterGreenApples(List<Apple> inventory) {
    List<Apple> result = new ArrayList<>();
    for (Apple apple : inventory) {
      if (GREEN.value().equals(apple.getColor())) {
        result.add(apple);
      }
    }
    return result;
  }

  public static List<Apple> filterHeavyApples(List<Apple> inventory) {
    List<Apple> result = new ArrayList<>();
    for (Apple apple : inventory) {
      if (apple.getWeight() > 150) {
        result.add(apple);
      }
    }
    return result;
  }
}
