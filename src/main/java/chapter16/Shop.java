package chapter16;

import static java.lang.String.format;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

public class Shop {

  private final String name;
  private final Random random;
  static List<Shop> shops = Arrays.asList(new Shop("BestPrice"), new Shop("LetsSaveBig"),
      new Shop("MyFavoriteShop"), new Shop("BuyItAll"));

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
    return shops.parallelStream()
        .map(shop -> format("%s price is %s", shop.getName(), shop.getPrice(product)))
        .toList();
  }

  // 3. CompletableFuture로 비동기 호출 구현하기
  public static List<String> findPricesWithCF(String product) {
    List<CompletableFuture<String>> priceFutures = shops.stream()
        .map(shop -> CompletableFuture.supplyAsync( // CompletableFuture로 각각의 가격을 비동기적으로 계산
            () -> format("%s price is %s", shop.getName(), shop.getPrice(product))
        ))
        .toList();
    return priceFutures.stream()
        .map(CompletableFuture::join)  // 모든 비동기 동작이 끝나기를 기다림
        .toList();
  }

  public String getName() {
    return name;
  }

  public String getPrice(String product) {
    double price = calculatePrice(product);
    return name + ":" + price;
  }

  public double calculatePrice(String product) {
    delay();
    return Double.parseDouble(
        String.valueOf(random.nextDouble() * product.charAt(0) + product.charAt(1)));
  }
}
