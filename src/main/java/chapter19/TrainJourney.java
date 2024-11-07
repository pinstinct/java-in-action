package chapter19;

/**
 * A에서 B까지 기차여행을 의미하는 가변 TrainJourney 클래스로, 간단한 단반향 연결 리스트로 구현.
 * <br>
 * 직통열차나 여정의 마지막 구간에서는 onward가 null이 된다.
 */
public class TrainJourney {

  public int price;
  public TrainJourney onward;

  public TrainJourney(int price, TrainJourney onward) {
    this.price = price;
    this.onward = onward;
  }

  @Override
  public String toString() {
    return "TrainJourney{" +
        "price=" + price +
        ", onward=" + onward +
        '}';
  }
}
