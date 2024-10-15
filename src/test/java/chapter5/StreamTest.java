package chapter5;

import constant.Type;
import domain.Dish;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
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

  @Nested
  @DisplayName("매핑")
  class Mapping {

    @Test
    @DisplayName("스트림의 각 요소에 함수 적용하기")
    void test1() {
      List<String> dishNames = menus.stream()
          .map(Dish::getName)
          .toList();
      System.out.println(dishNames);

      List<String> words = Arrays.asList("Modern", "Java", "In", "Action");
      List<Integer> wordLengths = words.stream()
          .map(String::length)
          .toList();
      System.out.println(wordLengths);
    }
  }

  @Nested
  @DisplayName("스트림 평면화 - 리스트에서 고유 문자로 이루어진 리스트 반환하기")
  class Flatten {

    String[] arrayOfWords = {"Hello", "World"};

    @Test
    @DisplayName("문제점 - map을 이용해서 단어 리스트에서 고유 문자를 찾는데 실패")
    void test1() {
      List<String[]> unique = Arrays.stream(arrayOfWords)
          // String[](문자열 배열)을 반환한다는 점이 문제, Stream<String>이 필요
          .map(word -> {
            System.out.println(word);
            return word.split("");
          })
          .distinct()
          .toList();
      System.out.println(unique);
    }

    @Test
    @DisplayName("문제점 - map과 Arrays.stream 활용")
    void test2() {
      Stream<String> stringStream = Arrays.stream(arrayOfWords);
      // List<Stream<String>>이 만들어지면서 문제가 해결되지 않음
      List<Stream<String>> strings = stringStream
          .map(word -> word.split(""))  // 각 단어를 개별 문자열 배열로 변환
          .map(Arrays::stream)  // 각 배열을 별도의 스트림으로 생성
          .distinct()
          .toList();
      System.out.println(strings);
    }

    @Test
    @DisplayName("flatMap")
    void test3() {
      List<String> unique = Arrays.stream(arrayOfWords)
          .map(word -> word.split(""))  // 각 단어를 개별 문자를 포함하는 배열로 변환
          .flatMap(Arrays::stream)  // 생성된 스트림을 하나의 스트림으로 평면화
          .distinct()
          .toList();
      System.out.println(unique);
    }
  }

  @Nested
  @DisplayName("퀴즈")
  class Quiz {

    @Test
    @DisplayName("숫자 리스트가 주어졌을 때 각 숫자의 제곱근으로 이루어진 리스트를 반환")
    void test1() {
      List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
      List<Integer> squares = numbers.stream()
          .map(n -> n * n)
          .toList();
      System.out.println(squares);
    }

    @Test
    @DisplayName("두 개의 숫자 리스트가 있을 때 모든 숫자 쌍의 리스트를 반환")
    void test2() {
      List<Integer> numbers1 = Arrays.asList(1, 2, 3);
      List<Integer> numbers2 = Arrays.asList(3, 4);
      List<int[]> pairs = numbers1.stream()
          .flatMap(a -> numbers2.stream()
              .map(b -> new int[]{a, b})
          )
          .toList();
      pairs.forEach(p -> System.out.println(Arrays.toString(p)));
    }

    @Test
    @DisplayName("위에서 합이 3으로 나누어떨어지는 쌍만 반환")
    void test3() {
      List<Integer> numbers1 = Arrays.asList(1, 2, 3);
      List<Integer> numbers2 = Arrays.asList(3, 4);
      List<int[]> pairs = numbers1.stream()
          .flatMap(a -> numbers2.stream()
              .filter(b -> (a + b) % 3 == 0)
              .map(b -> new int[]{a, b})
          )
          .toList();
      pairs.forEach(p -> System.out.println(Arrays.toString(p)));
    }

  }

  @Nested
  @DisplayName("검색과 매칭")
  class SearchAndMatch {

    @Test
    @DisplayName("프레디케이트가 적어도 한 요소와 일치하는지 확인")
    void test1() {
      boolean result = menus.stream()
          .anyMatch(Dish::isVegetarian);
      System.out.println(result);
    }

    @Test
    @DisplayName("프레디케이트가 모든 요소와 일치하는지 검사")
    void test2() {
      boolean result = menus.stream()
          .allMatch(dish -> dish.getCalories() < 1000);
      System.out.println(result);
    }

    @Test
    @DisplayName("nonMatch")
    void test3() {
      boolean result = menus.stream()
          .noneMatch(menu -> menu.getCalories() >= 1000);
      System.out.println(result);
    }

    @Test
    @DisplayName("요소 검색")
    void test4() {
      // findAny는 아무 요소도 반환하지 않을 수 있으므로 Optional 클래스 반환
      Optional<Dish> dish = menus.stream()
          .filter(Dish::isVegetarian)
          .findAny();  // 결과를 찾는 즉시 실행 종료
      System.out.println(dish);

      menus.stream()
          .filter(Dish::isVegetarian)
          .findAny()
          .ifPresent(dish1 -> System.out.println(dish1.getName()));  // 값이 있으면 출력, 없으면 아무 일도 일어나지 않음
    }

    @Test
    @DisplayName("첫 번째 요소 찾기")
    void test5() {
      // 숫자 리스트에서 3으로 나누어떨어지는 첫 번째 제곱값을 반환하는 코드
      List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
      Optional<Integer> first = numbers.stream()
          .map(n -> n * n)
          .filter(n -> (n % 3) == 0)
          .findFirst();
      System.out.println(first);
    }
  }

  @Nested
  @DisplayName("리듀싱")
  class Reducing {

    List<Integer> numbers = Arrays.asList(4, 5, 3, 9);

    @Test
    @DisplayName("요소의 합")
    void test1() {
      int sum = numbers.stream()
          .reduce(0, (a, b) -> a + b);  // reduce(초기값, BinaryOperator<T>)
      System.out.println(sum);

      int sum2 = numbers.stream()
          .reduce(0, Integer::sum);
      System.out.println(sum2);
    }

    @Test
    @DisplayName("최댓값과 최솟값")
    void test2() {
      Optional<Integer> max = numbers.stream()
          .reduce(Integer::max);
      System.out.println(max);

      Optional<Integer> min = numbers.stream()
          .reduce(Integer::min);
      System.out.println(min);
    }

    @Test
    @DisplayName("퀴즈 - map과 reduce 메서드를 이용해서 스트림의 요리 개수를 계산")
    void test3() {
      Optional<Integer> result = menus.stream()
          .map(menu -> 1)
          .reduce(Integer::sum);
      System.out.println(result);

      // 위와 동일
      long count = menus.stream().count();
      System.out.println(count);
    }
  }
}
