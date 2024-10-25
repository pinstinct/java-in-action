package chapter11;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Optional을 사용한 실용 예제")
public class OptionalPractice {

  @Test
  @DisplayName("Map<String, Object> 형식의 맵의 key로 값에 접근")
  void test1() {
    Map<String, Object> map = new HashMap<>();
    Object value = map.get("key");
    System.out.println(value);
    assertThat(value).isNull();
  }

  @Test
  @DisplayName("test1 반환 값을 Optional로 감싸서 코드 개선")
  void test2() {
    Map<Object, Object> map = new HashMap<>();
    Optional<Object> value = Optional.ofNullable(map.get("key"));
    System.out.println(value);
  }

  // 문자열을 정수 Optional로 변환하는 함수
  public Optional<Integer> stringToInt(String s) {
    try {
      // 문자열을 정수로 변환할 수 있으면 정수로 변환된 값을 포함하는 Optional 반환
      return Optional.of(Integer.parseInt(s));
    } catch (NumberFormatException e) {
      // 그렇지 않으면 빈 Optional 반환
      return Optional.empty();
    }

  }

  @Test
  @DisplayName("예외와 Optional 클래스 - stringToInt 메서드")
  void test3() {
    Optional<Integer> empty = stringToInt("abc");
    System.out.println(empty);
    assertThat(empty).isEmpty();

    Optional<Integer> number = stringToInt("12");
    System.out.println(number);
    assertThat(number).isNotEmpty();
  }

  public int readDuration(Properties properties, String name) {
    String value = properties.getProperty(name);
    if (value != null) {  // 요청한 이름에 해당하는 프로퍼티가 존재하는지 확인
      try {
        int i = Integer.parseInt(value);  // 문자열 프로퍼티를 숫자로 변환하기 위해 시도
        if (i > 0) {
          return i;
        }
      } catch (NumberFormatException nfe) {}
    }
    return 0;
  }

  @Test
  @DisplayName("응용")
  void test4() {
    Properties properties = new Properties();
    properties.setProperty("a", "5");
    properties.setProperty("b", "true");
    properties.setProperty("c", "-3");

    assertThat(readDuration(properties, "a")).isEqualTo(5);
    assertThat(readDuration(properties, "b")).isEqualTo(0);
    assertThat(readDuration(properties, "c")).isEqualTo(0);
    assertThat(readDuration(properties, "d")).isEqualTo(0);
  }

  public int readDuration2(Properties properties, String name) {
    return Optional.ofNullable(properties.getProperty(name))
        .flatMap(this::stringToInt)
        .filter(value -> value > 0)
        .orElse(0);
  }

  @Test
  @DisplayName("퀴즈 11-3 readDuration 메서드 개선하기")
  void test5() {
    Properties properties = new Properties();
    properties.setProperty("a", "5");
    properties.setProperty("b", "true");
    properties.setProperty("c", "-3");

    assertThat(readDuration(properties, "a")).isEqualTo(5);
    assertThat(readDuration(properties, "b")).isEqualTo(0);
    assertThat(readDuration(properties, "c")).isEqualTo(0);
    assertThat(readDuration(properties, "d")).isEqualTo(0);
  }
}
