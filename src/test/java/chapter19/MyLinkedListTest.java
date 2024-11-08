package chapter19;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MyLinkedListTest {

  @Test
  @DisplayName("MyLinkedList 값 생성")
  void test1() {
    MyLinkedList<Integer> l = new MyLinkedList<>(5,
        new MyLinkedList<Integer>(10, new Empty<>()));
    System.out.println(l);
  }

  @Test
  @DisplayName("LazyList 값 생성")
  void test2() {
    LazyList<Integer> l = new LazyList<>(5, () -> new LazyList<>(10, Empty::new));
    System.out.println(l);
  }
}