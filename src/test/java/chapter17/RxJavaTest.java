package chapter17;

import io.reactivex.rxjava3.core.Observable;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("리액티브 라이브러리 RxJava 사용하기")
public class RxJavaTest {

  @Test
  @DisplayName("Observable 만들고 사용하기")
  void test1() {
    // just() 팩토리 메서드: 한 개 이상의 요소를 이용해 이를 방출하는 Observable로 변환
    // Observable 구독자는 onNext("first"), onNext("second"), onComplete() 순서로 메시지를 받는다.
    Observable<String> strings = Observable.just("first", "second");

    // 지정된 속도로 이벤트를 방출
    // 0에서 시작해 1초 간격으로 long 형식의 값을 무한으로 증가시켜 값을 방출
    Observable<Long> onePerSec = Observable.interval(1, TimeUnit.SECONDS);

    // Observable은 초당 한 개의 이벤트를 방출하며 메시지를 수신하면 Subscriber가 뉴욕의 온도를 추출해 출력
    onePerSec.blockingSubscribe(i -> System.out.println(TempInfo.fetch("New York")));
  }

  @Test
  @DisplayName("위의 코드에 예외 처리와 일반화")
  void test2() {
    // 매 초마다 뉴욕의 온도 보고를 방출하는 Observable 생성
    Observable<TempInfo> observable = getTemperature("New York");

    // 단순 Observer로 Observable에 가입해서 온도 출력
    observable.blockingSubscribe(new TempObserver());
  }

  /* 팩토리 메서드를 제공해 매 초마다 온도를 방출(편의상 최대 다섯 번 온도를 방출하고 종료)하는 Observable 반환 */
  @SuppressWarnings("ResultOfMethodCallIgnored")
  public static Observable<TempInfo> getTemperature(String town) {
    // Observer를 소비하는 함수로부터 Observable 만들기
    return Observable.create(emitter ->
        // 매 초마다 무한으로 증가하는 일련의 long 값을 방출하는 Observable
        Observable.interval(1, TimeUnit.SECONDS)
        .subscribe(i -> {
          // 소비된 옵저버가 아직 폐기되지 않았으면 작업을 수행
          if (!emitter.isDisposed()) {
            if (i >= 5) {  // 온도를 5번 보고했으면 옵저버를 완료하고 스트림을 종료
              emitter.onComplete();
            }
            else {
              try {
                emitter.onNext(TempInfo.fetch(town));  // 온도를 Observer로 보고
              } catch (Exception e) {
                emitter.onError(e);  // 에러가 발생하면 Observer에 알림
              }
            }
          }
        }));
  }

  @Test
  @DisplayName("화씨를 섭씨로 바꾸기")
  void test3() {
    Observable<TempInfo> observable = getCelsiusTemperature("New York");
    observable.blockingSubscribe(new TempObserver());
  }

  /* Observable에 map을 이용해 화씨를 섭씨로 변환 */
  public static Observable<TempInfo> getCelsiusTemperature(String town) {
    return getTemperature(town)  // getTemperature가 반환하는 Observable를 받아
        // 화씨를 섭씨로 바꾼 다음 또 다른 Observable 반환
        .map(temp -> new TempInfo(temp.getTown(), (temp.getTemp() - 32) * 5 / 9));
  }

  @Test
  @DisplayName("영하 온도만 거르기")
  void test4() {
    Observable<TempInfo> observable = getNegativeTemperature("New York");
    observable.blockingSubscribe(new TempObserver());
  }

  public static Observable<TempInfo> getNegativeTemperature(String town) {
    return getCelsiusTemperature(town)
        .filter(tempInfo -> tempInfo.getTemp() < 0);
  }


  @Test
  @DisplayName("한 개 이상의 도시 온도를 합치기")
  void test5() {
    Observable<TempInfo> observable = getCelsiusTemperatures("New York", "Chicago", "San Francisco");
    observable.blockingSubscribe(new TempObserver());
  }

  /* 한 개 이상 도시의 온도 보고를 합친다. */
  public static Observable<TempInfo> getCelsiusTemperatures(String... towns) {
    return Observable.merge(Arrays.stream(towns)
        .map(RxJavaTest::getCelsiusTemperature)
        .toList());
  }
 }
