package com.adagio.java8.lesson01;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;

/**
 * 新的时间日期API是基于Joda-Time库开发的
 *
 */
public class JodaTimeDemo {

	public static void main(String[] args) {
		
//		testClock();
		
//		testTimezones();
		
//		testLocalTime();
		
//		testLocalDate();
		
		testLocalDateTime();
	}

	/**
	 * LocalDateTime表示的是日期-时间
	 */
	public static void testLocalDateTime() {
		LocalDateTime sylvester = LocalDateTime.of(2017, Month.OCTOBER, 1, 23, 59, 59);
		System.out.println(sylvester);
		
		DayOfWeek dayOfWeek = sylvester.getDayOfWeek();
		System.out.println(dayOfWeek);
		
		Month month = sylvester.getMonth();
		System.out.println(month);
		
		long minuteOfDay = sylvester.getLong(ChronoField.MINUTE_OF_DAY);
		System.out.println(minuteOfDay);
		
		//加上时区，转换成Instant，可以再转之前的Date
		Instant instant = sylvester.atZone(ZoneId.systemDefault()).toInstant();
		Date legacyDate = Date.from(instant);
		System.out.println(legacyDate);
		
		//格式化
		DateTimeFormatter df = DateTimeFormatter.ofPattern("MM dd, yyyy - HH:mm");
		LocalDateTime parsed = LocalDateTime.parse("08 03, 2014 - 07:13", df);
		String str = df.format(parsed);
		System.out.println(str);
	}

	/**
	 * 本地时间表示了一个独一无二的时间，例如：2017-10-01。这个时间是不可变的
	 */
	public static void testLocalDate() {
		LocalDate today = LocalDate.now();
		LocalDate tomorrow = today.plus(1, ChronoUnit.DAYS);
		LocalDate yesterday = tomorrow.minusDays(2);
		
		System.out.println(today);
		System.out.println(tomorrow);
		System.out.println(yesterday);
		
		
		LocalDate nationalDay = LocalDate.of(2017, Month.OCTOBER, 1);
		DayOfWeek dayOfWeek = nationalDay.getDayOfWeek();
		System.out.println(dayOfWeek);  
		
		DateTimeFormatter df = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
				.withLocale(Locale.GERMAN);
		
		LocalDate xms = LocalDate.parse("01.10.2017", df);
		System.out.println(xms);
	}

	/**
	 * 本地时间类表示一个没有指定时区的时间
	 */
	public static void testLocalTime() {
		ZoneId zone1 = ZoneId.of("Brazil/East");
		ZoneId zone2 = ZoneId.of("Asia/Shanghai");
		
		LocalTime now1 = LocalTime.now(zone1);
		LocalTime now2 = LocalTime.now(zone2);
		
		System.out.println(now1);
		System.out.println(now2);
		
		System.out.println(now1.isBefore(now2));
		
		
		long hoursBetween = ChronoUnit.HOURS.between(now1, now2);
		long minutesBetween = ChronoUnit.MINUTES.between(now1, now2);
		System.out.println(hoursBetween);
		System.out.println(minutesBetween);
		
		
		LocalTime late = LocalTime.of(23, 12, 12);
		System.out.println(late);
		
		DateTimeFormatter df = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)
				.withLocale(Locale.GERMAN);
		
		LocalTime localTime = LocalTime.parse("13:12", df);
		System.out.println(localTime);
	}

	/**
	 * 时区类的对象可以通过静态工厂方法方便地获取
	 */
	public static void testTimezones() {
		System.out.println(ZoneId.getAvailableZoneIds());
		// prints all available timezone ids

		ZoneId zone1 = ZoneId.of("Europe/Berlin");
		ZoneId zone2 = ZoneId.of("Brazil/East");
		ZoneId zone3 = ZoneId.of("Asia/Shanghai");
		
		System.out.println(zone1.getRules());
		System.out.println(zone2.getRules());
		System.out.println(zone3.getRules());
		
	}

	/**
	 * Clock提供了对当前时间和日期的访问功能
	 */
	public static void testClock() {
		Clock clock = Clock.systemDefaultZone();
		long millis = clock.millis();
		System.out.println(millis);
		
		Instant instant = clock.instant();
		Date d = Date.from(instant);
		System.out.println(d);
	}
}
