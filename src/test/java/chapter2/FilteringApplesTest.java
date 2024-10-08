package chapter2;

import static chapter2.FilteringApples.filterApplesByColor;
import static chapter2.FilteringApples.filterGreenApples;
import static constant.Colors.GREEN;
import static constant.Colors.RED;
import static org.assertj.core.api.Assertions.assertThat;

import domain.Apple;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FilteringApplesTest {

  List<Apple> inventory = Arrays.asList(
      new Apple(80, GREEN),
      new Apple(155, GREEN),
      new Apple(120, RED)
  );

  @Test
  @DisplayName("첫 번째 시도: 녹색 사과 필터링")
  void test1() {
    List<Apple> apples = filterGreenApples(inventory);
    assertThat(apples.size()).isEqualTo(2);
    System.out.println(apples);
  }

  @Test
  @DisplayName("두 번째 시도: 색을 파라미터화")
  void test2() {
    List<Apple> apples = filterApplesByColor(inventory, RED);
    assertThat(apples.size()).isEqualTo(1);
    System.out.println(apples);
  }
}