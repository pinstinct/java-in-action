package chapter19;

import static chapter19.PatternMatching.simplify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("패턴 매칭 흉내 내기")
class PatternMatchingTest {

  @Test
  @DisplayName("simplify 테스트")
  void test1() {
    Expr e = new BinOp("+", new Number(5), new Number(0));
    Expr match = simplify(e);
    System.out.println(match);
  }
}