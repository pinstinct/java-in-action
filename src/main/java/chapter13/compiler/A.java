package chapter13.compiler;

public interface A {

  default void hello() {
    System.out.println("Hello from A");
  }
}
