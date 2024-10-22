package chapter10;

import chapter10.Trade.Type;

public class MethodChainingOrderBuilder {

  public final Order order = new Order();  // 빌더로 감싼 주문

  private MethodChainingOrderBuilder(String customer) {
    order.setCustomer(customer);
  }

  /* 고객의 주문을 만드는 정적 팩토리 메서드 */
  public static MethodChainingOrderBuilder forCustomer(String customer) {
    return new MethodChainingOrderBuilder(customer);
  }

  /* 주식을 사는 TradeBuilder 생성 */
  public TradeBuilder buy(int quantity) {
    return new TradeBuilder(this, Type.BUY, quantity);
  }

  /* 주식을 파는 TradeBuilder 생성 */
  public TradeBuilder sell(int quantity) {
    return new TradeBuilder(this, Type.SELL, quantity);
  }

  public MethodChainingOrderBuilder addTrade(Trade trade) {
    order.addTrade(trade);
    return this;  // 유연하게 추가 주문을 만들어 추가할 수 있도록 주문 빌더 자체를 반환
  }

  /* 주문 만들기를 종료하고 반환 */
  public Order end() {
    return order;
  }
}
