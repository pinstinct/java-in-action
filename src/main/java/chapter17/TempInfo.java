package chapter17;

import java.util.Random;

/**
 * 현재 보고된 온도를 전달하는 자바 빈
 * 원격 온도계를 흉내 낸다. 0에서 99 사이의 화씨 온도를 임의로 만들어 연속적을 보고한다.
 */
public class TempInfo {

  public static final Random random = new Random();

  private final String town;
  private final int temp;

  public TempInfo(String town, int temp) {
    this.town = town;
    this.temp = temp;
  }

  // 정적 팩토리 메서드를 이용해 해당 도시의 TempInfo 인스턴스를 생성
  public static TempInfo fetch(String town) {
    if (random.nextInt(10) == 0) {  // 1/10 확률로 온도 가져오기 작업이 실패
      throw new RuntimeException("Error!");
    }
    return new TempInfo(town, random.nextInt(100));  // 0에서 99 사이의 임의 화씨 온도 반환
  }

  @Override
  public String toString() {
    return town + " : " + temp;
  }

  public String getTown() {
    return town;
  }

  public int getTemp() {
    return temp;
  }
}
