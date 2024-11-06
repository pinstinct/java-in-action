package chapter18;

import static chapter18.Recursion.factorialIterative;
import static chapter18.Recursion.factorialRecursive;
import static chapter18.Recursion.factorialStreams;
import static chapter18.Recursion.factorialTailRecursive;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("재귀와 반복")
class RecursionTest {

  @Test
  @DisplayName("반복 방식")
  void test1() {
    int result = factorialIterative(10);
    System.out.println(result);
  }

  @Test
  @DisplayName("재귀 방식")
  void test2() {
    long result = factorialRecursive(10);
    System.out.println(result);
  }

  @Test
  @DisplayName("스트림")
  void test3() {
    long result = factorialStreams(10);
    System.out.println(result);
  }

  @Test
  @DisplayName("꼬리 재귀 방식")
  void test4() {
    long result = factorialTailRecursive(10);
    System.out.println(result);
  }
}