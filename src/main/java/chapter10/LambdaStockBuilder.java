package chapter10;

public class LambdaStockBuilder {

  public Stock stock = new Stock();

  public void symbol(String symbol) {
    stock.setSymbol(symbol);
  }

  public void market(String market) {
    stock.setMarket(market);
  }
}
