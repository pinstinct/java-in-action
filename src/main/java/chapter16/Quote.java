package chapter16;

import chapter16.Discount.Code;

/**
 * 상점에서 제공한 문자열 파싱 클래스
 */
public class Quote {

  private final String shopName;
  private final double price;
  private final Discount.Code discountCode;

  public Quote(String shopName, double price, Discount.Code discountCode) {
    this.shopName = shopName;
    this.price = price;
    this.discountCode = discountCode;
  }

  /**
   * 상점에서 얻은 문자열을 정적 팩토리 메서드 parse 로 넘겨주면 상정 이름, 할인전 가격, 할인된 가격의 정보를 포함한 Quote 클래스 인스턴스 생성
   */
  public static Quote parse(String s) {
    String[] split = s.split(":");
    String shopName = split[0];
    double price = Double.parseDouble(split[1]);
    Discount.Code discountCode = Code.valueOf(split[2]);
    return new Quote(shopName, price, discountCode);
  }

  public String getShopName() {
    return shopName;
  }

  public double getPrice() {
    return price;
  }

  public Code getDiscountCode() {
    return discountCode;
  }
}
