package chapter10;

import chapter10.Trade.Type;
import java.util.function.Consumer;

public class LambdaOrderBuilder {

  private Order order = new Order();  // 빌더로 주문을 감쌈

  public static Order order(Consumer<LambdaOrderBuilder> consumer) {
    LambdaOrderBuilder builder = new LambdaOrderBuilder();
    consumer.accept(builder);  // 주문 빌더로 전달된 람다 표현식 실행
    return builder.order;  // OrderBuilder의 Consumer를 실행해 만들어진 주문을 반환
  }

  public void forCustomer(String customer) {
    order.setCustomer(customer);
  }

  public void buy(Consumer<LambdaTradeBuilder> consumer) {
  /* 주식 매수 주문을 만들도록 TradeBuilder 소비 */
    trade(consumer, Type.BUY);
  }

  /* 주식 매도 주문을 만들도록 TradeBuilder 소비 */
  public void sell(Consumer<LambdaTradeBuilder> consumer) {
    trade(consumer, Type.SELL);
  }

  private void trade(Consumer<LambdaTradeBuilder> consumer, Type type) {
    LambdaTradeBuilder builder = new LambdaTradeBuilder();
    builder.trade.setType(type);
    consumer.accept(builder);  // TradeBuilder로 전달할 람다 표현식 실행
    order.addTrade(builder.trade);  // TradeBuilder의 Consumer를 실행해 만든 거래를 주문에 추가
  }
}

