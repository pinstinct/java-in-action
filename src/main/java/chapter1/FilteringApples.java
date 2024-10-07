package chapter1;

import static constant.Colors.GREEN;

import domain.Apple;
import java.util.ArrayList;
import java.util.List;

public class FilteringApples {

  public static boolean isGreenApple(Apple apple) {
    return GREEN.value().equals(apple.getColor());
  }

  public static boolean isHeavyApple(Apple apple) {
    return apple.getWeight() > 150;
  }

  // 명확히 하기 위해 추가함(보통 java.util.function 에서 import)
  public interface Predicate<T> {
    boolean test(T t);
  }

  /**
   * 프레디케이트(predicate)란? 수학에서는 인수로 값을 받아 true 혹은 false 를 반환하는 함수를 프레디케이트라고 한다. 자바 8에서도
   * {@code Function<Apple, Boolean>} 같이 코드를 구현할 수 있지만 {@code Predicate<Apple>}을 사용하는 것이 더 표준적인
   * 방식이다. (boolean을 Boolean으로 변환하는 과정이 없으므로 더 효율적이기도 하다.)
   */
  static List<Apple> filterApples(List<Apple> inventory,
      Predicate<Apple> p) {  // 메서드가 p라는 이름의 프레디케이트 파라미터로 전달
    List<Apple> result = new ArrayList<>();
    for (Apple apple : inventory) {
      if (p.test(apple)) {  // 사과는 p가 제시하는 조건에 맞는지 확인
        result.add(apple);
      }
    }
    return result;
  }
}
