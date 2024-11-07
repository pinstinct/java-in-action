package chapter19;

import static chapter19.TrainJourneyUtil.append;
import static chapter19.TrainJourneyUtil.link;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("영속 자료구조")
class TrainJourneyUtilTest {

  @Test
  @DisplayName("파괴적인 갱신")
  void test1() {
    TrainJourney first = new TrainJourney(1, null);
    TrainJourney second = new TrainJourney(2, null);

    System.out.println(first);

    TrainJourney link = link(first, second);

    System.out.println(first);  // 파괴적 갱신, first가 변경됨
    System.out.println(link);
    assertThat(first.onward).isEqualTo(link.onward);
  }

  @Test
  @DisplayName("함수형")
  void test2() {
    TrainJourney first = new TrainJourney(1, null);
    TrainJourney second = new TrainJourney(2, null);

    System.out.println(first);

    TrainJourney append = append(first, second);

    System.out.println(first);
    System.out.println(append);
    assertThat(first.onward).isNotEqualTo(append.onward);
  }
}