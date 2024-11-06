package chapter18;

import static chapter18.Subset.subsets;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SubsetTest {

  @Test
  @DisplayName("함수형 실전 연습")
  void test1() {
    List<Integer> list = List.of(1, 4, 9);
    List<List<Integer>> result = subsets(list);
    System.out.println(result);
  }
}