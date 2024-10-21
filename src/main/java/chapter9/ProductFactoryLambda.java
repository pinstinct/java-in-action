package chapter9;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ProductFactoryLambda {

  public static Product createProduct(String name) {  // 여러 인수를 전달하는 상황에서는 이 기법을 사용하기 어려움
    Supplier<Product> p = map.get(name);
    if (p != null) return p.get();
    throw new IllegalArgumentException("No such product " + name);
  }

  static interface Product {}
  static class Loan implements Product {}
  static class Stock implements Product {}
  static class Bond implements Product {}

  final static Map<String, Supplier<Product>> map = new HashMap<>();
  static {
    map.put("loan", Loan::new);
    map.put("stock", Stock::new);
    map.put("bond", Bond::new);
  }
}
