package chapter17;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

/* 수신한 온도를 출력하는 Observer */
public class TempObserver implements Observer<TempInfo> {

  @Override
  public void onSubscribe(@NonNull Disposable d) {
  }

  @Override
  public void onNext(@NonNull TempInfo tempInfo) {
    System.out.println(tempInfo);
  }

  @Override
  public void onError(@NonNull Throwable e) {
    System.out.println("Got problem: " + e.getMessage());
  }

  @Override
  public void onComplete() {
    System.out.println("Done!");
  }
}
