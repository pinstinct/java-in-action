package chapter19;

import static chapter19.MathUtil.from;
import static chapter19.MathUtil.numbers;
import static chapter19.MathUtil.primes;
import static chapter19.MathUtil.printAll;
import static chapter19.MathUtil.printAllRecursion;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("스트림과 게으른 평가")
class MathUtilTest {

  @Test
  @DisplayName("스트림 동작을 보여주는 간단한 알고리즘")
  void test1() {
    Stream<Integer> primes = primes(5);
    System.out.println(primes.toList());
  }

  @Test
  @DisplayName("알고리즘 개선")
  void test2() {
    // 하지만 findFirst, skip에서 스트림이 완전 소비되어 에러 발생
    IntStream numbers = numbers();
    assertThrows(IllegalStateException.class, () -> primes(numbers));
  }

  @Test
  @DisplayName("from 테스트 - 게으른 리스트 생성")
  void test3() {
    // 모든 수를 미리 계산하려고 한다면 프로그램은 영원히 종료되지 않는다.
    LazyList<Integer> numbers = from(2);
    int two = numbers.head();
    int three = numbers.tail().head();
    int four = numbers.tail().tail().head();
    System.out.println(two + " " + three + " " + four);
  }

  @Test
  @DisplayName("게으른 필터 테스트")
  void test4() {
    LazyList<Integer> numbers = from(2);
    Integer two = primes(numbers).head();
    Integer three = primes(numbers).tail().head();
    Integer five = primes(numbers).tail().tail().head();
    System.out.println(two + " " + three + " " + five);
  }

  @Test
  @DisplayName("printAll 테스트")
  void test5() {
    printAll(primes(from(2)));
  }

  @Test
  @DisplayName("printAllRecursion 테스트")
  void test6() {
    printAllRecursion(primes(from(2)));
  }
}