package chapter10;

import java.util.function.Consumer;

public class LambdaTradeBuilder {

  public Trade trade = new Trade();

  public void quantity(int quantity) {
    trade.setQuantity(quantity);
  }

  public void price(double price) {
    trade.setPrice(price);
  }

  public void stock(Consumer<LambdaStockBuilder> consumer) {
    LambdaStockBuilder builder = new LambdaStockBuilder();
    consumer.accept(builder);
    trade.setStock(builder.stock);
  }
}
