package chapter5;

import constant.Type;
import domain.Dish;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class StreamTest {

  List<Dish> menus = Arrays.asList(new Dish("pork", false, 800, Type.MEAT),
      new Dish("beef", false, 700, Type.MEAT), new Dish("chicken", false, 400, Type.MEAT),
      new Dish("french fries", true, 530, Type.OTHER), new Dish("rice", true, 350, Type.OTHER),
      new Dish("season fruit", true, 120, Type.OTHER), new Dish("pizza", true, 550, Type.OTHER),
      new Dish("prawns", false, 300, Type.FISH), new Dish("salmon", false, 450, Type.FISH));

  @Nested
  @DisplayName("필터링")
  public class Filtering {

    @Test
    @DisplayName("filter")
    void test1() {
      List<Dish> vegetarianMenu = menus.stream()
          .filter(Dish::isVegetarian)
          .toList();
      System.out.println(vegetarianMenu);
    }

    @Test
    @DisplayName("distinct")
    void test2() {
      List<Integer> numbers = Arrays.asList(1, 2, 1, 3, 3, 2, 4);
      numbers.stream()
          .filter(i -> i % 2 == 0)
          .distinct()
          .forEach(System.out::println);
    }
  }
}
