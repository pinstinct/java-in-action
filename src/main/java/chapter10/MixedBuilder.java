package chapter10;

import chapter10.Trade.Type;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class MixedBuilder {

  public static Order forCustomer(String customer, MixedTradeBuilder... builders) {
    Order order = new Order();
    order.setCustomer(customer);
    Stream.of(builders).forEach(b -> order.addTrade(b.trade));
    return order;
  }

  public static MixedTradeBuilder buy(Consumer<MixedTradeBuilder> consumer) {
    return buildTrade(consumer, Type.BUY);
  }

  public static MixedTradeBuilder sell(Consumer<MixedTradeBuilder> consumer) {
    return buildTrade(consumer, Type.SELL);
  }

  private static MixedTradeBuilder buildTrade(Consumer<MixedTradeBuilder> consumer, Type buy) {
    MixedTradeBuilder builder = new MixedTradeBuilder();
    builder.trade.setType(buy);
    consumer.accept(builder);
    return builder;
  }
}
