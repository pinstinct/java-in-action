package chapter8;

import static java.util.Map.entry;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("컬렉션 API 개선")
public class CollectionApiTest {

  @Nested
  @DisplayName("컬렉션 팩토리")
  class CollectionFactory {

    @Test
    @DisplayName("작은 리스트 객체 생성 -  자바 9 이전")
    void test1() {
      List<String> friends = new ArrayList<>();
      friends.add("Raphael");
      friends.add("Olivia");
      friends.add("Thibaut");
      System.out.println(friends);
    }

    @Test
    @DisplayName("작은 리스트 객체 생성 - 자바 9 이후")
    void test2() {
      List<String> friends = Arrays.asList("Raphael", "Olivia", "Thibaut");  // 고정 크기 리스트 생성
      friends.set(0, "Kim");  // 요소 갱신 가능
      friends.add("lee");  // 요소 추가 불가능, UnsupportedOperationException 발생
      System.out.println(friends);
    }

    @Test
    @DisplayName("작은 집합 객체 생성 -  자바 9 이전")
    void test3() {
      Set<String> friends = new HashSet<>(Arrays.asList("Raphael", "Olivia", "Thibaut"));
      System.out.println(friends);

      // 또는
      Set<String> friends2 = Stream.of("Raphael", "Olivia", "Thibaut")
          .collect(Collectors.toSet());
      System.out.println(friends2);

      // 두 방법 모두 내부적으로 불필요한 객체 할당함
    }

    @Test
    @DisplayName("리스트 팩토리")
    void test4() {
      List<String> friends = List.of("Raphael", "Olivia", "Thibaut");  // 변경할 수 없는 리스트 생성
      System.out.println(friends);

      // UnsupportedOperationException 발생
      friends.set(0, "kim");  // 요소 수정 불가능
      friends.add("lee");  // 요소 추가 불가능
    }

    @Test
    @DisplayName("집합 팩토리")
    void test5() {
      Set<String> friends = Set.of("Raphael", "Olivia", "Thibaut");
      System.out.println(friends);

      // 중복 요소가 있으면, IllegalArgumentException 발생
      Set<String> friends2 = Set.of("Raphael", "Olivia", "Olivia");
    }

    @Test
    @DisplayName("맵 팩토리 - 10개 이하인 경우")
    void test6() {
      Map<String, Integer> ageOfFriends = Map.of("Raphael", 30,
          "Olivia", 25, "Thibaut", 26);
      System.out.println(ageOfFriends);
    }

    @Test
    @DisplayName("맵 팩토리 - 10개 초과인 경우")
    void test7() {
      // Map.entry<K, V> 객체를 인수로 받으며 가변 인수로 구현된 Map.ofEntries 팩토리 메서드 이용
      Map<String, Integer> ageOffFriends = Map.ofEntries(
          entry("Raphael", 30),
          entry("Olivia", 25),
          entry("Thibaut", 26)
      );
      System.out.println(ageOffFriends);
    }
  }

  @Nested
  @DisplayName("리스트와 집합 처리")
  class ListAndSetTest {

    List<String> referenceCodes = Arrays.asList("a12", "c14", "b13");

    @Test
    @DisplayName("replaceAll 메서드")
    void test1() {
      // 새 문자열 컬렉션 생성
      referenceCodes.stream()
          .map(code -> Character.toUpperCase(code.charAt(0)) + code.substring(1))
          .toList()
          .forEach(System.out::println);

      // 기존 컬렉션 수정
      referenceCodes.replaceAll(code -> Character.toUpperCase(code.charAt(0)) + code.substring(1));
      System.out.println(referenceCodes);
    }
  }

  @Nested
  @DisplayName("맵 처리")
  class MapTest {

    Map<String, Integer> ageOffFriends = Map.ofEntries(
        entry("Raphael", 30),
        entry("Olivia", 25),
        entry("Thibaut", 26)
    );

    Map<String, String> favoriteMovies = Map.ofEntries(
        entry("Raphael", "Star Wars"),
        entry("Cristina", "Matrix"),
        entry("Olivia", "James Bond")
    );

    @Test
    @DisplayName("forEach 메서드 - 자바 8 이전")
    void test1() {
      for (Map.Entry<String, Integer> entry : ageOffFriends.entrySet()) {
        String friend = entry.getKey();
        Integer age = entry.getValue();
        System.out.println(friend + " is " + age + " years old");
      }
    }

    @Test
    @DisplayName("forEach 메서드 - 자바 8 이후")
    void test2() {
      ageOffFriends.forEach(
          (friend, age) -> System.out.println(friend + " is " + age + " years old"));
    }

    @Test
    @DisplayName("정렬 메서드")
    void test3() {
      // 사람의 이름을 알파벳 순으로 스트림 요소를 처리
      favoriteMovies.entrySet().stream()
          .sorted(Entry.comparingByKey())
          .forEachOrdered(System.out::println);
    }

    @Test
    @DisplayName("getOrDefault 메서드")
    void test4() {
      String movie = favoriteMovies.getOrDefault("Olivia", "Matrix");
      System.out.println(movie);
    }

    @Test
    @DisplayName("계산 패턴 - 자바 8 이전")
    void test5() {
      Map<String, List<String>> friendsToMovies = new HashMap<>();

      String friend = "Raphael";
      List<String> movies = friendsToMovies.get(friend);
      if (movies == null) {
        movies = new ArrayList<>();
        friendsToMovies.put(friend, movies);
      }
      movies.add("Star Wars");

      System.out.println(friendsToMovies);
    }

    @Test
    @DisplayName("계산 패턴 - 자바 8 이후")
    void test6() {
      Map<String, List<String>> friendsToMovies = new HashMap<>();
      friendsToMovies.computeIfAbsent("Raphael", name -> new ArrayList<>())
          .add("Star wars");
      System.out.println(friendsToMovies);
    }
  }
}
