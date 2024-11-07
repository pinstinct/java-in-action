package chapter19;

public class TrainJourneyUtil {

  /**
   * X에서 Y까지, Y에서 Z까지의 여행을 나타내는 TrainJourney 객체를 연결해 하나의 여행을 만드는 메서드 (X -> Y -> Z)
   * */
  static TrainJourney link(TrainJourney a, TrainJourney b) {
    // a의 리스트 끝부분을 가리키는 null을 리스트 b로 대체
    // 문제: link를 호출하면 a의 경로가 갱신
    // 결과적으로 부작용을 수반하는 메서드가 됨
    if (a == null) return b;

    TrainJourney t = a;
    while (t.onward != null) {
      t = t.onward;
    }
    t.onward = b;
    return a;
  }

  /**
   * 함수형 해결 방법 사용, 기존 자료구조를 변경하지 않는다.
   * */
  static TrainJourney append(TrainJourney a, TrainJourney b) {
    return a == null ? b : new TrainJourney(a.price, append(a.onward, b));
  }
}
