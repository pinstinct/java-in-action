package chapter1;

import static chapter1.FilteringApples.filterApples;
import static constant.Colors.GREEN;
import static org.assertj.core.api.Assertions.assertThat;

import domain.Apple;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class FilteringApplesTest {

  List<Apple> inventory = Arrays.asList(
      new Apple(80, "green"),
      new Apple(155, "green"),
      new Apple(120, "red"));

  @Test
  void filterGreenApplesTest() {
    List<Apple> result = filterApples(inventory, FilteringApples::isGreenApple);
    System.out.println(result);
    assertThat(result.size()).isEqualTo(2);
  }

  @Test
  void filterHeavyApplesTest() {
    List<Apple> result = filterApples(inventory, FilteringApples::isHeavyApple);
    System.out.println(result);
    assertThat(result.size()).isEqualTo(1);
  }

  @Test
  void filterGreenApplesWithLambdaTest() {
    List<Apple> apples = filterApples(inventory, apple -> GREEN.value().equals(apple.getColor()));
    assertThat(apples.size()).isEqualTo(2);
    System.out.println(apples);
  }

  @Test
  void filterHeavyApplesWithLambdaTest() {
    List<Apple> apples = filterApples(inventory, apple -> apple.getWeight() > 150);
    assertThat(apples.size()).isEqualTo(1);
    System.out.println(apples);
  }

  /**
   * 라이브러리 메서드 filter 를 이용하면 filterApples 메서드를 구현할 필요가 없다.
   * */
  @Test
  void filterHeavyApplesWithLibraryTest() {
    List<Apple> apples = inventory.stream().filter(apple -> apple.getWeight() > 150).toList();
    assertThat(apples.size()).isEqualTo(1);
    System.out.println(apples);
  }
}