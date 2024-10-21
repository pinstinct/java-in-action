package chapter9;

import static org.assertj.core.api.Assertions.assertThat;

import chapter9.ProductFactory.Product;
import java.util.function.Function;
import java.util.function.UnaryOperator;
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

  @Nested
  @DisplayName("람다로 객체지향 디자인 패턴 리팩터링하기")
  class ObjectDesignPattern {

    @Test
    @DisplayName("전략")
    void test1() {
      Validator numericValidator = new Validator(new IsNumeric());
      boolean b1 = numericValidator.validate("aaaa");
      assertThat(b1).isFalse();

      Validator lowerCaseValidator = new Validator(new IsAllLowerCase());
      boolean b2 = lowerCaseValidator.validate("bbb");
      assertThat(b2).isTrue();
    }

    @Test
    @DisplayName("전략 - 람다 표현식 사용")
    void test2() {
      // 전략을 구현하는 새로운 클래스를 구현할 필요 없이 람다 표현식을 직접 전달
      Validator numericValidator = new Validator(s -> s.matches("\\d+"));
      boolean b1 = numericValidator.validate("aaaa");
      assertThat(b1).isFalse();

      Validator lowerCaseValidator = new Validator(s -> s.matches("[a-z]+"));
      boolean b2 = lowerCaseValidator.validate("bbb");
      assertThat(b2).isTrue();
    }

    @Test
    @DisplayName("템플릿 메서드 - 람다 표현식 사용")
    void test3() {
      new OnlineBankingLambda().processCustomer(122, customer -> System.out.println(customer));
    }

    @Test
    @DisplayName("옵저버")
    void test4() {
      Feed f = new Feed();
      f.registerObserver(new NYTimes());
      f.registerObserver(new Guardian());
      f.registerObserver(new LeMonde());
      f.notifyObservers("The queen said her favourite book is Modern Java in Action!");
    }

    @Test
    @DisplayName("옵저버 - 람다 표현식 사용")
    void test5() {
      Feed f = new Feed();

      // 옵저버를 명시적으로 인스턴스화하지 않고 람다 표현식으로 직접 전달해서 실행할 동작 지정
      // 이 예제에서는 실행해야 할 동작이 비교적 간단하므로 람다 표현식으로 불필요한 코드를 제거하는 것이 바람직하다.
      f.registerObserver(tweet -> {
        if (tweet != null && tweet.contains("money")) {
          System.out.println("Breaking news in NY| " + tweet);
        }
      });
      f.registerObserver(tweet -> {
        if (tweet != null && tweet.contains("queen")) {
          System.out.println("Yet more news from London..." + tweet);
        }
      });
      f.notifyObservers("The queen said her favourite book is Modern Java in Action!");
    }

    @Test
    @DisplayName("의무 체인")
    void test6() {
      ProcessingObject<String> p1 = new HeaderTextProcessing();
      ProcessingObject<String> p2 = new SpellCheckerProcessing();
      p1.setSuccessor(p2);  // 두 작업 처리 객체를 연결
      String result = p1.handle("Aren't labdas really sexy?!!");
      System.out.println(result);
    }

    @Test
    @DisplayName("의무 체인 - 람다 표현식 사용")
    void test7() {
      // 함수 체인과 비슷
      UnaryOperator<String> headerProcessing = (String text) -> "From Raoul, Mario and Alan: "
          + text;  // 첫 번째 작업 처리 객체
      UnaryOperator<String> spellCheckerProcessing = (String text) -> text.replaceAll("labda",
          "lambda");  // 두 번째 작업 처리 객체
      Function<String, String> pipeline = headerProcessing.andThen(
          spellCheckerProcessing);  // 동작 체인으로 두 함수를 조합
      String result = pipeline.apply("Aren't labdas really sexy?!!");
      System.out.println(result);
    }

    @Test
    @DisplayName("팩토리")
    void test8() {
      // 생성자와 설정을 외부로 노출하지 않음으로써 클라이언트가 단순하게 상품을 생산할 수 있다.
      Product p = ProductFactory.createProduct("loan");
    }

    @Test
    @DisplayName("팩토리 - 람다 표현식 사용")
    void test9() {
      ProductFactoryLambda.Product p = ProductFactoryLambda.createProduct("loan");
    }
  }
}
