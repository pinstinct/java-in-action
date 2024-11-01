package chapter16;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FutureTest {

  private Double doSomeLongComputation() {
    return 1D;
  }

  private void doSomethingElse() {

  }

  @Test
  @DisplayName("자바 8 이전의 Future 예제")
  void test1() {
    // 스레드 풀에 태스크를 제출하려면 ExecutorService를 만들어야 함
    ExecutorService executor = Executors.newCachedThreadPool();

    // Callable을 ExecutorService로 제출
    Future<Double> future = executor.submit(new Callable<Double>() {
      @Override
      public Double call() throws Exception {
        // 시간이 오래 걸리는 작업은 다른 스레드에서 비동기적으로 실행
        return doSomeLongComputation();
      }
    });

    // 비동기 작업을 수행하는 동안 다른 작업 수행
    doSomethingElse();

    try {
      // 비동기 작업 결과를 가져옴 (결과가 준비되어 있지 않으면 호출 스레드가 블록됨)
      // 하지만 최대 1초까지만 대기
      Double result = future.get(1, TimeUnit.SECONDS);
      System.out.println(result);
    } catch (ExecutionException e) {
      // 계산 중 예외 발생
    } catch (InterruptedException e) {
      // 현재 스레드에서 대기 중 인터럽트 발생
    } catch (TimeoutException e) {
      // Future가 완료되기 전에 타임아웃 발생
    }
  }


}
