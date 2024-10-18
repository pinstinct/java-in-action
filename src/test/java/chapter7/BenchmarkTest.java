package chapter7;

import static chapter7.CustomSum.iterativeSum;
import static chapter7.CustomSum.parallelRangedSum;
import static chapter7.CustomSum.parallelSum;
import static chapter7.CustomSum.rangedSum;
import static chapter7.CustomSum.sequentialSum;
import static chapter7.CustomSum.sideEffectParallelSum;
import static chapter7.CustomSum.sideEffectSum;

import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Warmup;

@BenchmarkMode(Mode.AverageTime)  // 벤치마크 대상 메서드를 실행하는 데 걸린 평균 시간 측정
@OutputTimeUnit(TimeUnit.MILLISECONDS)  // 벤치마크 결과를 밀리초 단위로 출력
@Warmup(iterations = 3)
@Measurement(iterations = 2)
@Fork(value = 2, jvmArgs = {"-Xms4G", "-Xmx4G"})  // 4GB의 힙 공간을 제공하는 환경에서 두 번 벤치마크를 수행
@State(Scope.Thread)
public class BenchmarkTest {
  /**
   * Benchmark                            Mode  Cnt   Score    Error  Units
   * BenchmarkTest.iterativeSumTest       avgt    4   3.362 ±  0.083  ms/op
   * BenchmarkTest.parallelRangedSumTest  avgt    4   0.531 ±  0.135  ms/op
   * BenchmarkTest.parallelSumTest        avgt    4  46.991 ± 29.433  ms/op
   * BenchmarkTest.rangedSumTest          avgt    4   3.406 ±  0.227  ms/op
   * BenchmarkTest.sequentialSumTest      avgt    4  55.338 ±  3.746  ms/op
   * */

  private static final long N = 10_000_000L;

  @TearDown(Level.Invocation)  // 매 번 벤치마크를 실행한 다음에 가비지 컬렉터 동작
  public void tearDown() {
    System.gc();
  }

  @Benchmark
  public void iterativeSumTest() {
    iterativeSum(N);
  }

  @Benchmark
  public void sequentialSumTest() {
    sequentialSum(N);
  }

  @Benchmark
  public void parallelSumTest() {
    parallelSum(N);
  }

  @Benchmark
  public void rangedSumTest() {
    rangedSum(N);
  }

  @Benchmark
  public void parallelRangedSumTest() {
    parallelRangedSum(N);
  }

  @Test
  public void sideEffectSumTest() {
    long result = sideEffectSum(N);
    System.out.println("SideEffect sum done in: " + result);
  }

  @Test
  public void sideEffectParallelSumTest() {
    long result = sideEffectParallelSum(N);
    System.out.println("SideEffect parallel sum done in: " + result);
  }

}
