package chapter2;

import static chapter2.FilteringApples.filter;
import static chapter2.FilteringApples.filterApples;
import static chapter2.FilteringApples.filterApplesByColor;
import static chapter2.FilteringApples.filterGreenApples;
import static constant.Colors.GREEN;
import static constant.Colors.RED;
import static org.assertj.core.api.Assertions.assertThat;

import chapter2.FilteringApples.ApplePredicate;
import chapter2.FilteringApples.AppleRedAndHeavyPredicate;
import domain.Apple;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
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

  @Test
  @DisplayName("네 번째 시도: 추상적 조건으로 필터링")
  void test3() {
    List<Apple> apples = filterApples(inventory, new AppleRedAndHeavyPredicate());
    assertThat(apples).isEmpty();
    System.out.println(apples);
  }

  @Test
  @DisplayName("다섯 번째 시도: 익명 클래스 사용")
  void test4() {
    // filterApples 메서드의 동작을 직접 파라미터화
    List<Apple> apples = filterApples(inventory, new ApplePredicate() {
      @Override
      public boolean test(Apple apple) {
        return RED.equals(apple.getColor());
      }
    });

    assertThat(apples.size()).isEqualTo(1);
    System.out.println(apples);
  }

  @Test
  @DisplayName("여섯 번째 시도: 람다 표현식 사용")
  void test5() {
    List<Apple> apples = filterApples(inventory, apple -> RED.equals(apple.getColor()));
    assertThat(apples.size()).isEqualTo(1);
    System.out.println(apples);
  }

  @Test
  @DisplayName("일곱 번째 시도: 리스트 형식의 추상화")
  void test6() {
    List<Apple> redApples = filter(inventory, apple -> RED.equals(apple.getColor()));
    assertThat(redApples.size()).isEqualTo(1);
    System.out.println(redApples);

    List<Integer> evenNumbers = filter(Arrays.asList(1, 2, 3, 4, 5), number -> number % 2 == 0);
    assertThat(evenNumbers.size()).isEqualTo(2);
    System.out.println(evenNumbers);
  }

  @Test
  @DisplayName("Runnable로 코드 블록 실행하기")
  void test7() {
    Thread thread = new Thread(new Runnable() {
      @Override
      public void run() {
        System.out.println("Hello world");
      }
    });
    thread.start();
  }

  @Test
  @DisplayName("Runnable로 코드 블록 실행하기 with 람다")
  void test8() {
    Thread thread = new Thread(() -> System.out.println("Hello world"));
    thread.start();
  }

  @Test
  @DisplayName("Callable을 결과로 반환하기")
  void test9() {
    ExecutorService executorService = Executors.newCachedThreadPool();
    Future<String> threadName = executorService.submit(new Callable<String>() {
      @Override
      public String call() throws Exception {
        return Thread.currentThread().getName();
      }
    });
  }

  @Test
  @DisplayName("Callable을 결과로 반환하기 with 람다")
  void test10() {
    ExecutorService executorService = Executors.newCachedThreadPool();
    Future<String> threadName = executorService.submit(() -> Thread.currentThread().getName());
  }
}