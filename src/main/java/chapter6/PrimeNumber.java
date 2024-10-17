package chapter6;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class PrimeNumber {

  /**
   * 소수로만 나누기(제수를 현재 숫자 이하에서 발견한 소수로 제한)
   */
  public static boolean isPrime(List<Integer> primes, int candidate) {
    int candidateRoot = (int) Math.sqrt(candidate);  // 제곱근보다 작은 소수만 사용
    return primes.stream()  // 지금까지 발견한 소수 리스트
        .takeWhile(i -> i <= candidateRoot)  // filter를 사용하면 전체 스트림을 처리하게 됨
        .noneMatch(i -> candidate % i == 0);
  }

  public static Map<Boolean, List<Integer>> partitionPrimesWithCustomCollector(int n) {
    return IntStream.rangeClosed(2, n).boxed()
        .collect(new PrimeNumberCollector());
  }
}
