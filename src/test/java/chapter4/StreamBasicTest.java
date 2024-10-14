package chapter4;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

import constant.Type;
import domain.Dish;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
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

  @Test
  @DisplayName("스트림 시작하기 예")
  void test4() {
    // 데이터 소스: menus
    List<String> threeHighCaloricDishNames = menus.stream()
        .filter(dish -> dish.getCalories() > 300)
        .map(Dish::getName)
        .limit(3)
        .collect(toList());  // 파이프라인을 처리해 결과 반환(collect 를 호출하기 전까지 menu 에서 무엇도 선택되지 않으며 출력 결과도 없다.)
    System.out.println(threeHighCaloricDishNames);
  }

  @Test
  @DisplayName("스트림과 컬렉션 예제")
  void test5() {
    // as-is
    List<String> result = new ArrayList<>();
    Iterator<Dish> iterator = menus.iterator();
    while (iterator.hasNext()) {
      Dish dish = iterator.next();
      if (dish.getCalories() > 300) {
        result.add(dish.getName());
      }
    }
    System.out.println(result);

    // to-be
    List<String> highCaloricDishes = menus.stream()
        .filter(menu -> menu.getCalories() > 300)
        .map(Dish::getName)
        .toList();
    System.out.println(highCaloricDishes);
  }
}
