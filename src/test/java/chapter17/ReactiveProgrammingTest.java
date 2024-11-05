package chapter17;

import java.util.concurrent.Flow.Publisher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ReactiveProgrammingTest {

  /**
   * 구독한 Subscriber 에게 TempSubscription 을 전송하는 Publisher 반환
   */
  private static Publisher<TempInfo> getTemperatures(String town) {
    return subscriber -> subscriber.onSubscribe(new TempSubscription(subscriber, town));
  }

  @Test
  @DisplayName("Publisher를 만들고 TempSubscriber를 구독시킴")
  void test1() {
    getTemperatures("New York").subscribe(new TempSubscriber());
  }


  private static Publisher<TempInfo> getCelsiusTemperatures(String town) {
    return subscriber -> {
      TempProcessor processor = new TempProcessor();
      processor.subscribe(subscriber);
      processor.onSubscribe(new TempSubscription(processor, town));
    };
  }

  @Test
  @DisplayName("Publisher를 만들고 TempSubscriber를 구독시킴 - 섭씨변환")
  void test2() {
    getCelsiusTemperatures("New York")  // 뉴욕의 섭씨 온도를 전송할 Publisher 생성
        .subscribe(new TempSubscriber());  // TempSubscriber 를 Publisher 로 구독
  }
}
