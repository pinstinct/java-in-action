package chapter7;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

public class ForkJoinSumCalculator extends
    RecursiveTask<Long> {  // RecursiveTask 상속받아 포크/조인 프레임워크에서 사용할 태스크를 생성

  public static final long THRESHOLD = 10_000;  // 이 값 이하의 서브태스크는 더 이상 분할할 수 없다.
  private final long[] numbers;  // 더할 숫자 배열
  private int start;
  private int end;

  // 메인 태스크를 생성할 때 사용할 공개 생성자
  public ForkJoinSumCalculator(long[] numbers) {
    this.numbers = numbers;
  }

  // 메인 태스크의 서브태스크를 재귀적으로 만들 때 사용할 비공개 생성자
  private ForkJoinSumCalculator(long[] numbers, int start, int end) {
    this.numbers = numbers;
    this.start = start;
    this.end = end;
  }

  public static long forkJoinSum(long n) {
    long[] numbers = LongStream.rangeClosed(1, n).toArray();
    ForkJoinTask<Long> task = new ForkJoinSumCalculator(numbers);
    // 일반적으로 애플리케이션에서 둘 이상의 ForkJoinPool을 사용하지 않는다.
    // 즉, 소프트웨어의 필요한 곳에서 언제든지 가져다 쓸 수 있도록 ForkJoinPool을 한 번만 인스턴스화해서 정적 필드에 싱글턴으로 저장한다.
    // 인수가 없는 ForkJoinPool 생성자를 이용했는데, 이는 JVM에서 이용할 수 있는 모든 프로세서가 자유롭게 풀에 접근할 수 있음을 의미한다.
    // 정확히는 Runtime.availableProcessors의 반환값으로 풀에 사용할 스레드 수를 결정한다.
    return new ForkJoinPool().invoke(task);  // ForkJoinSumCalculator를 ForkJoinPool로 전달, 풀의 스레드가 ForkJoinSumCalculator의 compute 메서드 실행
  }

  @Override
  protected Long compute() {  // RecursiveTask 추상 메서드 오버라이드
    int length = end - start;  // 이 태스크에서 더할 배열의 크기
    if (length <= THRESHOLD) {
      return computeSequentially();  // 기준값과 같거나 작으면 순차적으로 결과를 계산
    }

    // 배열의 첫 번째 절반을 더하도록 서브태스크를 생성
    ForkJoinSumCalculator leftTask = new ForkJoinSumCalculator(numbers, start, start + length / 2);
    // ForkJoinPool의 다른 스레드로 새로 생성한 태스크를 비동기로 실행
    leftTask.fork();
    // 배열의 나머지 절반을 더하도록 서브태스크를 생성
    ForkJoinSumCalculator rightTask = new ForkJoinSumCalculator(numbers, start + length / 2, end);
    // 두 번째 서브태스크를 동기 실행 (이 때 추가로 분할이 일어날 수 있다.)
    Long rightResult = rightTask.compute();
    // 첫 번째 서브태스크의 결과를 읽거나 아직 결과가 없으면 대기
    Long leftResult = leftTask.join();
    return leftResult + rightResult;  // 두 서브태스크의 결과를 조합한 값이 이 태스크의 결과
  }

  // 더 분할할 수 없을 때 서브태스크의 결과를 계산하는 단순한 알고리즘
  private long computeSequentially() {
    long sum = 0;
    for (int i = start; i < end; i++) {
      sum += numbers[i];
    }
    return sum;
  }
}
