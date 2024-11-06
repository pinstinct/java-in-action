package chapter18;

import java.util.stream.LongStream;

public class Recursion {

  // 반복 방식의 팩토리얼
  static int factorialIterative(int n) {
    int r = 1;
    // 매 반복마다 변수 r과 i가 갱신
    for (int i = 1; i <= n; i++) {
      r *= i;
    }
    return r;
  }

  // 재귀 방식의 팩토리얼
  static long factorialRecursive(long n) {
    return n == 1 ? 1 : n * factorialIterative((int) (n - 1));
  }

  // 스트림 팩토리얼
  static long factorialStreams(long n) {
    return LongStream.rangeClosed(1, n)
        .reduce(1, (long a, long b) -> a * b);
  }

  // 꼬리 재귀 팩토리얼
  static long factorialTailRecursive(long n) {
    return factorialHelper(1, n);
  }

  private static long factorialHelper(long acc, long n) {
    return n == 1 ? acc : factorialHelper(acc * n, n -1);
  }
}
