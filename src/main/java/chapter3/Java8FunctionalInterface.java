package chapter3;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Java8FunctionalInterface {

  public static <T> List<T> filter(List<T> list, Predicate<T> p) {
    List<T> result = new ArrayList<>();
    for (T t: list) {
      if (p.test(t)) {
        result.add(t);
      }
    }
    return result;
  }
}
