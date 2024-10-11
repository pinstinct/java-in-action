package chapter3;

import static constant.Colors.GREEN;
import static constant.Colors.RED;
import static java.util.Comparator.comparing;

import domain.Apple;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;


class FilteringApplesTest {

  List<Apple> inventory = Arrays.asList(new Apple(80, GREEN), new Apple(155, GREEN),
      new Apple(155, RED),
      new Apple(120, RED));

  @Nested
  @DisplayName("람다, 메소드 참조 활용하기")
  class LambdaAndMethodReferenceTest {

    @Test
    @DisplayName("완성 코드")
    void test1() {
      inventory.sort(comparing(Apple::getWeight));
      System.out.println(inventory);
    }

    @Test
    @DisplayName("1단계: 코드 전달")
    void test2() {
      inventory.sort(new AppleComparator());
      System.out.println(inventory);
    }

    @Test
    @DisplayName("2단계: 익명 클래스 사용")
    void test3() {
      // 한 번만 사용할 Comparator 는 구현하는 것보다 익명 클래스를 이용하는 것이 좋다.
      inventory.sort(new Comparator<Apple>() {
        @Override
        public int compare(Apple o1, Apple o2) {
          return o1.getWeight().compareTo(o2.getWeight());
        }
      });
      System.out.println(inventory);
    }

    @Test
    @DisplayName("3단계: 람다 표현식 사용")
    void test4() {
      // Comparator 의 함수 디스크립터는 (Apple, Apple) -> int
      inventory.sort((a1, a2) -> a1.getWeight().compareTo(a2.getWeight()));
      System.out.println(inventory);
    }

    @Test
    @DisplayName("3단계: 람다 표현식 사용 - 코드 간소화")
    void test5() {
      inventory.sort(comparing(apple -> apple.getWeight()));
      System.out.println(inventory);
    }

    @Test
    @DisplayName("4단계: 메서드 참조 사용")
    void test6() {
      inventory.sort(comparing(Apple::getWeight));
      System.out.println(inventory);
    }
  }

  @Nested
  @DisplayName("람다 표현식을 조합할 수 있는 유용한 메서드")
  class LambdaPractice {

    @Test
    @DisplayName("Comparator 조합 - 역정렬")
    void test1() {
      inventory.sort(comparing(Apple::getWeight).reversed());  // 무게 내림차순 정렬
      System.out.println(inventory);
    }

    @Test
    @DisplayName("Comparator 조합 - 연결")
    void test2() {
      inventory.sort(comparing(Apple::getWeight).reversed()  // 무게 내림차순 정렬
          .thenComparing(Apple::getColor));  // 사과의 무게가 같다면 색으로 정렬
      System.out.println(inventory);
    }

    @Test
    @DisplayName("Function 조합 - andThen")
    void test3() {
      Function<Integer, Integer> f = x -> x + 1;
      Function<Integer, Integer> g = x -> x * 2;
      // g(f(x))
      Function<Integer, Integer> h = f.andThen(g);
      int result = h.apply(1);
      System.out.println(result);
    }

    @Test
    @DisplayName("Function 조합 - apply")
    void test4() {
      Function<Integer, Integer> f = x -> x + 1;
      Function<Integer, Integer> g = x -> x * 2;
      // f(g(x))
      Function<Integer, Integer> h = f.compose(g);
      int result = h.apply(1);
      System.out.println(result);
    }

    @Test
    @DisplayName("Function 조합 - 헤더 추가 후 철자 검사를 하고, 마지막에 푸터 추가")
    void test5() {
      Function<String, String> addHeader = Letter::addHeader;
      Function<String, String> transformationPipeline = addHeader.andThen(Letter::checkSpelling)
          .andThen(Letter::addFooter);
      String result = transformationPipeline.apply("hi, labda");
      System.out.println(result);
    }

    @Test
    @DisplayName("Function 조합 - 헤더와 푸터만 추가")
    void test6() {
      Function<String, String> addHeader = Letter::addHeader;
      Function<String, String> transformationPipeline = addHeader.andThen(Letter::addFooter);
      String result = transformationPipeline.apply("hi, labda");
      System.out.println(result);
    }
  }
}