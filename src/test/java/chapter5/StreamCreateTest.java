package chapter5;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.function.IntSupplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("스트림 만들기")
public class StreamCreateTest {

  @Test
  @DisplayName("값으로 스트림 만들기")
  void test1() {
    Stream<String> stream = Stream.of("Modern", "Java", "In", "Action");
    stream.map(String::toUpperCase).forEach(System.out::println);

    // 스트림 비우기
    Stream<Object> empty = Stream.empty();
    System.out.println(empty);
  }

  @Test
  @DisplayName("null이 될 수 있는 객체로 스트림 만들기")
  void test2() {
    // 방법 1
    String homeValue = System.getProperty("home");  // null 객체
    Stream<String> stream = homeValue == null ? Stream.empty() : Stream.of(homeValue);

    // 방법 2
    Stream<String> stream1 = Stream.ofNullable(System.getProperty("home"));

    // 방법 3
    Stream<String> stream2 = Stream.of("config", "home", "user")
        .flatMap(key -> Stream.ofNullable(System.getProperty(key)));
  }

  @Test
  @DisplayName("배열로 스트림 만들기")
  void test3() {
    int[] numbers = {2, 3, 5, 7, 11, 13};
    int sum = Arrays.stream(numbers).sum();
    System.out.println(sum);
  }

  @Test
  @DisplayName("파일로 스트림 만들기")
  void test4() {
    long uniqueWords = 0;
    try (Stream<String> lines = Files.lines(Paths.get("data.txt"), Charset.defaultCharset())) {
      uniqueWords = lines.flatMap(line -> Arrays.stream(line.split(" ")))
          .distinct()
          .count();
      System.out.println(uniqueWords);
    } catch (IOException e) {
      // 파일을 열다가 예외가 발생하면 처리
      System.out.println("error: " + e.getMessage());
    }
  }

  @Nested
  @DisplayName("함수로 무한 스트림 만들기")
  class Function {

    @Test
    @DisplayName("iterate")
    void test1() {
      Stream.iterate(0, n -> n + 2)  // iterate(초기값, 람다)
          .limit(10)
          .forEach(System.out::println);
    }

    @Test
    @DisplayName("iterator - 피보나치수열 집합")
    void test2() {
      Stream.iterate(new int[]{0, 1}, t -> new int[]{t[1], t[0] + t[1]})
          .limit(20)
          .forEach(t -> System.out.println("(" + t[0] + "," + t[1] + ")"));

      Stream.iterate(new int[]{0, 1}, t -> new int[]{t[1], t[0] + t[1]})
          .limit(10)
          .map(t -> t[0])
          .forEach(System.out::println);
    }

    @Test
    @DisplayName("iterate(predicate)")
    void test3() {
      IntStream.iterate(0, n -> n < 100, n -> n + 4)
          .forEach(System.out::println);

      // 이 코드는 종료되지 않음
//      IntStream.iterate(0, n -> n + 4)
//          .filter(n -> n < 100)
//          .forEach(System.out::println);

      System.out.println("===");
      IntStream.iterate(0, n -> n + 4)
          .takeWhile(n -> n < 100)
          .forEach(System.out::println);
    }

    @Test
    @DisplayName("generate")
    void test4() {
      Stream.generate(Math::random)  // generate(Supplier<T>)
          .limit(5)
          .forEach(System.out::println);
    }

    @Test
    @DisplayName("generate - 피보나치수열 집합")
    void test5() {
      // 무한 스트림을 생성하는 코드
      IntStream ones = IntStream.generate(() -> 1);

      // getAsInt를 구현해 객체를 명시적으로 전달하는 코드
      IntStream tows = IntStream.generate(new IntSupplier() {
        @Override
        public int getAsInt() {
          return 2;
        }
      });

      // 설명을 위한 코드일 뿐, 병렬 코드에서 발행자에 상태가 있으면 안전하지 않다.
      IntSupplier fib = new IntSupplier() {
        // 가변 상태 객체 (iterator를 사용했을 때는 순수한 불변 상태를 유지)
        private int previous = 0;
        private int current = 1;

        @Override
        public int getAsInt() {
          int oldPrevious = this.previous;
          int nextValue = this.previous + this.current;
          this.previous = this.current;
          this.current = nextValue;
          return oldPrevious;
        }
      };
      IntStream.generate(fib)
          .limit(10)
          .forEach(System.out::println);
    }
  }
}
