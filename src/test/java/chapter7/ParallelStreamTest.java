package chapter7;

import static chapter7.CustomSum.iterativeSum;
import static chapter7.CustomSum.parallelSum;
import static chapter7.CustomSum.sequentialSum;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;


public class ParallelStreamTest {

  private static final long N = 10_000_000L;

  @Nested
  @DisplayName("병렬 스트림")
  class Parallel {

    @Test
    @DisplayName("숫자 n을 인수로 받아서 1부터 n까지의 모든 숫자 합계를 반환하기")
    public void test1() {
      long result = sequentialSum(N);
      System.out.println(result);

      System.out.println("===");
      long result2 = iterativeSum(N);
      System.out.println(result2);
    }

    @Test
    @DisplayName("순차 스트림을 병렬 스트림으로 변환하기")
    public void test2() {
      long result = parallelSum(N);
      System.out.println(result);
    }
  }
}
