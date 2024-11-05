package chapter17;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

/**
 * Subscriber 에게 TempInfo 스트림을 전송하는 Subscription
 * */
public class TempSubscription implements Subscription {

  private final Subscriber<? super TempInfo> subscriber;
  private final String town;
  private static final ExecutorService executor = Executors.newSingleThreadExecutor();

  public TempSubscription(Subscriber<? super TempInfo> subscriber, String town) {
    this.subscriber = subscriber;
    this.town = town;
  }

  @Override
  public void request(long n) {
    // 재귀 호출로 인한 스택 오버 플로 문제를 해결하기 위해 Executor를 추가
    executor.submit(() -> {  // 다음 스레드에서 다음 요소를 구독자에게 전송
      // Subscriber 가 만든 요청을 한 개씩 반복
      for (long i = 0L; i < n; i++) {
        try {
          // 현재 온도를 Subscriber 로 전달
          subscriber.onNext(TempInfo.fetch(town));
        } catch (Exception e) {
          // 온도 가져오기를 실패하면 Subscriber 로 에러 전달
          subscriber.onError(e);
          break;
        }
      }
    });
  }

  @Override
  public void cancel() {
    // 구독이 취소되면 완료(onComplete) 신호를 Subscriber 로 전달
    subscriber.onComplete();
  }
}
