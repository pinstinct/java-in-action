package chapter7;

import static chapter7.CustomSum.iterativeSum;
import static chapter7.CustomSum.parallelSum;
import static chapter7.CustomSum.sequentialSum;
import static chapter7.ForkJoinSumCalculator.forkJoinSum;
import static chapter7.WordCounter.countWords;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Spliterator;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;


public class ParallelStreamTest {

  private static final long N = 10_000_000L;

  @Nested
  @DisplayName("병렬 스트림")
  class Parallel {

    @Test
    @DisplayName("숫자 n을 인수로 받아서 1부터 n까지의 모든 숫자 합계를 반환하기")
    public void test1() {
      long result = sequentialSum(N);
      System.out.println(result);

      System.out.println("===");
      long result2 = iterativeSum(N);
      System.out.println(result2);
    }

    @Test
    @DisplayName("순차 스트림을 병렬 스트림으로 변환하기")
    public void test2() {
      long result = parallelSum(N);
      System.out.println(result);
    }
  }

  @Nested
  @DisplayName("포크/조인 프레임워크")
  class ForkJoin {

    @Test
    @DisplayName("forkJoinSum")
    void test1() {
      long result = forkJoinSum(N);
      System.out.println(result);
    }
  }

  final String SENTENCE = "Nel mezzo del cammin di nostra vita)[5] – half of the biblical lifespan of 70 (Psalm 89:10 in the Vulgate; numbered as Psalm 90:10 in the King James Bible). The poet finds himself lost in a dark wood (selva oscura),[6] astray from the \"straight way\" (diritta via,[7] also translatable as 'right way') of salvation.";

  @Nested
  @DisplayName("Spliterator 인터페이스")
  class SpliteratorTest {

    @Test
    @DisplayName("반복형으로 단어 수를 세기")
    void test1() {
      int result = countWordsIteratively(SENTENCE);
      System.out.println(result);
    }

    // 반복형으로 단어 수를 세는 메서드
    public int countWordsIteratively(String s) {
      int counter = 0;
      boolean lastSpace = true;
      for (char c : s.toCharArray()) {
        if (Character.isWhitespace(c)) {
          lastSpace = true;
        } else {
          if (lastSpace) {
            counter++;
          }
          lastSpace = false;
        }
      }
      return counter;
    }


    @Test
    @DisplayName("함수형으로 단어 수를 세기")
    void test2() {
      Stream<Character> stream = IntStream.range(0, SENTENCE.length()).mapToObj(SENTENCE::charAt);
      int result = countWords(stream);
      System.out.println(result);
    }

    @Test
    @DisplayName("WordCounter 병렬로 수행하기")
    void test3() {
      Stream<Character> stream = IntStream.range(0, SENTENCE.length()).mapToObj(SENTENCE::charAt);
      int result = countWords(stream.parallel());
      System.out.println(result);
      assertThat(result).isNotEqualTo(54);  // 결과가 틀림
      // 순차 스트림을 병렬 스트림으로 바꿀 때 스트림 분할 위치에 따라 잘못된 결과가 나옴
      // 해결법: 임의의 위치에서 분할하지 말고 단어가 끝나는 위치에서만 분할
    }

    @Test
    @DisplayName("WordCounterSpliterator 활용")
    void test4() {
      Spliterator<Character> spliterator = new WordCounterSpliterator(SENTENCE);
      Stream<Character> stream = StreamSupport.stream(spliterator, true);
      int result = countWords(stream);
      System.out.println(result);
    }
  }
}
