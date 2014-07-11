package com.springtour.otg.domain.model.transaction;

import static org.junit.Assert.*;

import java.util.*;

import org.jmock.*;
import org.junit.*;

import com.springtour.otg.domain.model.notification.*;
import com.springtour.test.*;

public class DeadTransactionSpecificationUnitTests extends
		AbstractJMockUnitTests {

	private DeadTransactionSpecification spec;

	private CheckingBatchConfigurations configurations = context
			.mock(CheckingBatchConfigurations.class);

	private Date now = new Date();

	@Before
	public void init() {
		spec = new DeadTransactionSpecification(configurations, now);
	}

	@Test
	public void returnsTrueIfTxnIsRequestedAndTheRoundTransactionPassingThroughIsGreaterThanDeadRound()
			throws Throwable {

		final Transaction transaction = new TransactionFixture().build();

		context.checking(new Expectations() {
			{
				allowing(configurations).roundFor(
						transaction.getWhenRequested(), now);
				will(returnValue(12L));

				allowing(configurations).isGreaterThanDeadRound(12L);
				will(returnValue(true));
			}
		});

		boolean actual = spec.satisfiedBy(transaction);
		assertTrue(actual);
	}

	@Test
	public void returnsTrueIfTxnIsRequestedAndTheRoundTransactionPassingThroughIsNotGreaterThanDeadRound()
			throws Throwable {

		final Transaction transaction = new TransactionFixture().build();

		context.checking(new Expectations() {
			{
				allowing(configurations).roundFor(
						transaction.getWhenRequested(), now);
				will(returnValue(12L));

				allowing(configurations).isGreaterThanDeadRound(12L);
				will(returnValue(false));
			}
		});

		boolean actual = spec.satisfiedBy(transaction);
		assertFalse(actual);
	}

	@Test
	public void returnsTrueIfTxnIsNotRequestedAndTheRoundTransactionPassingThroughIsGreaterThanDeadRound()
			throws Throwable {

		final Transaction transaction = new TransactionFixture().build();
		transaction.handle(new NotificationFixture().build(), new Date());

		context.checking(new Expectations() {
			{
				allowing(configurations).roundFor(
						transaction.getWhenRequested(), now);
				will(returnValue(12L));

				allowing(configurations).isGreaterThanDeadRound(12L);
				will(returnValue(true));
			}
		});

		boolean actual = spec.satisfiedBy(transaction);
		assertFalse(actual);
	}
}
