package chapter8;

import static java.util.Map.entry;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
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
      assertThrows(UnsupportedOperationException.class,
          () -> friends.add("lee"));  // 요소 추가 불가능, UnsupportedOperationException 발생
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
      assertThrows(UnsupportedOperationException.class, () -> friends.set(0, "kim"));  // 요소 수정 불가능
      assertThrows(UnsupportedOperationException.class, () -> friends.add("lee"));  // 요소 추가 불가능
    }

    @Test
    @DisplayName("집합 팩토리")
    void test5() {
      Set<String> friends = Set.of("Raphael", "Olivia", "Thibaut");
      System.out.println(friends);

      // 중복 요소가 있으면, IllegalArgumentException 발생
      assertThrows(IllegalArgumentException.class, () -> Set.of("Raphael", "Olivia", "Olivia"));
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

    Map<String, String> favoriteMovies = new HashMap<>(Map.ofEntries(
        entry("Raphael", "Star Wars"),
        entry("Cristina", "Matrix"),
        entry("Olivia", "James Bond")
    ));

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

    Map<String, String> family = Map.ofEntries(
        entry("Teo", "Star Wars"),
        entry("Cristina", "James Bond")
    );

    @Test
    @DisplayName("삭제 패턴 - 자바 8 이전")
    void test7() {
      String key = "Raphael";
      String value = "Jack Reacher 2";
      if (favoriteMovies.containsKey(key) && Objects.equals(favoriteMovies.get(key), value)) {
        favoriteMovies.remove(key);
        System.out.println(favoriteMovies);
        System.out.println(true);
      } else {
        System.out.println(favoriteMovies);
        System.out.println(false);
      }
    }

    @Test
    @DisplayName("삭제 패턴 - 자바 8 이후")
    void test8() {
      String key = "Raphael";
      String value = "Jack Reacher 2";
      favoriteMovies.remove(key, value);
      System.out.println(favoriteMovies);
    }

    @Test
    @DisplayName("교체 패턴")
    void test9() {
      favoriteMovies.replaceAll((friend, movie) -> movie.toUpperCase());
      System.out.println(favoriteMovies);
    }

    @Test
    @DisplayName("합침 - 중복된 키가 없는 경우")
    void test10() {
      Map<String, String> friends = Map.ofEntries(entry("Raphael", "Star Wars"));

      Map<String, String> everyone = new HashMap<>(family);
      everyone.putAll(friends);
      System.out.println(everyone);
    }

    @Test
    @DisplayName("합침 - 중복된 키가 있는 경우")
    void test11() {
      Map<String, String> friends = Map.ofEntries(
          entry("Raphael", "Star Wars"),
          entry("Cristina", "Matrix")
      );

      Map<String, String> everyone = new HashMap<>(family);
      friends.forEach((k, v) -> everyone.merge(k, v,
          (movie1, movie2) -> movie1 + " & " + movie2));  // 중복된 키가 있으면 두 값을 연결
      System.out.println(everyone);
    }

    @Test
    @DisplayName("합침 - 초기화 검사 (자바 8 이전)")
    void test12() {
      Map<String, Long> moviesToCount = new HashMap<>();
      String movieName = "JamesBond";
      Long count = moviesToCount.get(movieName);
      if (count == null) {
        moviesToCount.put(movieName, 1L);
      }
      else {
        moviesToCount.put(movieName, count + 1);
      }
      System.out.println(moviesToCount);
    }

    @Test
    @DisplayName("합침 - 초기화 검사 (자바 8 이후)")
    void test13() {
      Map<String, Long> moviesToCount = new HashMap<>();
      String movieName = "JamesBond";
      // merge(key, 초기화 값, BiFunction)
      moviesToCount.merge(movieName, 1L, (key, count) -> count + 1L);
      System.out.println(moviesToCount);
    }

    @Test
    @DisplayName("퀴즈 8-2: 다음의 코드를 단순화 할 수 있는 방법은?")
    void test14() {
      Map<String, Integer> movies = new HashMap<>();
      movies.put("JamesBond", 20);
      movies.put("Matrix", 15);
      movies.put("Harry Potter", 5);

      // 문제
      Iterator<Map.Entry<String, Integer>> iterator = movies.entrySet().iterator();
      while (iterator.hasNext()) {
        Map.Entry<String, Integer> entry = iterator.next();
        if (entry.getValue() < 10) {
          iterator.remove();
        }
      }
      System.out.println(movies);

      // 데이터 복구
      movies.put("Harry Potter", 5);
      System.out.println(movies);

      // 정답
      movies.entrySet().removeIf(entry -> entry.getValue() < 10);
      System.out.println(movies);
    }
  }
}
