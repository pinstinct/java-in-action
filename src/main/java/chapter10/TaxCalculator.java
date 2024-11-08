package chapter10;

import java.util.function.DoubleUnaryOperator;

public class TaxCalculator {

  private boolean useRegional;
  private boolean useGeneral;
  private boolean useSurcharge;

  // 주문값에 적용된 모든 세금을 계산하나느 함수
  public DoubleUnaryOperator taxFunction = d -> d;

  public TaxCalculator with(DoubleUnaryOperator f) {
    taxFunction = taxFunction.andThen(f);
    return this;
  }

  public static double calculate(Order order, boolean useRegional, boolean useGeneral,
      boolean useSurcharge) {
    double value = order.getValue();
    if (useRegional) {
      value = Tax.regional(value);
    }
    if (useGeneral) {
      value = Tax.general(value);
    }
    if (useSurcharge) {
      value = Tax.surcharge(value);
    }
    return value;
  }

  public TaxCalculator withTaxRegional() {
    useRegional = true;
    return this;
  }

  public TaxCalculator withTaxGeneral() {
    useGeneral = true;
    return this;
  }

  public TaxCalculator withTaxSurcharge() {
    useSurcharge = true;
    return this;
  }

  public double calculate(Order order) {
    return calculate(order, useRegional, useGeneral, useSurcharge);
  }

  public double calculate2(Order order) {
    return taxFunction.applyAsDouble(order.getValue());
  }
}
