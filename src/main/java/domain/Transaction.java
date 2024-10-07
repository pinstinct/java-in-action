package domain;

import java.util.Currency;

public class Transaction {

  private int price;
  private Currency currency;

  public Transaction(int price, Currency currency) {
    this.price = price;
    this.currency = currency;
  }

  @Override
  public String toString() {
    return "Transaction{" + "price=" + price + ", currency='" + currency + '\'' + '}';
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public Currency getCurrency() {
    return currency;
  }

  public void setCurrency(Currency currency) {
    this.currency = currency;
  }
}
