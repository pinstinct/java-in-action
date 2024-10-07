package chapter1;

import static chapter1.FilteringApples.filterGreenApples;
import static chapter1.FilteringApples.filterHeavyApples;
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
    List<Apple> result = filterGreenApples(inventory);
    System.out.println(result);
    assertThat(result.size()).isEqualTo(2);
  }

  @Test
  void filterHeavyApplesTest() {
    List<Apple> result = filterHeavyApples(inventory);
    System.out.println(result);
    assertThat(result.size()).isEqualTo(1);
  }


}