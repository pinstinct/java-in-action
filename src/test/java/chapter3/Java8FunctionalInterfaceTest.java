package chapter3;

import static chapter3.Java8FunctionalInterface.filter;
import static chapter3.Java8FunctionalInterface.forEach;
import static chapter3.Java8FunctionalInterface.map;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class Java8FunctionalInterfaceTest {

  @Test
  @DisplayName("Predicate")
  void test1() {
    Predicate<String> nonEmptyStringPredicate = (String s) -> !s.isEmpty();
    List<String> nonEmpty = filter(new ArrayList<>(), nonEmptyStringPredicate);
    System.out.println(nonEmpty);
  }

  @Test
  @DisplayName("Consumer")
  void test2() {
    forEach(Arrays.asList(1, 2, 3, 4, 5), (Integer i) -> System.out.println(i));
  }

  @Test
  @DisplayName("Function")
  void test3() {
    List<Integer> result = map(Arrays.asList("lambdas", "in", "action"), (String s) -> s.length());
    System.out.println(result);
  }

  @Test
  @DisplayName("기본형 특화")
  void test4() {
    // 1000은 기본적으로 int 타입

    IntPredicate evenNumbers = (int i) -> i % 2 == 0;
    System.out.println();
    boolean result = evenNumbers.test(1000);  // 1000이라는 값을 박싱하지 않음
    System.out.println(result);

    Predicate<Integer> oddNumbers = (Integer i) -> i % 2 != 0;
    boolean result2 = oddNumbers.test(1000);  // 1000이라는 값을 Integer 객체로 박싱
    System.out.println(result2);
  }
}