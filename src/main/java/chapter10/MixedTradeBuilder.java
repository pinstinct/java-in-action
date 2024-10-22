package chapter10;

public class MixedTradeBuilder {

  public Trade trade = new Trade();

  public MixedTradeBuilder quantity(int quantity) {
    trade.setQuantity(quantity);
    return this;
  }

  public MixedTradeBuilder at(double price) {
    trade.setPrice(price);
    return this;
  }

  public MixedStockBuilder stock(String symbol) {
    return new MixedStockBuilder(this, trade, symbol);
  }
}
