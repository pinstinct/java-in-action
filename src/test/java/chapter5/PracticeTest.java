package chapter5;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toSet;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("실전 연습")
public class PracticeTest {

  Trader raoul = new Trader("Raoul", "Cambridge");
  Trader mario = new Trader("Mario", "Milan");
  Trader alan = new Trader("Alan", "Cambridge");
  Trader brian = new Trader("Brian", "Cambridge");

  List<Transaction> transactions = Arrays.asList(
      new Transaction(brian, 2011, 300),
      new Transaction(raoul, 2012, 1000),
      new Transaction(raoul, 2011, 400),
      new Transaction(mario, 2012, 710),
      new Transaction(mario, 2012, 700),
      new Transaction(alan, 2012, 950)
  );

  @Test
  @DisplayName("2011년에 일어난 모든 트랜잭션을 찾아 값을 오름차순으로 정리하시오.")
  void test1() {
    List<Transaction> result = transactions.stream()
        .filter(transaction -> transaction.year() == 2011)
        .sorted(comparing(Transaction::value))
        .toList();
    System.out.println(result);
  }

  @Test
  @DisplayName("거래자가 근무하는 모든 도시를 중복 없이 나열하시오.")
  void test2() {
    // my
    List<String> result = transactions.stream()
        .map(Transaction::trader)
        .map(Trader::city)
        .distinct()
        .toList();
    System.out.println(result);

    // book
    Set<String> result2 = transactions.stream()
        .map(transaction -> transaction.trader().city())
        .collect(toSet());  // distinct 대신 toSet 사용
    System.out.println(result2);
  }

  @Test
  @DisplayName("케임브리지에서 근무하는 모든 거래자를 찾아서 이름순으로 정렬하시오.")
  void test3() {
    List<Trader> result = transactions.stream()
        .map(Transaction::trader)
        .filter(trader -> trader.city().equals("Cambridge"))
        .distinct()
        .sorted(comparing(Trader::name))
        .toList();
    System.out.println(result);
  }

  @Test
  @DisplayName("모든 거래자의 이름을 알파벳순으로 정렬해서 반환하시오.")
  void test4() {
    // my
    List<Trader> result = transactions.stream()
        .map(Transaction::trader)
        .distinct()
        .sorted(comparing(Trader::name))
        .toList();
    System.out.println(result);

    // book
    String solution1 = transactions.stream()
        .map(transaction -> transaction.trader().name())
        .distinct()
        .sorted()
        .reduce("", (n1, n2) -> n1 + n2);
    System.out.println(solution1);

    String solution2 = transactions.stream()
        .map(transaction -> transaction.trader().name())
        .distinct()
        .sorted()
        .collect(Collectors.joining());
    System.out.println(solution2);
  }

  @Test
  @DisplayName("밀라노에 거래자가 있는가?")
  void test5() {
    // my
    boolean result = transactions.stream()
        .map(Transaction::trader)
        .anyMatch(trader -> trader.city().equals("Milan"));
    System.out.println(result);

    // book
    boolean solution = transactions.stream()
        .anyMatch(transaction -> transaction.trader().city().equals("Milan"));
    System.out.println(solution);
  }

  @Test
  @DisplayName("케임브리지에 거주하는 거래자의 모든 트랜잭션 값을 출력하시오.")
  void test6() {
    // my
    List<Transaction> result = transactions.stream()
        .filter(transaction -> transaction.trader().city().equals("Cambridge"))
        .toList();
    System.out.println(result);

    // solution
    transactions.stream()
        .filter(transaction -> transaction.trader().city().equals("Cambridge"))
        .map(Transaction::value)
        .forEach(System.out::println);
  }

  @Test
  @DisplayName("전체 트랜잭션 중 최댓값은 얼마인가?")
  void test7() {
    Optional<Integer> result = transactions.stream()
        .map(Transaction::value)
        .reduce(Integer::max);
    System.out.println(result);
  }

  @Test
  @DisplayName("전체 트랜잭션 중 최솟값은 얼마인가?")
  void test8() {
    Optional<Integer> result = transactions.stream()
        .map(Transaction::value)
        .reduce(Integer::min);
    System.out.println(result);
  }
}
