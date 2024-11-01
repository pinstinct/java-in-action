package chapter16;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class AsyncShopTest {

  public void doSomethingElse() {

  }

  @Nested
  @DisplayName("비동기 API 구현")
  class AsyncApi {

    @Test
    @DisplayName("동기 메서드를 비동기 메서드로 변환")
    void test1() {
      AsyncShop asyncShop = new AsyncShop("BestShop");
      long start = System.nanoTime();
      Future<Double> futurePrice = asyncShop.getPriceAsync("my favorite product");
      long invocationTime = (System.nanoTime() - start) / 1_000_000;
      System.out.println("Invocation returned after " + invocationTime + " m secs");

      // 제품의 가격을 계산하는 동안 다른 작업 수행
      doSomethingElse();

      try {
        // Future가 결과값을 가지고 있다면 Future에 포함된 값을 읽거나
        // 아니면 값이 계산될 때까지 블록
        Double price = futurePrice.get();
        System.out.printf("Price is %.2f%n", price);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      } catch (ExecutionException e) {
        throw new RuntimeException(e);
      }

      long retrievalTime = (System.nanoTime() - start) / 1_000_000;
      System.out.println("Price returned after " + retrievalTime + "m secs");
    }

    @Test
    @DisplayName("에러 처리 방법")
    void test2() {
      // 위의 코드는 에러 처리가 되어 있지 않아 영원히 대기 상태에 빠질 수 있다.
      AsyncShop asyncShop = new AsyncShop("BestShop");
      Future<Double> futurePrice = asyncShop.getPriceAsyncWithException(null);

      doSomethingElse();

      try {
        Double price = futurePrice.get();
        System.out.println(price);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }

    @Test
    @DisplayName("팩토리 메서드 supplyAsync로 CompletableFuture 만들기")
    void test3() {
      AsyncShop asyncShop = new AsyncShop("BestShop");
      Future<Double> future = asyncShop.getPriceAsync4(null);

      doSomethingElse();

      try {
        Double price = future.get();
        System.out.println(price);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
  }
}