package chapter16;

import static chapter16.Discount.applyDiscount;
import static chapter16.ExchangeService.DEFAULT_RATE;
import static chapter16.ExchangeService.getRate;
import static java.lang.String.format;

import chapter16.Discount.Code;
import chapter16.ExchangeService.Money;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class Shop {

  private final String name;
  private final Random random;
  static List<Shop> shops = Arrays.asList(new Shop("BestPrice"), new Shop("LetsSaveBig"),
      new Shop("MyFavoriteShop"), new Shop("BuyItAll"), new Shop("005"), new Shop("006"),
      new Shop("007"), new Shop("008"), new Shop("009"), new Shop("010"), new Shop("011"));

  private static final Executor executor = Executors.newFixedThreadPool(Math.min(shops.size(), 100),
      // 상점 수만큼 스레드를 갖는 풀을 생성(0과 100사이)
      new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
          Thread t = new Thread(r);
          // 프로그램 종료를 방해하지 않는 데몬 스레드를 사용
          t.setDaemon(true);
          return t;
        }
      });

  public Shop(String name) {
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

  // 1. 모든 상점에 순차적으로 정보를 요청하는 메서드
  public static List<String> findPrices(String product) {
    return shops.stream()
        .map(shop -> format("%s price is %s", shop.getName(), shop.getPrice(product)))
        .toList();
  }

  // 2. 병렬 스트림으로 요청 병렬화
  public static List<String> findPricesParallel(String product) {
    return shops.parallelStream().map(shop -> shop.getPriceString(product))
        .toList();
  }

  // 3. CompletableFuture로 비동기 호출 구현하기
  public static List<String> findPricesWithCF(String product) {
    List<CompletableFuture<String>> priceFutures = shops.stream()
        .map(shop -> CompletableFuture.supplyAsync( // CompletableFuture로 각각의 가격을 비동기적으로 계산
            () -> format("%s price is %.2f", shop.getName(), shop.getPrice(product))
        ))
        .toList();
    return priceFutures.stream()
        .map(CompletableFuture::join)  // 모든 비동기 동작이 끝나기를 기다림
        .toList();
  }

  // 4. 커스텀 Executor 사용하기
  public static List<String> findPriceWithExecutor(String product) {
    List<CompletableFuture<String>> priceFutures = shops.stream().map(
            shop -> CompletableFuture.supplyAsync(
                () -> format("%s price is %s", shop.getName(), shop.getPrice(product)), executor))
        .toList();
    return priceFutures.stream().map(CompletableFuture::join).toList();
  }

  public static List<String> findPricesAsyncSimple(String product) {
    return shops.stream().map(shop -> shop.getPriceString(product))  // 각 상점에서 할인 전 가격 얻기
        .map(Quote::parse)  // 상점에서 반환한 문자열을 Quote 객체로 변환
        .map(Discount::applyDiscount)  // Discount 서비스를 이용해서 각 Quote에 할인 적용
        .toList();
  }

  public static List<String> findPricesAsync(String product) {
    List<CompletableFuture<String>> priceFutures = shops.stream()
        .map(shop -> CompletableFuture.supplyAsync(() -> shop.getPriceString(product), executor))
        // 원격 서비스가 없으므로 지연 없이 동작 수행 가능
        // thenApply 메서드는 CompletableFuture가 끝날 때까지 블록하지 않는다
        // CompletableFuture가 동작을 완전히 완료한 다음에 thenApply 메서드로 전달된 람다 표현식을 적용할 수 있다.
        .map(future -> future.thenApply(Quote::parse))
        // thenCompose(Function) 메서드는 첫 번째 연산의 결과를 두 번째 연산으로 전달
        // Function은 첫 번째 CompletableFuture 반환 결과를 인수로 받고 두 번째 CompletableFuture 반환
        .map(future -> future.thenCompose(
            quote -> CompletableFuture.supplyAsync(() -> applyDiscount(quote), executor))).toList();
    return priceFutures.stream().map(CompletableFuture::join)  // 값 추출
        .toList();
  }

  public String getName() {
    return name;
  }

  public double getPrice(String product) {
    return calculatePrice(product);
  }

  public static List<String> findPricesInUSD(String product) {
    List<CompletableFuture<String>> futures = shops.stream().map(
        // 제품가격 정보를 요청하는 첫 번째 태스크 생성 (Executor 스레드 1)
        shop -> CompletableFuture.supplyAsync(() -> shop.getPrice(product))
            // USD, EUR의 환율 정보를 요청하는 독립적인 두 번째 태스크 생성 (Executor 스레드 2)
            .thenCombine(CompletableFuture.supplyAsync(() -> getRate(Money.EUR, Money.USD)),
                (price, rate) -> price * rate)
            .completeOnTimeout(DEFAULT_RATE, 1,
                TimeUnit.SECONDS)  // 환전 서비스가 1초 안에 결과를 제공하지 않으면 기본 환율값을 사용
            .thenApply(price -> shop.getName() + " price is " + price)).toList();
    return futures.stream().map(CompletableFuture::join).toList();
  }

  public double calculatePrice(String product) {
    delay();
    return Double.parseDouble(
        String.valueOf(random.nextDouble() * product.charAt(0) + product.charAt(1)));
  }

  public String getPriceString(String product) {
    double price = calculatePrice(product);
    Discount.Code code = Discount.Code.values()[random.nextInt(Code.values().length)];
    return format("%s:%.2f:%s", name, price, code);
  }
}
