package chapter19;


import java.util.function.Predicate;
import java.util.function.Supplier;

public class LazyList<T> implements MyList<T> {

  final T head;
  // Supplier<T>를 이용해서 게으른 리스트를 만들면 꼬리가 모두 메모리에 존재하지 않게 된다.
  final Supplier<MyList<T>> tail;

  public LazyList(T head, Supplier<MyList<T>> tail) {
    this.head = head;
    this.tail = tail;
  }

  @Override
  public String toString() {
    return "LazyList{" + "head=" + head + ", tail=" + tail + '}';
  }

  @Override
  public T head() {
    return head;
  }

  @Override
  public MyList<T> tail() {
    // tail에서는 Supplier로 게으른 동작을 생성
    return tail.get();
  }

  @Override
  public boolean isEmpty() {
    return false;
  }

  @Override
  // 게으른 필터 구현
  public MyList<T> filter(Predicate<T> predicate) {
    return isEmpty() ?
        this :  // 새로운 Empty<>()를 반환할 수도 있지만 여기서는 this로 대신
        predicate.test(head()) ?
            new LazyList<>(head(), () -> tail().filter(predicate)) :
            tail().filter(predicate);
  }

}
