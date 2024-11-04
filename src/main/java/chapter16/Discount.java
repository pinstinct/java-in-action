package chapter16;

/* 원격 서비스라고 가정 */
public class Discount {

  /**
   * Quote 객체를 인수로 받아 할인된 가격 문자열을 반환하는 메서드
   * */
  public static String applyDiscount(Quote quote) {
    return quote.getShopName() + " price is " + Discount.apply(quote.getPrice(),
        quote.getDiscountCode());
  }

  private static double apply(double price, Code code) {
    delay();  // 응답 지연
    return (price * (100 - code.percent) / 100);
  }

  public static void delay() {
    try {
      Thread.sleep(1000L);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  // 할인 코드 정의
  public enum Code {
    NONE(0),
    SILVER(5),
    GOLD(10),
    PLATINUM(15),
    DIAMOND(20);

    private final int percent;

    Code(int percent) {
      this.percent = percent;
    }
  }
}
