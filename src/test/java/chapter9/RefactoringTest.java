package chapter9;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@Nested
@DisplayName("리팩터링, 테스팅, 디버깅")
public class RefactoringTest {

  @Nested
  @DisplayName("가독성과 유연성을 개선하는 리팩터링")
  class Readability {

    @Test
    @DisplayName("익명 클래스를 람다 표현식으로 리팩터링")
    void test1() {
      // 이전 코드
      Runnable r1 = new Runnable() {
        @Override
        public void run() {
          System.out.println("Hello");
        }
      };

      // 최신 코드
      Runnable r2 = () -> System.out.println("Hello");
    }

    @Test
    @DisplayName("익명 클래스를 람다 표현식으로 변환할 수 없는 경우 - shadow variable")
    void test2() {
      int a = 10;
      Runnable r1 = () -> {
//        int a = 2;  // 컴파일 에러
        System.out.println(a);
      };

      Runnable r2 = new Runnable() {
        @Override
        public void run() {
          int a = 2;
          System.out.println(a);
        }
      };
    }

    @Test
    @DisplayName("콘텍스트 오버로딩에 따른 모호함 초래")
    void test3() {
      // 익명 클래스는 인스턴스화할 때 명시적으로 형식이 정해지는 반면
      // 람다 형식은 콘텍스트에 따라 달라지기 때문이다.
      Task t1 = new Task() {
        @Override
        public void execute() {
          System.out.println("Danger danger!!");
        }
      };

      // 명시적 형변환을 이용해 모호함을 제거
      Task t2 = () -> System.out.println("Danger danger!!");
    }

    @Test
    @DisplayName("조건부 연기 실행")
    void test4() {
      Logger logger = Logger.getLogger("tester");

      // 이 코드에는 다음과 같은 문제가 있다.
      // 1. logger의 상태가 isLoggable이라는 메서드에 의해 클라이언트 코드로 노출
      // 2. 메시지를 로깅할 때 마다 logger 객체의 상태를 매번 확인
      if (logger.isLoggable(Level.FINER)) {
        logger.finer("Problem: " + generateDiagnostic());
      }

      // 메시지를 로깅하기 전에 logger 객체가 적절한 수준으로 설정되었는지 내부적으로 확인하는 log 메서드를 사용하는 것이 바람직
      // 하지만, 인수로 전달된 메시지 수준에에서 logger가 활성화되어 있지 않더라도 항상 로깅 메시지를 평가한다.
      logger.log(Level.FINER, "Problem: " + generateDiagnostic());

      // 람다를 이용하면, 특정 조건(FINER)에서만 메시지가 생성될 수 있도록 메시지 생성 과정을 연기(defer)한다.
      logger.log(Level.FINER, () -> "Problem: " + generateDiagnostic());
    }

    private String generateDiagnostic() {
      return "error";
    }
  }
}
