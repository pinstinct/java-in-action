package chapter12;

import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.THURSDAY;
import static java.time.Month.MARCH;
import static java.time.Month.SEPTEMBER;
import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;
import static java.time.temporal.TemporalAdjusters.nextOrSame;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.Chronology;
import java.time.chrono.HijrahDate;
import java.time.chrono.IsoChronology;
import java.time.chrono.JapaneseDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.Locale;
import java.util.TimeZone;
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

  @Nested
  @DisplayName("날짜 조정, 파싱, 포매팅")
  class ParsingAndFormatting {

    @Test
    @DisplayName("절대적인 방식으로 LocalDate의 속성 바꾸기")
    void test1() {
      // 새로운 객체를 반환 (기존 객체를 바꾸지 않음)
      LocalDate date1 = LocalDate.of(2017, 9, 2);
      LocalDate date2 = date1.withYear(2011);  // 2011-09-02
      LocalDate date3 = date2.withDayOfMonth(25);  // 2011-09-25
      LocalDate date4 = date3.with(ChronoField.MONTH_OF_YEAR, 2);  // 2011-02-25

      System.out.println(date1);
      System.out.println(date2);
      System.out.println(date3);
      System.out.println(date4);

      int year = LocalDate.of(2024, 1, 1).get(ChronoField.YEAR);
      System.out.println(year);
    }

    @Test
    @DisplayName("상대적인 방식으로 LocalDate의 속성 바꾸기")
    void test2() {
      LocalDate date1 = LocalDate.of(2017, 9, 21);
      LocalDate date2 = date1.plusWeeks(1);  // 2017-09-28
      LocalDate date3 = date2.minusYears(6);  // 2011-09-28
      LocalDate date4 = date3.plus(6, ChronoUnit.MONTHS);  // 2012-03-28

      System.out.println(date1);
      System.out.println(date2);
      System.out.println(date3);
      System.out.println(date4);
    }

    @Test
    @DisplayName("퀴즈 - 다음 코드를 실행했을 때 date의 값은?")
    void test3() {
      LocalDate date = LocalDate.of(2014, 3, 18);
      date = date.with(ChronoField.MONTH_OF_YEAR, 9);  // 2014-09-18
      date = date.plusYears(2).minusDays(10);  // 2016-09-08
      date.withYear(2011);  // 인스턴스가 생성되지만 변수에 할당하지 않으므로 아무 일도 일어나지 않음
      System.out.println(date);
    }

    @Test
    @DisplayName("TemporalAdjusters 사용하기")
    void test4() {
      LocalDate date1 = LocalDate.of(2014, 3, 18);
      LocalDate date2 = date1.with(nextOrSame(DayOfWeek.SUNDAY));
      LocalDate date3 = date2.with(lastDayOfMonth());
    }

    @Test
    @DisplayName("퀴즈 - 커스텀 TemporalAdjuster 구현하기")
    void test5() {
      LocalDate date = LocalDate.of(2014, 3, 19);
      date = date.with(new NextWorkingDay());
      System.out.println(date);

      // 람다표현식 이용
      date = date.with(temporal -> {
        DayOfWeek dayOfWeek = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));
        int dayToAdd = 1;
        if (dayOfWeek == FRIDAY) {
          dayToAdd = 3;
        } else if (dayOfWeek == SATURDAY) {
          dayToAdd = 2;
        }
        return temporal.plus(dayToAdd, ChronoUnit.DAYS);
      });
      System.out.println(date);

      // 람다표현식 정의
      TemporalAdjuster nextWorkingDay = TemporalAdjusters.ofDateAdjuster(
          temporal -> {
            DayOfWeek dayOfWeek = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));
            int dayToAdd = 1;
            if (dayOfWeek == FRIDAY) {
              dayToAdd = 3;
            } else if (dayOfWeek == SATURDAY) {
              dayToAdd = 2;
            }
            return temporal.plus(dayToAdd, ChronoUnit.DAYS);
          }
      );
      date = date.with(nextWorkingDay);
      System.out.println(date);
    }

    @Test
    @DisplayName("날짜와 시간 객체 출력과 파싱")
    void test6() {
      LocalDate date = LocalDate.of(2014, 3, 18);

      // DateTimeFormatter 이용해 날짜와 시간을 특정 형식의 문자열로 만들기
      String s1 = date.format(DateTimeFormatter.BASIC_ISO_DATE);
      String s2 = date.format(DateTimeFormatter.ISO_LOCAL_DATE);
      System.out.println(s1);
      System.out.println(s2);

      // 날짜와 시간을 표현하는 문자열을 파싱해 날짜 객체로 만들기
      LocalDate date1 = LocalDate.parse("20140318", DateTimeFormatter.BASIC_ISO_DATE);
      LocalDate date2 = LocalDate.parse("2014-03-18", DateTimeFormatter.ISO_LOCAL_DATE);
      System.out.println(date1);
      System.out.println(date2);
    }

    @Test
    @DisplayName("패턴으로 DateTimeFormatter 만들기")
    void test7() {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
      LocalDate date1 = LocalDate.of(2014, 3, 18);
      String formatted = date1.format(formatter);
      LocalDate date2 = LocalDate.parse(formatted, formatter);
      System.out.println(date1);
      System.out.println(date2);
    }

    @Test
    @DisplayName("지역화된 DateTimeFormatter 만들기")
    void test8() {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d. MMMM yyyy", Locale.ITALIAN);
      LocalDate date1 = LocalDate.of(2014, 3, 18);
      String formatted = date1.format(formatter);
      System.out.println(formatted);
      LocalDate date2 = LocalDate.parse(formatted, formatter);
      System.out.println(date2);
    }

    @Test
    @DisplayName("DateTimeFormatterBuilder 클래스로 복합적인 포매터 만들기")
    void test9() {
      DateTimeFormatter formatter = new DateTimeFormatterBuilder()
          .appendText(ChronoField.DAY_OF_MONTH)
          .appendLiteral(". ")
          .appendText(ChronoField.MONTH_OF_YEAR)
          .appendLiteral(" ")
          .appendText(ChronoField.YEAR)
          .parseCaseInsensitive()
          .toFormatter(Locale.ITALIAN);
      LocalDate date = LocalDate.of(2014, 3, 18);
      String formatted = date.format(formatter);
      System.out.println(formatted);
    }

  }

  @Nested
  @DisplayName("다양한 시간대와 캘린더 활용 방법")
  class Timezone {

    @Test
    @DisplayName("시간대 사용하기")
    void test1() {
      // 지역/도시 형식으로 이루어지며 IANA Time Zone Database에서 제공하는 지역 집합 정보 사용
      ZoneId romeZone = ZoneId.of("Europe/Rome");

      // 기존의 TimeZone 객체를 ZoneId 객체로 변환
      ZoneId zoneId = TimeZone.getDefault().toZoneId();

      // 특정 시점에 시간대 적용
      LocalDate date = LocalDate.of(2014, Month.MARCH, 18);
      ZonedDateTime zdt1 = date.atStartOfDay(romeZone);
      LocalDateTime dateTime = LocalDateTime.of(2014, Month.MARCH, 18, 13, 45);
      ZonedDateTime zdt2 = dateTime.atZone(romeZone);
      Instant instant = Instant.now();
      ZonedDateTime zdt3 = instant.atZone(romeZone);

      System.out.println(zdt1);
      System.out.println(zdt2);
      System.out.println(zdt3);

      // LocalDateTime을 Instant로 바꾸기
      LocalDateTime timeForInstant = LocalDateTime.ofInstant(instant, romeZone);
      System.out.println(timeForInstant);
    }

    @Test
    @DisplayName("UTC/Greenwich 기준의 고정 오프셋")
    void test2() {
      // ZoneOffset으로는 서머타임을 제대로 처리할 수 없으므로 권장하지 않는 방식
      ZoneOffset newYorkOffset = ZoneOffset.of("-05:00");

      // ISO-8601 캘린더 시스템에서 정의하는 UTC/GMT와 오프셋으로 날짜와 시간을 표현하는 OffsetDateTime
      LocalDateTime dateTime = LocalDateTime.of(2014, MARCH, 18, 13, 45);
      OffsetDateTime offsetDateTime = OffsetDateTime.of(dateTime, newYorkOffset);
      System.out.println(dateTime);
      System.out.println(offsetDateTime);
    }

    @Test
    @DisplayName("대안 캘린더 시스템 사용하기")
    void test3() {
      LocalDate date = LocalDate.of(2014, MARCH, 18);
      JapaneseDate japaneseDate = JapaneseDate.from(date);
      System.out.println(date);
      System.out.println(japaneseDate);

      Chronology japaneseChronology = Chronology.ofLocale(Locale.JAPAN);
      // ChronoLocalDate보다는 LocalDate 사용 권장
      ChronoLocalDate now = japaneseChronology.dateNow();
      System.out.println(now);
    }

    @Test
    @DisplayName("대안 캘린더 시스템 사용하기 - 이슬람력")
    void test4() {
      // Hijrah 캘린더 시스템은 태음월에 기초
      HijrahDate ramadanDate = HijrahDate.now()
          // 현재 Hijrah 날짜를 얻은 후, 라마단의 첫 번째 날로 바꿈
          .with(ChronoField.DAY_OF_MONTH, 1)
          .with(ChronoField.MONTH_OF_YEAR, 9);
      System.out.println(
          "Ramadan starts on " + IsoChronology.INSTANCE.date(ramadanDate) + " and ends on "
              + IsoChronology.INSTANCE.date(ramadanDate.with(TemporalAdjusters.lastDayOfMonth())));
    }
  }
}
