package chapter19;

public class Number extends Expr {

  int val;

  public Number(int val) {
    this.val = val;
  }

  @Override
  public String toString() {
    return "Number{" +
        "val=" + val +
        '}';
  }
}
