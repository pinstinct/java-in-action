package chapter3;

import static chapter3.Java8FunctionalInterface.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class Java8FunctionalInterfaceTest {

  @Test
  @DisplayName("Predicate")
  void test1() {
    Predicate<String> nonEmptyStringPredicate = (String s) -> !s.isEmpty();
    List<String> nonEmpty = filter(new ArrayList<>(), nonEmptyStringPredicate);
    System.out.println(nonEmpty);
  }
}