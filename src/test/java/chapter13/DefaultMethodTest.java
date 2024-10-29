package chapter13;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class DefaultMethodTest {

  @Nested
  @DisplayName("디폴트 메서드")
  class DefaultMethod {

    @Test
    @DisplayName("List 인터페이스의 sort")
    void test1() {
      List<Integer> numbers = Arrays.asList(3, 5, 1, 2, 6);
      numbers.sort(Comparator.naturalOrder());
      System.out.println(numbers);
    }
  }
}
