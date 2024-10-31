package chapter15;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.IntConsumer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ThreadTest {

  private int g(int x) {
    // todo 오래 걸리는 작업이라고 가정
    return x;
  }

  private int f(int x) {
    // todo 오래 걸리는 작업이라고 가정
    return x;
  }

  private static class Result {

    private int left;
    private int right;
  }

  @Test
  @DisplayName("Thread 사용해 실행이 오래 걸리는 작업의 시간을 축소했지만, 코드가 복잡")
  void test1() throws InterruptedException {
    int x = 1337;
    Result result = new Result();

    Thread t1 = new Thread(() -> result.left = f(x));
    Thread t2 = new Thread(() -> result.right = g(x));

    t1.start();
    t2.start();
    t1.join();
    t2.join();
    System.out.println(result.left + result.right);
  }

  @Test
  @DisplayName("Runnable 대신 Future API 이용해 코드 단순화")
  void test2() throws ExecutionException, InterruptedException {
    int x = 1337;
    ExecutorService executorService = Executors.newFixedThreadPool(2);
    Future<Integer> y = executorService.submit(() -> f(x));
    Future<Integer> z = executorService.submit(() -> g(x));
    System.out.println(y.get() + z.get());

    executorService.shutdown();
  }

  private void f(int x, IntConsumer dealWithResult) {
    // 값을 반환하는 대신 f에 추가 인수로 콜백(람다)을 전달해서
    // return 문으로 결과를 반환하는 것이 아니라 결과가 준비되면 이를 람다로 호출하는 태스크를 만드는 것
    // 즉, f는 바디를 실행하면서 태스크를 만든 다음 즉시 반환
    dealWithResult.accept(x);
  }

  private void g(int x, IntConsumer dealWithResult) {
    dealWithResult.accept(x);
  }

  @Test
  @DisplayName("리액티브 형식 API")
  void test3() {
    int x = 1337;
    Result result = new Result();
    f(x, (int y) -> {
      result.left = y;
      System.out.println(result.left + result.right);
    });

    g(x, (int z) -> {
      result.right = z;
      System.out.println(result.left + result.right);
    });
  }

  @Test
  @DisplayName("CompletableFuture와 콤비네이터를 이용한 동시성")
  void test4() throws ExecutionException, InterruptedException {
    int x = 1337;
    ExecutorService executorService = Executors.newFixedThreadPool(10);

    CompletableFuture<Integer> a = new CompletableFuture<>();
    executorService.submit(() -> a.complete(f(x)));
    int b = g(x);
    // f(x)의 실행이 끝나지 않는 상황에서 get()을 기다려야 하므로 프로세싱 자원을 낭비할 수 있다.
    System.out.println(a.get() + b);

    executorService.shutdown();
  }

  @Test
  @DisplayName("CompletableFuture와 콤비네이터를 이용한 동시성 - 조합하기 thenCombine")
  void test5() throws ExecutionException, InterruptedException {
    int x = 1337;
    ExecutorService executorService = Executors.newFixedThreadPool(10);
    CompletableFuture<Integer> a = new CompletableFuture<>();
    CompletableFuture<Integer> b = new CompletableFuture<>();
    // 다른 두 작업이 끝날 때까지는 스레드에서 실행되지 않는다.
    CompletableFuture<Integer> c = a.thenCombine(b, (y, z) -> y + z);
    executorService.submit(() -> a.complete(f(x)));
    executorService.submit(() -> b.complete(g(x)));

    System.out.println(c.get());
    executorService.shutdown();
  }

}
