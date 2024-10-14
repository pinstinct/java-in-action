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
    @DisplayName("predicate")
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

  @Nested
  @DisplayName("스트림 슬라이싱")
  class Slicing {

    // 칼로리 순으로 정렬된 리스트
    List<Dish> specialMenu = Arrays.asList(
        new Dish("seasonal fruit", true, 120, Type.OTHER),
        new Dish("prawns", false, 300, Type.FISH),
        new Dish("rice", true, 350, Type.OTHER),
        new Dish("chicken", false, 400, Type.MEAT),
        new Dish("french fries", true, 530, Type.OTHER)
    );

    @Test
    @DisplayName("predicate - takeWhile")
    void test1() {
      List<Dish> sliceMenu = specialMenu.stream()
          .takeWhile(dish -> dish.getCalories() < 320)
          .toList();
      System.out.println(sliceMenu);
    }

    @Test
    @DisplayName("predicate - dropWhile")
    void test2() {
      List<Dish> dishes = specialMenu.stream()
          .dropWhile(dish -> dish.getCalories() < 320)  // 거짓이 되는 지점에서 작업 중단하고, 발견된 요소를 버린다.
          .toList();
      System.out.println(dishes);
    }

    @Test
    @DisplayName("스트림 축소")
    void test3() {
      List<Dish> dishes = specialMenu.stream()
          .filter(dish -> dish.getCalories() > 300)
          .limit(3)
          .toList();
      System.out.println(dishes);
    }

    @Test
    @DisplayName("요소 건너뛰기")
    void test4() {
      List<Dish> dishes = menus.stream()
          .filter(dish -> dish.getCalories() > 300)
          .skip(2)  // 처음 n개 요소를 제외한 스트림 반환
          .toList();
      System.out.println(dishes);
    }

    @Test
    @DisplayName("처음 등장하는 두 고기 요리를 필터링")
    void test5() {
      List<Dish> dishes = menus.stream()
          .filter(menu -> menu.getType().equals(Type.MEAT))
          .limit(2)
          .toList();
      System.out.println(dishes);
    }
  }
}
