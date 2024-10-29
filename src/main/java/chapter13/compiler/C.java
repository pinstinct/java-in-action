package chapter13.compiler;

public class C extends D implements B, A {
  public static void main(String... args) {
    new C().hello();
  }
}
