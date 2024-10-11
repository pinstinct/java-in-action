package chapter3;

import static constant.Colors.GREEN;
import static constant.Colors.RED;

import constant.Colors;
import domain.Apple;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MethodReferenceTest {

  List<Apple> inventory = Arrays.asList(new Apple(80, GREEN), new Apple(155, GREEN),
      new Apple(120, RED));

  @Test
  @DisplayName("메서드 참조")
  void test1() {
    // 위, 아래 동일한 코드 (메서드 참조 여부 차이)
    inventory.sort((a1, a2) -> a1.getWeight().compareTo(a2.getWeight()));
    inventory.sort(Comparator.comparing(Apple::getWeight));

    Function<Apple, Integer> function = (Apple apple) -> apple.getWeight();
    Function<Apple, Integer> getWeight = Apple::getWeight;

    Thread.dumpStack();
    Runnable stack = Thread::dumpStack;

    BiFunction<String, Integer, String> function1 = (String str, Integer i) -> str.substring(i);
    BiFunction<String, Integer, String> function2 = String::substring;
  }

  @Test
  @DisplayName("생성자 참조")
  void test2() {
    Supplier<Apple> s = () -> new Apple();
    Apple a2 = s.get();  // Supplier 의 get 메서드를 호출해서 새로운 객체 생성
    System.out.println(a2);

    Supplier<Apple> supplier = Apple::new;
    Apple a1 = supplier.get();
    System.out.println(a1);

    BiFunction<Integer, Colors, Apple> f = (weight, color) -> new Apple(weight, color);
    Apple a4 = f.apply(20, RED);  // BiFunction 의 apply 메서드에 무게와 색깔을 인수로 호출해서 새로운 객체 생성
    System.out.println(a4);

    BiFunction<Integer, Colors, Apple> function = Apple::new;
    Apple a3 = function.apply(10, GREEN);
    System.out.println(a3);
  }

  @Test
  @DisplayName("생성자 참조 - 여러개의 객체 생성")
  void test3() {
    List<Integer> weights = Arrays.asList(7, 3, 5);
    List<Apple> result = map(weights, Apple::new);
    System.out.println(result);
  }

  public List<Apple> map(List<Integer> weights, Function<Integer, Apple> f) {
    List<Apple> result = new ArrayList<>();
    for (Integer weight : weights) {
      result.add(f.apply(weight));
    }
    return result;
  }

  @Test
  @DisplayName("생성자 참조 - 인수가 3개인 생성자")
  void test4() {
    TriFunction<Integer, Integer, Colors, Apple> f = Apple::new;
    Apple apple = f.apply(100, 200, RED);
    System.out.println(apple);
  }

}
