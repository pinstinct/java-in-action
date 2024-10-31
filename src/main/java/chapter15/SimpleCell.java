package chapter15;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.function.Consumer;

// Publisher 이며 동시에 Subscriber
public class SimpleCell implements Publisher<Integer>, Subscriber<Integer> {

  private int value = 0;
  private String name;
  private List<Subscriber> subscribers = new ArrayList<>();

  public SimpleCell(String name) {
    this.name = name;
  }

  public void subscribe(Consumer<? super Integer> onNext) {
    subscribers.add(new Subscriber<Integer>() {
      @Override
      public void onSubscribe(Subscription subscription) {

      }

      @Override
      public void onNext(Integer item) {
        onNext.accept(item);
      }

      @Override
      public void onError(Throwable throwable) {
        throwable.printStackTrace();
      }

      @Override
      public void onComplete() {

      }
    });
  }

  @Override
  public void subscribe(Subscriber<? super Integer> subscriber) {
    subscribers.add(subscriber);
  }

  private void notifyAllSubscribers() {
    subscribers.forEach(subscriber -> subscriber.onNext(this.value));
  }

  @Override
  public void onSubscribe(Subscription subscription) {

  }

  @Override
  public void onNext(Integer newValue) {
    this.value = newValue;  // 구독한 셀에 새 값이 생겼을 때 값을 갱신해서 반응(react)함
    System.out.println(this.name + ":" + this.value);
    notifyAllSubscribers();  // 값이 갱신되었음을 모든 구독자에게 알림
  }

  @Override
  public void onError(Throwable throwable) {
    throwable.printStackTrace();
  }

  @Override
  public void onComplete() {

  }
}
