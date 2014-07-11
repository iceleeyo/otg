package com.springtour.otg.application.impl;

import java.util.*;

import org.jmock.*;
import org.junit.*;

import com.springtour.otg.application.*;
import com.springtour.otg.domain.model.notification.*;
import com.springtour.otg.domain.model.transaction.*;
import com.springtour.otg.domain.service.*;
import com.springtour.otg.infrastructure.logging.LoggingSupport;
import com.springtour.otg.infrastructure.time.*;
import com.springtour.test.*;

public class CheckTransactionServiceImplUnitTests extends
		AbstractJMockUnitTests {
	private CheckTransactionServiceImpl target = new CheckTransactionServiceImpl();

	private TransactionRepository transactionRepository = context
			.mock(TransactionRepository.class);

	private TransactionChecker transactionChecker = context
			.mock(TransactionChecker.class);

	private Transaction transaction = context.mock(Transaction.class);

	private Clock clock = context.mock(Clock.class);

	private TransactionNo transactionNo = new TransactionFixture().build()
			.getTransactionNo();

	private NotificationService notificationService = context
			.mock(NotificationService.class);

	private ApplicationEvents applicationEvents = context
			.mock(ApplicationEvents.class);

	private TransactionCheckingEvents checkingEvents = context
			.mock(TransactionCheckingEvents.class);

	private String extTxnNo = "ext txn no";

	private Charge charged = Charge.SUCCESS;

	private Notification notification = new NotificationFixture().build();

	@Before
	public void injects() {
		target.setTransactionChecker(transactionChecker);
		target.setTransactionRepository(transactionRepository);
		target.setApplicationEvents(applicationEvents);
		target.setCheckingEvents(checkingEvents);
		target.setNotificationService(notificationService);
		target.setClock(clock);
	}

	@Test
	public void firesCheckingProcedureAndHandlesValidCheckResultWhenOnMessage()
			throws Throwable {

		final Date now = now();

		final CheckResult checkResult = CheckResult.valid(transactionNo,
				extTxnNo, charged);

		context.checking(new Expectations() {
			{
				allowing(transactionRepository).find(transactionNo);
				will(returnValue(transaction));

				allowing(transactionChecker).check(transaction);
				will(returnValue(checkResult));

				allowing(clock).now();
				will(returnValue(now));

				oneOf(transaction).regardedAsValidAt(now);

				oneOf(transactionRepository).store(transaction);
			}
		});

		target.check(transactionNo);
	}

	@Test
	public void firesCheckingProcedureAndHandlesInvalidCheckResultWhenOnMessage()
			throws Throwable {

		final Date now = now();

		final CheckResult checkResult = CheckResult.invalid(transactionNo,
				extTxnNo, charged);

		context.checking(new Expectations() {
			{
				allowing(transactionRepository).find(transactionNo);
				will(returnValue(transaction));

				allowing(transactionChecker).check(transaction);
				will(returnValue(checkResult));

				allowing(clock).now();
				will(returnValue(now));

				oneOf(transaction).regardedAsInvalidAt(now);

				oneOf(checkingEvents).notifySpottingInvalid(transaction);

				oneOf(transactionRepository).store(transaction);
			}
		});

		target.check(transactionNo);
	}

	@Test
	public void firesCheckingProcedureAndIgnoresUncheckedResultWhenOnMessage()
			throws Throwable {

		final Date now = now();

		final CheckResult checkResult = CheckResult.unhandled(transactionNo);

		context.checking(new Expectations() {
			{
				allowing(transactionRepository).find(transactionNo);
				will(returnValue(transaction));

				allowing(transactionChecker).check(transaction);
				will(returnValue(checkResult));

			}
		});

		target.check(transactionNo);
	}

	@Test
	public void firesCheckingProcedureAndNotifesNotificationLostWhenOnMessage()
			throws Throwable {

		final CheckResult checkResult = CheckResult.notificationLost(
				transactionNo, extTxnNo, charged);

		context.checking(new Expectations() {
			{

				allowing(transactionRepository).find(transactionNo);
				will(returnValue(transaction));

				allowing(transactionChecker).check(transaction);
				will(returnValue(checkResult));

				oneOf(notificationService).retrieve(transactionNo, extTxnNo,
						charged, SynchronizingMethod.CHECK);
				will(returnValue(notification));

				oneOf(applicationEvents).notificationWasReceived(notification);
			}
		});

		target.check(transactionNo);
	}

	@Test
	public void updatesTransactionAsDeadWhenMarkAsDead() throws Throwable {

		final Date now = now();

		context.checking(new Expectations() {
			{

				allowing(transactionRepository).find(transactionNo);
				will(returnValue(transaction));

				allowing(clock).now();
				will(returnValue(now));

				allowing(transactionChecker).check(transaction);

				oneOf(transaction).regardedAsDeadAt(now);

				oneOf(transactionRepository).store(transaction);
			}
		});

		target.retrieveOrMarkAsDead(transactionNo);
	}

	@Test
	public void retrieveWhenNotificationLost() throws Throwable {

		final CheckResult checkResult = CheckResult.notificationLost(
				transactionNo, extTxnNo, charged);

		context.checking(new Expectations() {
			{

				allowing(transactionRepository).find(transactionNo);
				will(returnValue(transaction));

				allowing(transactionChecker).check(transaction);
				will(returnValue(checkResult));

				oneOf(notificationService).retrieve(transactionNo, extTxnNo,
						charged, SynchronizingMethod.CHECK);
				will(returnValue(notification));

				oneOf(applicationEvents).notificationWasReceived(notification);
			}
		});

		target.retrieveOrMarkAsDead(transactionNo);
	}

	@Test
	public void retrievesTxnWhenFailsInChecking() throws Throwable {
		
		final Date now = now();
		
		context.checking(new Expectations() {
			{

				allowing(transactionRepository).find(transactionNo);
				will(returnValue(transaction));

				allowing(transactionChecker).check(transaction);
				will(throwException(new IllegalArgumentException("11111")));
				
				allowing(clock).now();
				will(returnValue(now));

				oneOf(transaction).regardedAsDeadAt(now);

				oneOf(transactionRepository).store(transaction);
			}
		});

		target.retrieveOrMarkAsDead(transactionNo);
	}

	private Date now() {
		return new Date();
	}
}
