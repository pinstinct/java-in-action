package chapter2;

import static constant.Colors.GREEN;

import constant.Colors;
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

  public static List<Apple> filterApplesByColor(List<Apple> inventory, Colors color) {
    List<Apple> result = new ArrayList<>();
    for (Apple apple : inventory) {
      if (apple.getColor().equals(color)) {
        result.add(apple);
      }
    }
    return result;
  }

  public static List<Apple> filterApples(List<Apple> inventory, Colors color, int weight,
      boolean flag) {
    List<Apple> result = new ArrayList<>();
    for (Apple apple : inventory) {
      if ((flag && apple.getColor().equals(color)) || (!flag && apple.getWeight() > weight)) {
        result.add(apple);
      }
    }
    return result;
  }

  /**
   * 전략 디자인 패턴: 각 알고리즘(전략이라 불리는)을 캡슐화하는 알고리즘 패밀리를 정의해둔 다음에 런타임에 알고리즘을 선택하는 기법이다.
   * {@code ApplePredicate}가 알고리즘 패밀리고, {@code AppleHeavyWeightPredicate}, {@code AppleGreenColorPredicate}가 전략이다.
   */
  public interface ApplePredicate {  // 사과 선택 전략을 캡슐화함

    boolean test(Apple apple);
  }

  static class AppleHeavyWeightPredicate implements ApplePredicate {  // 무거운 사과만 선택

    @Override
    public boolean test(Apple apple) {
      return apple.getWeight() > 150;
    }
  }

  static class AppleGreenColorPredicate implements ApplePredicate {  // 녹색 사과만 선택

    @Override
    public boolean test(Apple apple) {
      return GREEN.equals(apple.getColor());
    }

  }
}
