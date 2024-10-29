package chapter13.diamond;

public class D implements B, C {

  public static void main(String... args) {
    new D().hello();
  }
}
