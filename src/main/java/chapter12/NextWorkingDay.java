package chapter12;

import java.time.DayOfWeek;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;

public class NextWorkingDay implements TemporalAdjuster {

  /**
   * 날짜를 하루씩 다음날로 바꾸는데 토요일과 일요일은 건너뛴다.
   * */
  @Override
  public Temporal adjustInto(Temporal temporal) {
    DayOfWeek dayOfWeek = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));  // 현재 날짜 읽기
    int dayToAdd = 1;  // 보통은 하루 추가
    if (dayOfWeek == DayOfWeek.FRIDAY) dayToAdd = 3;
    else if (dayOfWeek == DayOfWeek.SATURDAY) dayToAdd = 2;
    return temporal.plus(dayToAdd, ChronoUnit.DAYS);
  }
}
