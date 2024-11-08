package chapter19;

import java.util.stream.IntStream;
import java.util.stream.Stream;

/* 소수 구하기 */
public class MathUtil {

  public static Stream<Integer> primes(int n) {
    return Stream.iterate(2, i -> i + 1)
        .filter(MathUtil::isPrime)
        .limit(n);
  }

  public static boolean isPrime(int candidate) {
    int candidateRoot = (int) Math.sqrt(candidate);
    return IntStream.rangeClosed(2, candidateRoot)
        .noneMatch(i -> candidate % i == 0);
  }

  // 스트림 숫자 얻기
  static IntStream numbers() {
    return IntStream.iterate(2, n -> n + 1);
  }

  // 머리 획득
  static int head(IntStream numbers) {
    // findFirst: 스트림 완전 소비
    return numbers.findFirst().getAsInt();
  }

  // 꼬리 필터링
  static IntStream tail(IntStream numbers) {
    // skip: 스트림 완전 소비
    return numbers.skip(1);
  }

  // 재귀적으로 소수 스트림 생성
  static IntStream primes(IntStream numbers) {
    int head = head(numbers);
    // concat은 두 개의 스트림 인스턴스를 인수로 받는다.
    // 두 번째 인수가 primes를 재귀적으로 호출하면서 무한 재귀에 빠진다.
    return IntStream.concat(IntStream.of(head), primes(tail(numbers).filter(n -> n % head != 0)));
  }

  // 게으른 리스트 숫자 얻기
  static LazyList<Integer> from(int n) {
    return new LazyList<>(n, () -> from(n + 1));
  }

  // 게으른 리스트를 이용한 소수 생성
  static MyList<Integer> primes(MyList<Integer> numbers) {
    return new LazyList<>(
        numbers.head(),
        () -> primes(
            numbers.tail().filter(n -> n % numbers.head() != 0)
        )
    );
  }

  // 모든 소수 출력
  static <T> void  printAll(MyList<T> list) {
    while (!list.isEmpty()) {
      System.out.println(list.head());
      list = list.tail();
    }
  }

  // 모든 소수 출력 - 재귀
  static <T> void  printAllRecursion(MyList<T> list) {
    if (list.isEmpty()) return;
    System.out.println(list.head());
    printAllRecursion(list.tail());
  }
}
