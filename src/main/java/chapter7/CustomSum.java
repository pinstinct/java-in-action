package chapter7;


import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * 숫자 n을 인수로 받아서 1부터 n 까지의 모든 숫자 합계를 반환
 */
public class CustomSum {

  // 반복문
  static long iterativeSum(long n) {
    long result = 0;
    for (long i = 1L; i <= n; i++) {
      result += i;
    }
    return result;
  }

  // 순차 리듀싱
  static long sequentialSum(long n) {
    return Stream.iterate(1L, i -> i + 1)  // 무한 자연수 스트림 생성
        .limit(n)  // n개 이하로 제한
        .reduce(0L, Long::sum);  // 모든 숫자를 더하는 스트림 리듀싱 연산
  }

  // 병렬 리듀싱
  static long parallelSum(long n) {
    return Stream.iterate(1L, i -> i + 1)
        .limit(n)
        .parallel()  // 스트림을 병렬 스트림으로 변환
        .reduce(0L, Long::sum);
  }

  static long rangedSum(long n) {
    return LongStream.rangeClosed(1, n)
        .reduce(0L, Long::sum);
  }

  static long parallelRangedSum(long n) {
    return LongStream.rangeClosed(1, n)
        .parallel()
        .reduce(0L, Long::sum);
  }

  static long sideEffectSum(long n) {
    Accumulator accumulator = new Accumulator();
    LongStream.rangeClosed(1, n).forEach(accumulator::add);
    return accumulator.total;
  }

  static long sideEffectParallelSum(long n) {
    Accumulator accumulator = new Accumulator();
    LongStream.rangeClosed(1, n).parallel().forEach(accumulator::add);
    return accumulator.total;
  }

  public static class Accumulator {

    // total 접근 할 때마다 (다수의 스레드에서 동시에 데이터에 접근하는) 데이터 레이트 문제 발생
    // 동기화로 문제를 해결하다보면 결국 병렬화라는 특성이 사라짐
    public long total = 0;

    public void add(long value) {
      total += value;  // 아토믹 연산이 아님
    }
  }
}
