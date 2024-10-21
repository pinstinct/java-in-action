package chapter9;

import java.util.function.Consumer;

public class OnlineBankingLambda {

  /**
   * 온라인 뱅킹 알고리즘이 해야 할 일
   */
  public void processCustomer(int id, Consumer<Customer> makeCustomerHappy) {
    Customer c = Database.getCustomerWithId(id);
    makeCustomerHappy.accept(c);
  }

  // 더미 Customer 클래스
  static public class Customer {

  }

  // 더비 Database 클래스
  static public class Database {

    static Customer getCustomerWithId(int id) {
      return new Customer();
    }
  }
}
