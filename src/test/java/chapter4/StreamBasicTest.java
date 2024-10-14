package chapter4;

import static java.util.Comparator.comparing;

import constant.Type;
import domain.Dish;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StreamBasicTest {

  List<Dish> menus = Arrays.asList(
      new Dish("pork", false, 800, Type.MEAT),
      new Dish("beef", false, 700, Type.MEAT),
      new Dish("chicken", false, 400, Type.MEAT),
      new Dish("french fries", true, 530, Type.OTHER),
      new Dish("rice", true, 350, Type.OTHER),
      new Dish("season fruit", true, 120, Type.OTHER),
      new Dish("pizza", true, 550, Type.OTHER),
      new Dish("prawns", false, 300, Type.FISH),
      new Dish("salmon", false, 450, Type.FISH)
  );

  @Test
  @DisplayName("자바 8 이전 코드")
  void test1() {
    List<Dish> lowCaloricDishes = new ArrayList<>();
    for (Dish dish : menus) {
      if (dish.getCalories() < 400) {
        lowCaloricDishes.add(dish);
      }
    }
    Collections.sort(lowCaloricDishes, new Comparator<Dish>() {  // 익명 클래스로 요리 정렬
      @Override
      public int compare(Dish o1, Dish o2) {
        return Integer.compare(o1.getCalories(), o2.getCalories());
      }
    });

    List<String> lowCaloricDishesName = new ArrayList<>();
    for (Dish dish : lowCaloricDishes) {
      lowCaloricDishesName.add(dish.getName());  // 정렬된 리스트를 처리하면서 요리 이름 선택
    }
    System.out.println(lowCaloricDishesName);
  }

  @Test
  @DisplayName("자바 8 이후 코드")
  void test2() {
    List<String> lowCaloricDishesName = menus.stream()
        .filter(dish -> dish.getCalories() < 400)  // 400 칼로리 이하의 요리 선택
        .sorted(comparing(Dish::getCalories))  // 칼로리로 요리 정렬
        .map(Dish::getName)  // 요리명 추출
        .toList();  // 요리명을 리스트에 저장
    System.out.println(lowCaloricDishesName);
  }

  @Test
  @DisplayName("자바 8 이후 코드 - 병렬 실행")
  void test3() {
    List<String> lowCaloricDishesName = menus.parallelStream()
        .filter(dish -> dish.getCalories() < 400)
        .sorted(comparing(Dish::getCalories))
        .map(Dish::getName)
        .toList();
    System.out.println(lowCaloricDishesName);
  }
}