package chapter19;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 자바로 패턴 매칭 흉내 내기 (람다를 이용하며, if-then-else 가 없어야 한다.)
 */
public class PatternMatching {

  static <T> T myIf(boolean b, Supplier<T> trueCase, Supplier<T> falseCase) {
    return b ? trueCase.get() : falseCase.get();
  }

  static <T> T patternMatchExpr(Expr e, TriFunction<String, Expr, Expr, T> binOpCase,
      Function<Integer, T> numCase, Supplier<T> defaultCase) {
    return (e instanceof BinOp) ? binOpCase.apply(((BinOp) e).opName, ((BinOp) e).left,
        ((BinOp) e).right)
        : (e instanceof Number) ? numCase.apply(((Number) e).val) : defaultCase.get();
  }

  static Expr simplify(Expr e) {
    TriFunction<String, Expr, Expr, Expr> binOpCase = (opName, left, right) -> {
      if ("+".equals(opName)) {
        if (left instanceof Number && ((Number) left).val == 0) {
          return right;
        }
        if (right instanceof Number && ((Number) right).val == 0) {
          return left;
        }
      }
      if ("*".equals(opName)) {
        if (left instanceof Number && ((Number) left).val == 1) {
          return right;
        }
        if (right instanceof Number && ((Number) right).val == 1) {
          return left;
        }
      }
      return new BinOp(opName, left, right);
    };
    Function<Integer, Expr> numCase = Number::new;  // 숫자 처리
    Supplier<Expr> defaultCase = () -> new Number(0);  // 수식을 인식할 수 없을 때 기본처리
    return patternMatchExpr(e, binOpCase, numCase, defaultCase);
  }
}
