package test.dateTime;

import java.time.*;
import java.time.temporal.ChronoUnit;

public class DateTimeProg {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		basicTest();
		incrementDateByMonthWithoutPeriod();
		incrementDateByMonthWithPeriod();
		manipulationsByPeriods();
		findDifference();
		manipulationsByDuration();
		useInstants();
		DayLightSavingTime.getSample();
	}
	
	@SuppressWarnings("unused")
	static void basicTest() {
		
		System.out.println(LocalDate.now());
		System.out.println(LocalTime.now());
		
		//2018-05-09T17:28:34.054980
		System.out.println(LocalDateTime.now());//as per time zone without time zone info.
		
		//2018-05-09T17:28:34.056033+05:30[Asia/Kolkata]
		System.out.println(ZonedDateTime.now());// 05:30 hrs ahead of GMT (Greenwich Mean Time).
		
		//2018-05-09T11:58:34.056386Z, which is 11:58 = 17:28-5:30. 
		//Because we are 5:30 hrs ahead of GMT.
		System.out.println(Instant.now());// info in GMT (Greenwich Mean Time)
		
		LocalDate date1 = LocalDate.of(2017, Month.JANUARY, 11);
		LocalDate date2 = LocalDate.of(2017, 10, 18);
		
		System.out.println("date1:"+date1);
		System.out.println("date2:"+date2);
		
		LocalTime time1 = LocalTime.of(8, 45); // hours and minutes
		LocalTime time2 = LocalTime.of(1, 35, 27); // hours, minutes and seconds
		LocalTime time3 = LocalTime.of(10, 25, 50, 200); // hours, minutes, seconds and nanoseconds
		
		LocalDateTime dateTime1 = LocalDateTime.of(2013, Month.JANUARY, 19, 6, 15, 30);
		LocalDateTime dateTime2 = LocalDateTime.of(date1, time1);
		
		ZoneId zone = ZoneId.of("Asia/Kolkata");
		ZonedDateTime zoned1 = ZonedDateTime.of(2011, 1, 20,
		9, 25, 40, 100, zone);
		ZonedDateTime zoned2 = ZonedDateTime.of(date1, time1, zone);
		ZonedDateTime zoned3 = ZonedDateTime.of(dateTime1, zone);
		
		System.out.println(zoned3);
		
		ZoneId.getAvailableZoneIds().stream()
		.filter(z -> z.contains("US") || z.contains("America"))
		.sorted().forEach(System.out::println);
		
		LocalDate date = LocalDate.of(2012, Month.JANUARY, 20);
		System.out.println(date);
		date = date.plusDays(5);
		System.out.println(date);
		date = date.plusWeeks(0);
		System.out.println(date);
		date = date.plusMonths(3).plusYears(2).minusYears(4);
		System.out.println("Date Manipulations: "+date);
		
	}
	
	static void incrementDateByMonthWithoutPeriod() {
		
		LocalDate start = LocalDate.of(2012, Month.JANUARY, 1);
		LocalDate end = LocalDate.of(2014, Month.MARCH, 30);
		
		while (start.isBefore(end)) {

			System.out.println("New Date without period:" + start);
			start = start.plusMonths(1);

		}
	}
	
	static void incrementDateByMonthWithPeriod() {
		
		LocalDate start = LocalDate.of(2012, Month.JANUARY, 1);
		LocalDate end = LocalDate.of(2014, Month.MARCH, 30);
		Period period = Period.ofMonths(1);
		while (start.isBefore(end)) {

			System.out.println("New Date with period:" + start);
			start = start.plus(period);

		}
	}
	
	@SuppressWarnings("unused")
	static void waysOfDeclaringPeriod() {
		
		Period annually = Period.ofYears(1);
		Period quarterly = Period.ofMonths(3);
		Period everyNineWeeks = Period.ofWeeks(9);
		Period everyOtherDay = Period.ofDays(2);
		Period everyYearAndAWeek = Period.of(1, 0, 7);
		Period everyYearAndAMonth = Period.of(1, 1, 0);
		
	}
	
	static void manipulationsByPeriods() {
		
		LocalDate date = LocalDate.of(2012, 6, 12);
		LocalTime time = LocalTime.of(2, 15);
		LocalDateTime dateTime = LocalDateTime.of(date, time);
		Period period = Period.ofMonths(10);
		
		try {
			System.out.println(date.plus(period));		
			System.out.println(dateTime.plus(period));		
			System.out.println(time.plus(period));
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	@SuppressWarnings("unused")
	static void waysOfDeclaringDuration() {
		
		Duration daily = Duration.ofDays(1);
		Duration hourly = Duration.ofHours(1);
		Duration everyMinute = Duration.ofMinutes(1);
		Duration everyHundredthMinute = Duration.ofMinutes(100);
		Duration everyNinetySeconds = Duration.ofSeconds(90);
		Duration everyMilliSeconds = Duration.ofMillis(1);
		Duration everyNanoSeconds = Duration.ofNanos(1);
		
		//Using TemporalUnit interface with ChronoUnit as the implementor. 
		Duration dailyDuration = Duration.of(1, ChronoUnit.DAYS);
		Duration hourlyDuration = Duration.of(1, ChronoUnit.HOURS);
		Duration everyOneMinute = Duration.of(1, ChronoUnit.MINUTES);
		Duration every90Minute = Duration.of(90, ChronoUnit.MINUTES);
		Duration every10Seconds = Duration.of(10, ChronoUnit.SECONDS);
		Duration everyMilli = Duration.of(1, ChronoUnit.MILLIS);
		Duration everyNano = Duration.of(1, ChronoUnit.NANOS);
		Duration everyHalfADay = Duration.of(12, ChronoUnit.HALF_DAYS);
		
		/*
		 * API: Duration java.time.Duration.of(long amount, TemporalUnit unit) - 
		 * 'ChronoUnit' is the only implementor.
		 */
		
	}
	
	static void findDifference() {
		
		LocalTime one = LocalTime.of(5, 15);
		LocalTime two = LocalTime.of(6, 30);
		
		System.out.println(ChronoUnit.HOURS.between(one, two));
		System.out.println(ChronoUnit.MINUTES.between(one, two));
		
		LocalDate firstDate = LocalDate.of(2019, 1, 20);
		LocalDate secondDate = LocalDate.of(2016, 1, 20);
		System.out.println(ChronoUnit.YEARS.between(secondDate, firstDate));
	}
	
	static void manipulationsByDuration() {
		
		LocalDate date = LocalDate.of(2012, 1, 20);
		LocalTime time = LocalTime.of(2, 15);
		LocalDateTime dateTime = LocalDateTime.of(date, time);
		Duration duration = Duration.ofHours(27);
		
		try {
			System.out.println(dateTime.plus(duration));
			System.out.println(time.plus(duration));		
			System.out.println(date.plus(duration));
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	@SuppressWarnings("unused")
	static void useInstants() {
		
		Instant before = Instant.now();
		Instant after = Instant.now();
		
		Duration duration = Duration.between(before, after);
		System.out.println(duration.toNanos());
		
		LocalDate date = LocalDate.of(2013, 5, 25);
		LocalTime time = LocalTime.of(9, 55, 00);
		ZoneId zone = ZoneId.of("Asia/Kolkata");
		ZonedDateTime zonedDateTime = ZonedDateTime.of(date, time, zone);
		Instant instant = zonedDateTime.toInstant(); 
		System.out.println(zonedDateTime); 
		System.out.println(instant);
		
		long epochSeconds = System.currentTimeMillis()/1000;
		Instant epochBasedNow = Instant.ofEpochSecond(epochSeconds);
		System.out.println("EpochSeconds based:"+epochBasedNow);
		
		Instant now = Instant.now();
		System.out.println("Now:"+now);
		
		Duration duration1 = Duration.between(epochBasedNow, now);
		System.out.println(duration1.toNanos());
		
		//Manipulation on Instants:
		Instant nextDay = now.plus(1, ChronoUnit.DAYS);
		System.out.println(nextDay); 
		Instant nextHour = now.plus(1, ChronoUnit.HOURS);
		System.out.println(nextHour); 
		
		try {//Instants cannot be manipulated by Weeks, Months, and years.
			Instant nextWeek = now.plus(1, ChronoUnit.WEEKS);
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	
	/**
	 * Most of the United States begins Daylight Saving Time at 2:00 a.m. on the second Sunday 
	 * in March and reverts to standard time on the first Sunday in November. 
	 * In the U.S., each time zone switches at a different time.
	 * 
	 * @author sawanpatwari
	 *
	 */
	static class DayLightSavingTime{
		
		private static ZoneId zoneUS = ZoneId.of("US/Eastern");
		private static ZoneId zoneIndia = ZoneId.of("Asia/Kolkata");
		
		private static void springForward(ZoneId zone) {
			
			System.out.println("Testing 'Spring Forward' for the zone - '" +zone.getId()+"'"+ " [STARTED]");
			
			//Spring Forward: The second Sunday in March, which is 13th March, 2016.
			LocalDate date = LocalDate.of(2016, Month.MARCH, 13);
			LocalTime time = LocalTime.of(1, 30);
			
			ZonedDateTime dateTime = ZonedDateTime.of(date, time, zone);
			
			System.out.print("Before adding 1 hr at ");
			System.out.println(dateTime.getHour() + ":" + dateTime.getMinute());
			
			System.out.println("Adding 1 hr to 1:30 AM.");
			dateTime = dateTime.plusHours(1);//API will find the right time based on the time-zone.
			
			System.out.print("After adding 1 hr to 1:30 AM.");
			System.out.println(" Time is: "+dateTime.getHour() + ":" + dateTime.getMinute());
			
			System.out.println("Testing 'Spring Forward' for the zone - '" +zone.getId()+"'"+ " [ENDED]");

		}
		
		private static void fallBack(ZoneId zone) {
			
			System.out.println("Testing 'Fall Back' for the zone - '" +zone.getId()+"'"+ " [STARTED]");
			
			//Fall Back: The first Sunday in November, which is 6th November, 2016.
			LocalDate date = LocalDate.of(2016, Month.NOVEMBER, 6);
			LocalTime time = LocalTime.of(1, 30);
			
			ZonedDateTime dateTime = ZonedDateTime.of(date, time, zone);
			
			System.out.print("Before adding 1 hr at ");
			System.out.println(dateTime.getHour() + ":" + dateTime.getMinute());
			
			System.out.println("Adding 1 hr to 1:30 AM.");
			dateTime = dateTime.plusHours(1);//API will find the right time based on the time-zone.
			
			System.out.print("After adding 1 hr to 1:30 AM.");
			System.out.println(" Time is:"+dateTime.getHour() + ":" + dateTime.getMinute());
			
			System.out.print("After adding 2 hr to 1:30 AM.");
			dateTime = dateTime.plusHours(1);
			System.out.println(" Time is:"+dateTime.getHour() + ":" + dateTime.getMinute());
			
			System.out.println("Testing 'Fall Back' for the zone - '" +zone.getId()+"'"+ " [ENDED]");
		}
		
		private static void weObserveDayLightSavingTime() { 
			System.out.println("Time Zone:"+zoneUS.getId()+". We do observe 'Day Light Saving Time'.");
			springForward(zoneUS);
			fallBack(zoneUS);
		}
		
		private static void weDoNotObserveDayLightSavingTime() {
			System.out.println("Time Zone:"+zoneIndia.getId()+". We do not observe 'Day Light Saving Time'.");
			springForward(zoneIndia);
			fallBack(zoneIndia);
		}
		
		static void getSample() {
			weObserveDayLightSavingTime();
			weDoNotObserveDayLightSavingTime();
		}
	}

}

/*
 * Note-1: During Java project upgrade, need to remove all Calendar based code to 1.8 version based LocalDate code.
 * Should also remove old date manipulation based code.
 * 
 * Note-2: Date Manipulations functions:
 * 	plusYears/ minusYears   
	plusMonths / minusMonths   
	plusWeeks / minusWeeks   
	plusDays / minusDays   
	plusHours / minusHours   
	plusMinutes / minusMinutes   
	plusSeconds / minusSeconds   
	plusNanos / minusNanos 
	
	1. Days based manipulation functions are available for LocalDate. Time based ones are for LocalTime. 
	   Days and Time are both available for LocalDateTime.
	   
   Note-3: All manipulations functions return new instances. Thus, need to use assignment operator. 
   Example: 
   	LocalDate date = LocalDate.of(2012, Month.JANUARY, 20);
   	date = date.plusMonths(3).plusYears(2).minusYears(4);
   
   Note-4: 'Period' class is not for LocalTime. 'Duration' class needs to be used. 
   'Duration' class is not applicable for LocalDate. However, both the classes can 
   be used for ZonedDateTime.
   
   
 */
