package chapter10;

public class MixedStockBuilder {

  private final MixedTradeBuilder builder;
  private final Trade trade;
  private final Stock stock = new Stock();

  public MixedStockBuilder(MixedTradeBuilder builder, Trade trade, String symbol) {
    this.builder = builder;
    this.trade = trade;
    stock.setSymbol(symbol);
  }

  public MixedTradeBuilder on(String market) {
    stock.setMarket(market);
    trade.setStock(stock);
    return builder;
  }
}
