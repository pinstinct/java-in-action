package chapter6;

import static java.util.stream.Collectors.averagingInt;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.reducing;
import static java.util.stream.Collectors.summarizingInt;
import static java.util.stream.Collectors.summingInt;

import constant.Type;
import domain.Dish;
import domain.Transaction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Currency;
import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("스트림으로 데이터 수집")
public class StreamCollectorTest {

  List<Transaction> transactions = Arrays.asList(
      new Transaction(2000, Currency.getInstance("USD")),
      new Transaction(1500, Currency.getInstance("USD")),
      new Transaction(1000, Currency.getInstance("KRW")),
      new Transaction(3000, Currency.getInstance("KRW")));

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

  @Nested
  @DisplayName("컬렉터란 무엇인가")
  class Collectors {

    @Test
    @DisplayName("통화별로 트랜잭션을 그룹화한 코드(명령형 버전)")
    void test1() {
      Map<Currency, List<Transaction>> transactionByCurrencies = new HashMap<>();

      for (Transaction transaction : transactions) {
        Currency currency = transaction.getCurrency();
        List<Transaction> transactionsForCurrency = transactionByCurrencies.get(currency);
        if (transactionsForCurrency == null) {  // 현재 통화를 그룹화하는 맵에 항목이 없으면 항목을 생성
          transactionsForCurrency = new ArrayList<>();
          transactionByCurrencies.put(currency, transactionsForCurrency);
        }
        transactionsForCurrency.add(transaction);
      }
      System.out.println(transactionByCurrencies);
    }

    @Test
    @DisplayName("통화별로 트랜잭션을 그룹화한 코드(함수형 버전)")
    void test2() {
      Map<Currency, List<Transaction>> result = transactions.stream()
          .collect(groupingBy(Transaction::getCurrency));
      System.out.println(result);
    }
  }

  @Nested
  @DisplayName("리듀싱과 요약")
  class ReduceAndSummarize {

    @Test
    @DisplayName("counting")
    void test1() {
      Long howManyDishes = menus.stream()
          .collect(counting());
      System.out.println(howManyDishes);

      long howManyDishes2 = menus.stream().count();
      System.out.println(howManyDishes2);
    }

    @Test
    @DisplayName("스트림값에서 최댓값과 최솟값 검색")
    void test2() {
      Comparator<Dish> dishCaloriesComparator = Comparator.comparingInt(Dish::getCalories);
      Optional<Dish> mostCalorieDish = menus.stream()
          .collect(maxBy(dishCaloriesComparator));  // maxBy(Comparator)
      System.out.println(mostCalorieDish);

      Optional<Dish> mostCalorieDish2 = menus.stream()
          .max(dishCaloriesComparator);
      System.out.println(mostCalorieDish2);
    }

    @Test
    @DisplayName("요약 연산")
    void test3() {
      // 합계
      Integer totalCalories = menus.stream().collect(summingInt(Dish::getCalories));
      System.out.println(totalCalories);

      // 평균
      Double avgCalories = menus.stream().collect(averagingInt(Dish::getCalories));
      System.out.println(avgCalories);

      // 요소 수, 합계, 평균, 최댓값, 최소값 정보 수집
      IntSummaryStatistics menuStatistics = menus.stream()
          .collect(summarizingInt(Dish::getCalories));
      System.out.println(menuStatistics);
    }

    @Test
    @DisplayName("문자열 연결")
    void test4() {
      String shortMenu = menus.stream()
          .map(Dish::getName).collect(joining());  // joining() 메서드는 내부적으로 StringBuilder 이용
      System.out.println(shortMenu);

      // 연결된 두 요소 사이에 구분자 추가
      String shortMenu2 = menus.stream()
          .map(Dish::getName).collect(joining(", "));
      System.out.println(shortMenu2);
    }

    @Test
    @DisplayName("범용 리듀싱 요약 연산")
    void test5() {
      Integer totalCalories = menus.stream()
          // reducing(시작값 또는 반환값, 변환 함수, BinaryOperator)
          .collect(reducing(0, Dish::getCalories, (i, j) -> i + j));
      System.out.println(totalCalories);

      Optional<Dish> mostCalorieDish = menus.stream()
          // reducing(자신을 반환하는 항등 함수): 스트림의 첫 번째 요소가 시작값이다.
          .collect(reducing((d1, d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2));
      System.out.println(mostCalorieDish);
    }

    @Test
    @DisplayName("컬렉션 프레임워크 유연성")
    void test6() {
      Integer totalCalories = menus.stream()
          .collect(reducing(0,  // 초기값
              Dish::getCalories,  // 합계 함수
              Integer::sum));  // 변환 함수
      System.out.println(totalCalories);
    }

    @Test
    @DisplayName("컬렉션 프레임워크 유연성")
    void test7() {
      Integer totalCalories = menus.stream()
          .map(Dish::getCalories)
          .reduce(Integer::sum).get();
      System.out.println(totalCalories);

      // 간결하고 가독성이 좋으며, IntStream 덕분에 자동 언박싱 과정을 회피하므로 성능도 좋다.
      int totalCalories2 = menus.stream().mapToInt(Dish::getCalories).sum();
      System.out.println(totalCalories2);
    }
  }
}
