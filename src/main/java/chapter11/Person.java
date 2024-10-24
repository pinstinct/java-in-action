package chapter11;

public class Person {

  private Car car;

  public Car getCar() {
    return car;
  }

  /**
   * 코드에 문제가 없는 것처럼 보이지만, 차를 소유하지 않은 사람도 많다.
   * 이 때, getCar()를 호출하면 NullPointException이 발생한다.
   * 런타임에 NullPointException이 발생하면 프로그램 실행이 중단된다.
   * */
  public String getCarInsuranceName(Person person) {
    return person.getCar().getInsurance().getName();
  }

  /* 필요한 곳에 null 확인 코드 추가 */
  public String getCarInsuranceName2(Person person) {
    // 중첩된 if 증가, 이와 같은 반복 패턴(recurring pattern) 코드를 깊은 의심(deep doubt)이라고 부른다.
    if (person != null) {
      Car car = person.getCar();
      if (car != null) {
        Insurance insurance = car.getInsurance();
        if (insurance != null) {
          return insurance.getName();
        }
      }
    }
    return "Unknown";
  }

  /* if 블록을 줄였지만, 제 개의 출구가 생김 */
  public String getCarInsuranceName3(Person person) {
    if (person == null) {
      return "Unknown";
    }
    Car car = person.getCar();
    if (car == null) {
      return "Unknown";
    }
    Insurance insurance = car.getInsurance();
    if (insurance == null) {
      return "Unknown";
    }
    return insurance.getName();
  }
}
