package chapter10;

import static chapter10.GroupingBuilder.groupOn;
import static chapter10.MethodChainingOrderBuilder.forCustomer;
import static chapter10.NestedFunctionOrderBuilder.at;
import static chapter10.NestedFunctionOrderBuilder.buy;
import static chapter10.NestedFunctionOrderBuilder.on;
import static chapter10.NestedFunctionOrderBuilder.order;
import static chapter10.NestedFunctionOrderBuilder.sell;
import static chapter10.NestedFunctionOrderBuilder.stock;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.groupingBy;

import chapter10.Trade.Type;
import constant.Colors;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@Nested
@DisplayName("람다를 이용한 도메인 전용 언어")
public class DSLTest {

  @Nested
  @DisplayName("도메인 전용 언어")
  class DomainSpecificLanguage {

    @Nested
    @DisplayName("스트림 API는 컬렉션을 조작하는 DSL")
    class StreamApi {

      @Test
      @DisplayName("반복 형식으로 로그 파일에서 에러 행을 읽는 코드")
      void test1() throws IOException {
        // 편의상 에러코드를 생략했음에도 코드가 장황하고, 의도를 파악하기 어렵다.
        List<String> errors = new ArrayList<>();
        int errorCount = 0;
        BufferedReader bufferedReader = new BufferedReader(new FileReader("abc.txt"));
        String line = bufferedReader.readLine();
        while (errorCount < 40 && line != null) {
          if (line.startsWith("ERROR")) {
            errors.add(line);
            errorCount++;
          }
          line = bufferedReader.readLine();
        }
      }

      @Test
      @DisplayName("위의 코드를 Stream 인터페이스를 이용해 함수형으로 구현")
      void test2() throws IOException {
        // Files.lines() 정적 유티리티 메서드로 Stream<String> 반환
        List<String> errors = Files.lines(Paths.get("abc.txt"))  // 파일을 열어서 문자열 스트림 생성
            .filter(line -> line.startsWith("ERROR"))  // ERROR로 시작하는 행을 필터링
            .limit(40)  // 결과를 첫 40행으로 제한
            .toList();
      }
    }
  }

  @Nested
  @DisplayName("데이터를 수집하는 DSL인 Collectors")
  class CollectorsTest {

    List<Car> cars = Arrays.asList(
        new Car("Kia", Colors.RED),
        new Car("A", Colors.GREEN)
    );

    @Test
    @DisplayName("Collectors 다중 그룹화")
    void test1() {
      // Comparator 인터페이스는 다중 필드 정렬을 지원하도록 합쳐진다.
      Map<String, Map<Colors, List<Car>>> carsByBrandAndColor = cars.stream()
          .collect(groupingBy(Car::brand, groupingBy(Car::color)));

    }

    @Test
    @DisplayName("두 Comparator 연결하는 법 - 플루언트 방식")
    void test2() {
      Comparator<Car> comparator = comparing(Car::brand).thenComparing(Car::color);
    }

    @Test
    @DisplayName("Collectors API를 이용해 다중 Collectors 만들기")
    void test3() {
      Collector<Car, ?, Map<String, Map<Colors, List<Car>>>> carGroupingCollector = groupingBy(
          Car::brand, groupingBy(Car::color));
    }

    @Test
    @DisplayName("플루언트 형식의 커스텀 빌더")
    void test4() {
      // 문제점: 반대로 그룹화를 구현해야 하므로 직관적이지 않다.
      // 자바 형식 시스템으로 이런 순서 문제를 해결할 수 없다.
      Collector<? super Car, ?, Map<String, Map<Colors, List<Car>>>> carGroupingCollector = groupOn(
          Car::color).after(Car::brand).get();
    }
  }

  @Nested
  @DisplayName("자바로 DSL을 만드는 패턴과 기법")
  class JavaDsl {

