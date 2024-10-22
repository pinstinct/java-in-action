package chapter10;

import static chapter10.GroupingBuilder.groupOn;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.groupingBy;

import constant.Colors;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@Nested
@DisplayName("람다를 이용한 도메인 전용 언어")
public class DSLTest {

  @Nested
  @DisplayName("도메인 전용 언어")
  class DomainSpecificLanguage {

    @Nested
    @DisplayName("스트림 API는 컬렉션을 조작하는 DSL")
    class StreamApi {

      @Test
      @DisplayName("반복 형식으로 로그 파일에서 에러 행을 읽는 코드")
      void test1() throws IOException {
        // 편의상 에러코드를 생략했음에도 코드가 장황하고, 의도를 파악하기 어렵다.
        List<String> errors = new ArrayList<>();
        int errorCount = 0;
        BufferedReader bufferedReader = new BufferedReader(new FileReader("abc.txt"));
        String line = bufferedReader.readLine();
        while (errorCount < 40 && line != null) {
          if (line.startsWith("ERROR")) {
            errors.add(line);
            errorCount++;
          }
          line = bufferedReader.readLine();
        }
      }

      @Test
      @DisplayName("위의 코드를 Stream 인터페이스를 이용해 함수형으로 구현")
      void test2() throws IOException {
        // Files.lines() 정적 유티리티 메서드로 Stream<String> 반환
        List<String> errors = Files.lines(Paths.get("abc.txt"))  // 파일을 열어서 문자열 스트림 생성
            .filter(line -> line.startsWith("ERROR"))  // ERROR로 시작하는 행을 필터링
            .limit(40)  // 결과를 첫 40행으로 제한
            .toList();
      }
    }
  }

  @Nested
  @DisplayName("데이터를 수집하는 DSL인 Collectors")
  class CollectorsTest {

    List<Car> cars = Arrays.asList(
        new Car("Kia", Colors.RED),
        new Car("A", Colors.GREEN)
    );

    @Test
    @DisplayName("Collectors 다중 그룹화")
    void test1() {
      // Comparator 인터페이스는 다중 필드 정렬을 지원하도록 합쳐진다.
      Map<String, Map<Colors, List<Car>>> carsByBrandAndColor = cars.stream()
          .collect(groupingBy(Car::brand, groupingBy(Car::color)));

    }

    @Test
    @DisplayName("두 Comparator 연결하는 법 - 플루언트 방식")
    void test2() {
      Comparator<Car> comparator = comparing(Car::brand).thenComparing(Car::color);
    }

    @Test
    @DisplayName("Collectors API를 이용해 다중 Collectors 만들기")
    void test3() {
      Collector<Car, ?, Map<String, Map<Colors, List<Car>>>> carGroupingCollector = groupingBy(
          Car::brand, groupingBy(Car::color));
    }

    @Test
    @DisplayName("플루언트 형식의 커스텀 빌더")
    void test4() {
      // 문제점: 반대로 그룹화를 구현해야 하므로 직관적이지 않다.
      // 자바 형식 시스템으로 이런 순서 문제를 해결할 수 없다.
      Collector<? super Car, ?, Map<String, Map<Colors, List<Car>>>> carGroupingCollector = groupOn(
          Car::color).after(Car::brand).get();
    }
  }
}
