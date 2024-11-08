package chapter19;

import java.util.function.Predicate;

/* 단순한 연결 리스트 형태의 클래스 */
public class MyLinkedList<T> implements MyList<T> {

  private final T head;
  private final MyList<T> tail;

  @Override
  public String toString() {
    return "MyLinkedList{" +
        "head=" + head +
        ", tail=" + tail +
        '}';
  }

  public MyLinkedList(T head, MyList<T> tail) {
    this.head = head;
    this.tail = tail;
  }

  @Override
  public T head() {
    return head;
  }

  @Override
  public MyList<T> tail() {
    return tail;
  }

  @Override
  public boolean isEmpty() {
    return false;
  }

  @Override
  public MyList<T> filter(Predicate<T> predicate) {
    return isEmpty() ? this : predicate.test(head()) ?
        new MyLinkedList<>(head(), tail().filter(predicate)) : tail().filter(predicate);
  }
}
