package chapter18;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Subset {

  /**
   * {1, 4, 9} 처럼 {@code List<Integer>}가 주어졌을 때 이것의 모든 서브 집합 멤버로 구성된 {@code List<List<Integer>>}를
   * 만드는 프로그램이다. 예를 들어 {1, 4, 9}의 서브집합은 {1, 4, 9}, {1, 4}, {1, 9}, {4, 9}, {1}, {4}, {9}, {} 다.
   */
  static List<List<Integer>> subsets(List<Integer> list) {
    if (list.isEmpty()) {  // 입력 리스트가 비어있다면 빈 리스트 자신이 서브집합이다.
      List<List<Integer>> ans = new ArrayList<>();
      ans.add(Collections.emptyList());
      return ans;
    }

    Integer first = list.get(0);
    List<Integer> rest = list.subList(1, list.size());

    List<List<Integer>> subans = subsets(rest);
    List<List<Integer>> subans2 = insertAll(first, subans);
    return concat(subans, subans2);
  }

  private static List<List<Integer>> concat(List<List<Integer>> a, List<List<Integer>> b) {
    // 인수의 정보를 직접 변경하지 않는다.
    List<List<Integer>> r = new ArrayList<>(a);
    r.addAll(b);
    return r;
  }

  private static List<List<Integer>> insertAll(Integer first, List<List<Integer>> lists) {
    List<List<Integer>> result = new ArrayList<>();
    for (List<Integer> list : lists) {
      // 리스트를 복사한 다음에 복사한 리스트에 요소를 추가한다.
      // 구조체가 가변이더라도 저수준 구조를 복사하지 않는다.
      List<Integer> copyList = new ArrayList<>();
      copyList.add(first);
      copyList.addAll(list);
      result.add(copyList);
    }
    return result;
  }


}
