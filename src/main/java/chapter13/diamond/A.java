package chapter13.diamond;

public interface A {

  default void hello() {
    System.out.println("Hello from A");
  }
}
