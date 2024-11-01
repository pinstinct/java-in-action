package chapter16;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class AsyncShop {

  private final String name;
  private final Random random;

  public AsyncShop(String name) {
    this.name = name;
    random = new Random(name.charAt(0) * name.charAt(1) * name.charAt(2));
  }

  public static void delay() {
    try {
      Thread.sleep(1000L);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  // 1. 제품명에 해당하는 가격을 반환하는 동기 메서드
  public double getPrice(String product) {
    // 상점의 데이터베이스를 이용해서 가격 정보를 얻는 동시에
    // 다른 외부 서비스에도 접근
    // 실제 구현할 수 없으니 delay 메서드로 대체
    return calculatePrice(product);
  }

  // 2. 제품명에 해당하는 가격을 반환하는 비동기 메서드
  public Future<Double> getPriceAsync(String product) {
    // 계산 결과를 포함할 CompletableFuture 생성
    CompletableFuture<Double> futurePrice = new CompletableFuture<>();

    new Thread(() -> {
      // 다른 스레드에서 비동기적으로 계산을 수행
      double price = calculatePrice(product);
      // 오랜 시간이 걸리는 계산이 완료되면 Future에 값을 설정
      futurePrice.complete(price);
    }).start();
    // 계산 결과가 완료되길 기다리지 않고 Future 반환
    return futurePrice;
  }

  // 3. 비동기 메서드의 에외 처리
  public Future<Double> getPriceAsyncWithException(String product) {
    CompletableFuture<Double> futurePrice = new CompletableFuture<>();

    new Thread(() -> {
      try {
        double price = calculatePrice(product);
        // 계산이 정상적으로 종료되면 Future에 가격 정보를 저장하고 종료
        futurePrice.complete(price);
      } catch (Exception e) {
        // 도중에 문제가 발생하면 발생한 에러를 포함시켜 Future 종료
        futurePrice.completeExceptionally(e);
      }
    }).start();
    return futurePrice;
  }

  // 4. 팩토리 메서드 supplyAsync로 CompletableFuture 만들기 (위의 3번 코드와 동일)
  public Future<Double> getPriceAsync4(String product) {
    return CompletableFuture.supplyAsync(() -> calculatePrice(product));
  }

  // 임의의 계산값을 반환
  public double calculatePrice(String product) {
    delay();
    return random.nextDouble() * product.charAt(0) + product.charAt(1);
  }

}
