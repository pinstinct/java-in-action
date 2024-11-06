package chapter19;

import static chapter19.Currying.curriedConverter;

import java.util.function.DoubleUnaryOperator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("커링")
class CurryingTest {

  @Test
  void test1() {
    // 팩토리를 이용해 원하는 작업을 수행하는 함수 생성
    DoubleUnaryOperator convertCtoF = curriedConverter(9.0 / 5, 32);
    DoubleUnaryOperator convertUSDtoGBP = curriedConverter(0.6, 0);
    DoubleUnaryOperator convertKmToMi = curriedConverter(0.6214, 0);

    double gbp = convertUSDtoGBP.applyAsDouble(1000);
    System.out.println(gbp);
  }
}