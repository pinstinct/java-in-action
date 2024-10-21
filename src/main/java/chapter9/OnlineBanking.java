package chapter9;

abstract class OnlineBanking {

  /**
   * 온라인 뱅킹 알고리즘이 해야 할 일
   */
  public void processCustomer(int id) {
    Customer c = Database.getCustomerWithId(id);
    makeCustomerHappy(c);
  }

  /**
   * 각 지검은 OnlineBanking 클래스를 상속받아 makeCustomerHappy 메서드가 원하는 동작을 수행하도록 구현
   */
  abstract void makeCustomerHappy(Customer c);

  // 더미 Customer 클래스
  static private class Customer {

  }

  // 더비 Database 클래스
  static private class Database {

    static Customer getCustomerWithId(int id) {
      return new Customer();
    }
  }
}
