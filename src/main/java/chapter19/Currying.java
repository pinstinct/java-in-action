package chapter19;


import java.util.function.DoubleUnaryOperator;

/**
 * 변환 요소를 곱함 + 기준치 조정 요소를 적용
 */
public class Currying {

  // x는 변환하려는 값, f는 변환 요소, b는 기준치 조정 요소
  static double converter(double x, double f, double b) {
    return x * f + b;
  }

  // 한 개의 인수를 갖는 변환 함수를 생산하는 '팩토리'를 정의
  static DoubleUnaryOperator curriedConverter(double f, double b) {
    return x -> x * f + b;
  }
}
