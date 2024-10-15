package chapter5;

import constant.Type;
import domain.Dish;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("숫자형 스트림")
public class NumberStreamTest {

  List<Dish> menus = Arrays.asList(new Dish("pork", false, 800, Type.MEAT),
      new Dish("beef", false, 700, Type.MEAT), new Dish("chicken", false, 400, Type.MEAT),
      new Dish("french fries", true, 530, Type.OTHER), new Dish("rice", true, 350, Type.OTHER),
      new Dish("season fruit", true, 120, Type.OTHER), new Dish("pizza", true, 550, Type.OTHER),
      new Dish("prawns", false, 300, Type.FISH), new Dish("salmon", false, 450, Type.FISH));

  @Test
  @DisplayName("문제점 - 박싱 비용")
  void test1() {
    Integer result = menus.stream()
        .map(Dish::getCalories)
        .reduce(0, Integer::sum);  // Integer를 기본형으로 언박싱
    System.out.println(result);
  }

  @Test
  @DisplayName("숫자 스트림으로 매핑")
  void test2() {
    int calories = menus.stream()
        .mapToInt(Dish::getCalories)  // IntStream 반환
        .sum();
    System.out.println(calories);
  }

  @Test
  @DisplayName("객체 스트림으로 복원하기")
  void test3() {
    IntStream intStream = menus.stream()
        .mapToInt(Dish::getCalories);
    Stream<Integer> stream = intStream.boxed();
  }

  @Test
  @DisplayName("기본값 - OptionalInt")
  void test4() {
    OptionalInt maxCalories = menus.stream()
        .mapToInt(Dish::getCalories)
        .max();
    int max = maxCalories.orElse(1);  // 값이 없을 때 기본 최대값을 명시적으로 설정
    System.out.println(max);
  }

  @Test
  @DisplayName("숫자 범위")
  void test5() {
    IntStream evenNumbers = IntStream.rangeClosed(1, 100)  // closed는 시작값과 종료값이 결과에 포함
        .filter(n -> n % 2 == 0);
    System.out.println(evenNumbers.count());
  }
}
