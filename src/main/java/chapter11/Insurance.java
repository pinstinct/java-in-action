package chapter11;

public class Insurance {

  /**
   * {@code Optional<String>}이 아니라 {@code String} 형식으로 선언되어 있는데, 이는 보험회사는 반드시 이름을 가져야 함을 보여준다. 따라서
   * 보험회사 이름을 참조할 때 NullPointerException이 발생할 수 도 있다. 하지만 보험회사 이름이 null 인지 확인하는 코드를 추가할 필요는 없다.
   * 보험회사는 반드시 이름을 가져야 하며 이름이 없는 보험회사를 발견했다면 예외를 처리하는 코드를 추가하는 것이 아니라 보험회사 이름이 없는 이유가 무엇인지 밝혀서 문제를
   * 해결해야 한다.
   */
  private String name;  // 보험회사는 반드시 이름을 가져야 함

  public String getName() {
    return name;
  }
}