    @Test
    @DisplayName("BigBank라는 고객이 요청한 거래를 포함하는 주문 만들기")
    void test1() {
      // 코드가 너무 장황하다. 직관적인 도메인 모델을 반영할 수 있는 DSL이 필요
      Order order = new Order();
      order.setCustomer("BigBank");

      Trade trade1 = new Trade();
      trade1.setType(Type.BUY);

      Stock stock1 = new Stock();
      stock1.setSymbol("IBM");
      stock1.setMarket("NYSE");

      trade1.setStock(stock1);
      trade1.setPrice(125.00);
      trade1.setQuantity(80);
      order.addTrade(trade1);

      Trade trade2 = new Trade();
      trade2.setType(Type.BUY);

      Stock stock2 = new Stock();
      stock2.setSymbol("GOOGLE");
      stock2.setMarket("NASDAQ");

      trade2.setStock(stock2);
      trade2.setPrice(375.00);
      trade2.setQuantity(50);
      order.addTrade(trade2);
    }

    @Test
    @DisplayName("메서드 체인으로 주식 거래 주문 만들기")
    void test2() {
      // 하지만 빌더를 구현해야 한다는 것이 메서드 테인의 단점이다.
      // 또한, 도메인 개개체의 중첩 구조와 일치하게 들여쓰기를 강제하는 방법이 없다.
      Order order = forCustomer("BigBank")
          .buy(80)
          .stock("IMB")
          .on("NYSE")
          .at(125.00)
          .sell(50)
          .stock("GOOGLE")
          .on("NASDAQ")
          .at(375.00)
          .end();
    }

    @Test
    @DisplayName("중첩된 함수 이용")
    void test3() {
      // 메서드 체인에 비해 함수의 중첩 방식이 도메인 객체 계층 구조에 그대로 반영
      // 하지만, 더 많은 괄호를 사용한다는 단점이 있다.
      // 또한, 선택 사항 필드가 있으면 인수를 생략할 수 있도록 여러 메서드 오버라이드를 구현해야 한다.
      order(
          "BigBank",
          buy(80, stock("IBM", on("NYSE")), at(125.00)),
          sell(50, stock("GOOGLE", on("NASDAQ")), at(375.00))
      );
    }

    @Test
    @DisplayName("람다 표현식을 이용한 함수 시퀀싱")
    void test4() {
      // 플루언트 방식으로 거래 주문을 정의하면서,
      // 중첩 함수 형식처럼 다양한 람다 표현식의 중첩 수준과 비슷하게 도메인 객체의 계층 구조 유지
      // 단점으로는, 많은 설정 코드가 필요하며 람다 표현식 문법에 의한 잡음의 영향을 받는다.
      Order order = LambdaOrderBuilder.order(o -> {
        o.forCustomer("BigBank");
        o.buy(t -> {
          t.quantity(80);
          t.price(125.00);
          t.stock(s -> {
            s.symbol("IBM");
            s.market("NYSE");
          });
        });
        o.sell(t -> {
          t.quantity(50);
          t.price(375.00);
          t.stock(s -> {
            s.symbol("GOOGLE");
            s.market("NASDAQ");
          });
        });
      });
    }

    @Test
    @DisplayName("조합하기")
    void test5() {
      // DSL을 배우는데 오래걸리는 단점이 있다.
      Order order = MixedBuilder.forCustomer("BigBank",  // 최상위 수준 주문의 속성을 지정하는 중첩 함수
          MixedBuilder.buy(t -> t.quantity(80)
              .stock("IBM")
              .on("NYSE")
              .at(125.00)
          ),
          MixedBuilder.sell(t -> t.quantity(50)
              .stock("GOOGLE")
              .on("NASDAQ")
              .at(125.00)
          )
      );
    }

    @Test
    @DisplayName("DSL에 메서드 참조 사용하기")
    void test6() {
      // 코드가 장황하고, 각 세금에 해당하는 불리언 필드가 필요하므로 확장성도 제한적이다.
      Order order = new Order();
      double value = new TaxCalculator().withTaxRegional()
          .withTaxSurcharge()
          .calculate(order);
    }

    @Test
    @DisplayName("함수형 기능을 이용해 좀 더 간결하게 리팩터링")
    void test7() {
      Order order = new Order();
      double value = new TaxCalculator().with(Tax::regional)
          .with(Tax::surcharge)
          .calculate2(order);
    }

  }
}
