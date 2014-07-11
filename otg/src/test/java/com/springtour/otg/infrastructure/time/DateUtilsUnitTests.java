package com.springtour.otg.infrastructure.time;

import static org.junit.Assert.*;

import java.text.*;
import java.util.*;

import org.junit.*;

import com.springtour.test.*;

public class DateUtilsUnitTests extends AbstractJMockUnitTests {

	@Test
	public void returnsACopyFromAGivenDateButRemovesSecondsAndMillissenod()
			throws Exception {

		final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss.SSS");
		Date prototype = simpleDateFormat.parse("2013-12-22 13:12:54.221");

		Date expected = simpleDateFormat.parse("2013-12-22 13:12:00.000");

		Date actual = DateUtils.copyWithoutSencondsAndMillis(prototype);

		assertEquals(expected.getTime(), actual.getTime());// 这里要比较getTime()
															// 因为存在毫秒
	}

	@Test
	public void returnsAMillissecondDurationWhenCalsDurationMillis()
			throws Exception {

		final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss.SSS");
		Date start = simpleDateFormat.parse("2013-12-22 13:12:54.221");

		Date end = simpleDateFormat.parse("2013-12-22 13:12:54.222");

		assertEquals(1, DateUtils.durationMillisBetween(start, end));
	}

	@Test
	public void returnsZeroDurationMillisWhenCalsDurationMillis()
			throws Exception {

		final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss.SSS");
		Date start = simpleDateFormat.parse("2013-12-22 13:12:54.222");

		Date end = simpleDateFormat.parse("2013-12-22 13:12:54.222");

		assertEquals(0, DateUtils.durationMillisBetween(start, end));
	}

	@Test(expected = IllegalArgumentException.class)
	public void throwssExceptionsWhenCalsDurationMillisGivenReversedStartAndEnd()
			throws Exception {

		final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss.SSS");
		Date start = simpleDateFormat.parse("2013-12-22 13:12:54.221");

		Date end = simpleDateFormat.parse("2013-12-22 13:12:54.222");

		DateUtils.durationMillisBetween(end, start);
	}
	
	@Test
	public void returnsQuotientWhenCalsDurationMinutesGivenDurationLessThanOneMin()
			throws Exception {

		final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss.SSS");
		Date start = simpleDateFormat.parse("2013-12-22 13:09:53.456");

		Date end = simpleDateFormat.parse("2013-12-22 13:12:54.222");

		assertEquals(3, DateUtils.durationMinutesBetween(start, end));
	}

	@Test
	public void returnsZeroDurationMillisWhenCalsDurationMinutesGivenDurationLessThanOneMin()
			throws Exception {

		final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss.SSS");
		Date start = simpleDateFormat.parse("2013-12-22 13:12:53.456");

		Date end = simpleDateFormat.parse("2013-12-22 13:12:54.222");

		assertEquals(0, DateUtils.durationMinutesBetween(start, end));
	}

	@Test(expected = IllegalArgumentException.class)
	public void throwssExceptionsWhenCalsDurationMinutesGivenReversedStartAndEnd()
			throws Exception {

		final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss.SSS");
		Date start = simpleDateFormat.parse("2013-12-22 13:12:54.221");

		Date end = simpleDateFormat.parse("2013-12-22 13:12:54.222");

		DateUtils.durationMinutesBetween(end, start);
	}
	
	@Test
	public void returnDateAndTimeWithoutYear() throws ParseException{
		final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss.SSS");
		Date date = simpleDateFormat.parse("2013-12-22 13:12:54.221");
		assertEquals("1222131254", DateUtils.getDateAndTimeWithoutYear(date));
	}
}
