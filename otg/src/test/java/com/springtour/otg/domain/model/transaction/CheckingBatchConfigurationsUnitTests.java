package com.springtour.otg.domain.model.transaction;

import static org.junit.Assert.*;

import java.text.*;
import java.util.*;

import org.junit.*;

import com.springtour.test.*;

public class CheckingBatchConfigurationsUnitTests extends
		AbstractJMockUnitTests {

	private CheckingBatchConfigurations spec = new CheckingBatchConfigurations();

	@Before
	public void init() throws Exception {
		spec.setDeadRound(12);
		spec.setIntervalMinutes(5);
		spec.setNewBornRound(3);
		spec.setUnlivelyRound(6);
		spec.setUnlivelyTurnRounds(new int[] { 7, 10 });
	}

	@Test
	public void returnsANumberEqualsDurationMinutesDividedByRoundInterval()
			throws Throwable {

		final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss.SSS");
		Date start = simpleDateFormat.parse("2013-12-22 13:12:54.221");

		Date end = simpleDateFormat.parse("2013-12-22 13:12:54.222");

		long round = spec.roundFor(start, end);

		assertEquals(0, round);
	}

}
