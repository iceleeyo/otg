package com.springtour.otg;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Date;

import org.jmock.Expectations;

import com.springtour.otg.application.ApplicationEvents;
import com.springtour.otg.application.CheckTransactionService;
import com.springtour.otg.application.NotificationService;
import com.springtour.otg.domain.model.notification.Charge;
import com.springtour.otg.domain.model.notification.Notification;
import com.springtour.otg.domain.model.notification.NotificationFixture;
import com.springtour.otg.domain.model.notification.SynchronizingMethod;
import com.springtour.otg.domain.model.transaction.CheckingState;
import com.springtour.otg.domain.model.transaction.Transaction;
import com.springtour.otg.domain.model.transaction.TransactionFixture;
import com.springtour.otg.domain.model.transaction.TransactionNo;
import com.springtour.otg.domain.model.transaction.TransactionRepository;
import com.springtour.otg.domain.shared.Money;
import com.springtour.otg.infrastructure.channel.ChannelDispatcher;
import com.springtour.otg.infrastructure.channel.ExternalTransactionQueryResult;
import com.springtour.otg.infrastructure.channel.ThrowableExternalTransactionQueryResult;
import com.springtour.test.ApplicationContextGateway;
import com.springtour.test.TestDoubleBeanPostProcessor;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class MarkingSteps {
	private CheckTransactionService target;
	private TransactionRepository transactionRepository;
	private ChannelDispatcher externalTransactionQueryObject;
	private NotificationService notificationService;
	private ApplicationEvents applicationEvents;

	private TransactionNo transactionNo;
	private Transaction transaction;
	private final String extTxnNo = "ext txn no";
	private final Date now = new Date();
	private ExternalTransactionQueryResult queryResult;
	private Notification notification;

	@Before(value = "@MarkingSteps")
	public void setUp() {

		TestDoubleBeanPostProcessor.resetContext();
		TestDoubleBeanPostProcessor.addMock(
				"otg.IBatisTransactionRepositoryImpl",
				TransactionRepository.class);
		TestDoubleBeanPostProcessor.addMock("otg.ChannelDispatcher",
				ChannelDispatcher.class);
		TestDoubleBeanPostProcessor.addMock("otg.NotificationServiceImpl",
				NotificationService.class);
		TestDoubleBeanPostProcessor.addMock("otg.JmsApplicationEventsImpl",
				ApplicationEvents.class);

		final ApplicationContextGateway applicationContext = ApplicationContextGateway
				.newInstance();
		target = (CheckTransactionService) applicationContext
				.get("otg.CheckTransactionServiceImpl");

		transactionRepository = (TransactionRepository) applicationContext
				.get("otg.IBatisTransactionRepositoryImpl");
		externalTransactionQueryObject = (ChannelDispatcher) applicationContext
				.get("otg.ChannelDispatcher");
		notificationService = (NotificationService) applicationContext
				.get("otg.NotificationServiceImpl");
		applicationEvents = (ApplicationEvents) applicationContext
				.get("otg.JmsApplicationEventsImpl");

	}

	@Given("^一笔交易，金额([A-Z]{3})(\\d+)，状态(.*)$")
	public void aTransaction(String currencyCode, BigDecimal amount,
			String state) {
		// setUp();
		transaction = new TransactionFixture().specify(
				Money.valueOf(amount, currencyCode)).build();
		transactionNo = transaction.getTransactionNo();

		if (Transaction.State.CONCLUDED.name().equals(state)) {
			transaction.handle(new NotificationFixture().build(), now);
			transaction.conclude(now);
		} else if (Transaction.State.RESPONSED_SUCCESS.name().equals(state)) {
			transaction.handle(new NotificationFixture().build(), now);
		} else if (Transaction.State.RESPONSED_FAILURE.name().equals(state)) {
			transaction.handle(new NotificationFixture()
					.specify(Charge.FAILURE).build(), now);
		} else if (Transaction.State.REQUESTED.name().equals(state)) {
		} else {
			throw new IllegalArgumentException("unknown txn state[" + state
					+ "]");
		}
	}

	@When("^核对时，从供应商得知金额为([A-Z]{3})(\\d+)，扣款结果为(.*)$")
	public void checks(String currencyCode, BigDecimal amount, String charged) {
		Charge charging = charged(charged);

		queryResult = new ThrowableExternalTransactionQueryResult(charging,
				Money.valueOf(amount, currencyCode), extTxnNo);
	}

	private Charge charged(String charged) {
		if ("扣款成功".equals(charged)) {
			return Charge.SUCCESS;
		} else if ("扣款失败".equals(charged)) {
			return Charge.FAILURE;
		} else if ("未扣款".equals(charged)) {
			return null;
		} else {
			throw new IllegalArgumentException(
					"unknown charge representation [" + charged + "]");
		}
	}

	@Then("^将交易认定为(.*)，(.*)$")
	public void theTransactionShouldBeRegardedAsValid(String checkingState,
			final String followingAction) {

		notification = new NotificationFixture().build();

		TestDoubleBeanPostProcessor.context.checking(new Expectations() {

			{
				allowing(transactionRepository).find(transactionNo);
				will(returnValue(transaction));

				allowing(externalTransactionQueryObject).queryBy(transaction);
				will(returnValue(queryResult));

				allowing(transactionRepository).store(transaction);

				if ("并通知执行恢复流程".equals(followingAction)) {
					oneOf(notificationService).retrieve(transactionNo,
							extTxnNo, queryResult.chargedFromSupplier(),SynchronizingMethod.CHECK);
					will(returnValue(notification));
					oneOf(applicationEvents).notificationWasReceived(
							notification);
				} else if ("状态改为DEAD".equals(followingAction)) {
				}
			}
		});
		target.retrieveOrMarkAsDead(transactionNo);
		TestDoubleBeanPostProcessor.context.assertIsSatisfied();
		CheckingState cs = CheckingState.nameOf(checkingState);
		assertTrue("unexpected checking state", transaction.is(cs));
	}
}
