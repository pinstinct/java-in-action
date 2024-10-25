package chapter11;

import java.util.Optional;

public class Person {

  private Optional<Car> car;  // 사람이 차를 소유할 수도 않을 수도 있으므로 Optional로 정의
  private int age;

  public int getAge() {
    return age;
  }

  public Optional<Car> getCar() {
    return car;
  }
}
