package chapter11;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Optional 적용 패턴")
public class OptionalTest {

  @Nested
  @DisplayName("Optional 객체 만들기")
  class CreateTest {

    @Test
    @DisplayName("빈 Optional")
    void test1() {
      Optional<Car> car = Optional.empty();
      System.out.println(car);
    }

    @Test
    @DisplayName("null이 아닌 값으로 Optional 만들기")
    void test2() {
      Car car = new Car();
      Optional<Car> optCar = Optional.of(car);
      System.out.println(optCar);
    }

    @Test
    @DisplayName("null 값으로 Optional 만들기")
    void test3() {
      Car car = null;
      Optional<Car> optCar = Optional.ofNullable(car);
      System.out.println(optCar);
    }
  }

  @Nested
  @DisplayName("맵으로 Optional의 값을 추출하고 변환하기")
  class MapToOptional {

    @Test
    @DisplayName("map 메서드 지원")
    void test1() {
      Insurance insurance = new Insurance();
      Optional<Insurance> optionalInsurance = Optional.ofNullable(insurance);
      // 스트림의 map은 스트림의 각 요소에 제공괸 함수를 적용하는 연산이다.
      // Optional 객체는 최대 요소의 개수가 한 개 이하인 데이터 컬렉션으로 생각할 수 있다.
      // Optional이 값을 포함하면 map의 인수로 제공된 함수가 값을 바꾼다. 비어있으면 아무 일도 일어나지 않는다.
      Optional<String> name = optionalInsurance.map(Insurance::getName);
      System.out.println(name);
    }
  }
}
