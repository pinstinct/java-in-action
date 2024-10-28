package chapter12;

import static java.time.DayOfWeek.THURSDAY;
import static java.time.Month.SEPTEMBER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.Period;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.UnsupportedTemporalTypeException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@Nested
public class NewDateAndTimeTest {

  @Nested
  @DisplayName("LocalDate, LocalTime, Instant, Duration, Period 클래스")
  class NewTest {

    @Test
    @DisplayName("LocalDate 만들고 값 읽기")
    void test1() {
      LocalDate date = LocalDate.of(2017, 9, 21);
      int year = date.getYear();
      assertThat(year).isEqualTo(2017);

      Month month = date.getMonth();
      assertThat(month).isEqualTo(SEPTEMBER);

      int day = date.getDayOfMonth();
      assertThat(day).isEqualTo(21);

      DayOfWeek dayOfWeek = date.getDayOfWeek();
      assertThat(dayOfWeek).isEqualTo(THURSDAY);

      int len = date.lengthOfMonth();  // 9월의 일수 (31일)
      assertThat(len).isEqualTo(30);

      boolean leap = date.isLeapYear();  // 윤년 여부
      assertThat(leap).isEqualTo(false);
    }

    @Test
    @DisplayName("현재 날짜 정보 얻기")
    void test2() {
      LocalDate today = LocalDate.now();
      System.out.println(today);
    }

    @Test
    @DisplayName("TemporalField를 이용해서 LocalDate 값 읽기")
    void test3() {
      LocalDate date = LocalDate.of(2024, 1, 1);

      int year = date.get(ChronoField.YEAR);
      assertThat(year).isEqualTo(2024);

      int month = date.get(ChronoField.MONTH_OF_YEAR);
      assertThat(month).isEqualTo(1);

      int day = date.get(ChronoField.DAY_OF_MONTH);
      assertThat(day).isEqualTo(1);
    }

    @Test
    @DisplayName("LocalTime 만들고 값 읽기")
    void test4() {
      LocalTime time = LocalTime.of(13, 45, 20);

      int hour = time.getHour();
      assertThat(hour).isEqualTo(13);

      int minute = time.getMinute();
      assertThat(minute).isEqualTo(45);

      int second = time.getSecond();
      assertThat(second).isEqualTo(20);
    }

    @Test
    @DisplayName("날짜와 시간 문자열로 LocalDate와 LocalTime의 인스턴스 생성")
    void test5() {
      LocalDate date = LocalDate.parse("2017-09-21");
      LocalTime time = LocalTime.parse("13:34:20");
      System.out.println(date + " " + time);
    }

    @Test
    @DisplayName("날짜와 시간 조합")
    void test6() {
      LocalDate date = LocalDate.parse("2017-09-21");
      LocalTime time = LocalTime.parse("13:34:20");

      LocalDateTime dt1 = LocalDateTime.of(2017, SEPTEMBER, 21, 13, 45, 20);
      LocalDateTime dt2 = LocalDateTime.of(date, time);
      LocalDateTime dt3 = date.atTime(13, 45, 20);
      LocalDateTime dt4 = date.atTime(time);
      LocalDateTime dt5 = time.atDate(date);

      System.out.println(dt1);
      System.out.println(dt2);

      LocalDate date1 = dt1.toLocalDate();
      LocalTime time1 = dt1.toLocalTime();
    }

    @Test
    @DisplayName("Instant 클래스: 기계의 날짜와 시간")
    void test7() {
      // Instant 클래스는 나노초(10억분의 1초)의 정밀도 제공
      Instant instant = Instant.ofEpochSecond(3);
      Instant instant1 = Instant.ofEpochSecond(3, 0);
      Instant instant2 = Instant.ofEpochSecond(2, 1_000_000_000);// 2초 이후의 1억 나노초(1초)
      Instant instant3 = Instant.ofEpochSecond(4, -1_000_000_000);// 4초 이전의 1억 나노초(1초)
      assertThat(instant).isEqualTo(instant1);
      assertThat(instant1).isEqualTo(instant2);
      assertThat(instant2).isEqualTo(instant3);
      System.out.println(instant);
    }

    @Test
    @DisplayName("Instant 클래스는 사람이 읽을 수 있는 시간 정보를 제공하지 않는다.")
    void test8() {
      Instant now = Instant.now();
      System.out.println(now);

      assertThrows(UnsupportedTemporalTypeException.class, () -> now.get(ChronoField.DAY_OF_MONTH));
    }

    @Test
    @DisplayName("Duration")
    void test9() {
      LocalTime time1 = LocalTime.of(13, 45, 20);
      LocalTime time2 = LocalTime.of(13, 45, 20);
      Duration d1 = Duration.between(time1, time2);
      System.out.println(d1);

      LocalDateTime dateTime1 = LocalDateTime.now();
      LocalDateTime dateTime2 = LocalDateTime.of(2013, 2, 3, 13, 22, 11);
      Duration d3 = Duration.between(dateTime1, dateTime2);
      System.out.println(d3);

      Instant instant1 = Instant.ofEpochSecond(44 * 365 * 86400);
      Instant instant2 = Instant.now();
      Duration d2 = Duration.between(instant1, instant2);
      System.out.println(d2);
    }

    @Test
    @DisplayName("Duration 클래스의 between 메서드에 LocalDate를 전달할 수 없다.")
    void test10() {
      LocalDate date1 = LocalDate.of(2017, 9, 11);
      LocalDate date2 = LocalDate.of(2017, 9, 21);
      assertThrows(UnsupportedTemporalTypeException.class, () -> Duration.between(date1, date2));
    }

    @Test
    @DisplayName("Period")
    void test11() {
      LocalDate date1 = LocalDate.of(2017, 9, 11);
      LocalDate date2 = LocalDate.of(2017, 9, 21);
      Period days = Period.between(date1, date2);
    }

    @Test
    @DisplayName("두 시간 객체를 사용하지 않고 Duration과 Period 클래스 생성")
    void test12() {
      Duration threeMinutes = Duration.ofMinutes(3);
      Duration threeMinutes1 = Duration.of(3, ChronoUnit.MINUTES);

      Period tenDays = Period.ofDays(10);
      Period threeWeeks = Period.ofWeeks(3);
      Period twoYearsSixMonthsOneDay = Period.of(2, 6, 1);
    }
  }
}
