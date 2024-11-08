package chapter19;

public class BinOp extends Expr {

  String opName;
  Expr left, right;

  public BinOp(String opName, Expr left, Expr right) {
    this.opName = opName;
    this.left = left;
    this.right = right;
  }
}
