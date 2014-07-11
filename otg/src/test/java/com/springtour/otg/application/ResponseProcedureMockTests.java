package com.springtour.otg.application;

import com.springtour.otg.application.exception.CannotMakePaymentException;
import java.math.BigDecimal;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.springtour.otg.application.exception.DuplicateResponseException;
import com.springtour.otg.application.exception.IllegalAmountException;
import com.springtour.otg.domain.model.notification.Notification;
import com.springtour.otg.domain.model.notification.NotificationFixture;
import com.springtour.otg.domain.model.transaction.HandlingActivity;
import com.springtour.otg.domain.model.transaction.Transaction;
import com.springtour.otg.domain.model.transaction.TransactionNo;
import com.springtour.otg.domain.service.MakePaymentService;
import com.springtour.otg.domain.shared.Money;

@RunWith(JMock.class)
public class ResponseProcedureMockTests /* extends JUnit4Mockery */{
	// {
	// setImposteriser(ClassImposteriser.INSTANCE);
	// }

	private Mockery context = new JUnit4Mockery() {

		{
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};
	private ResponseProcedure target;
	// @Mock
	private MakePaymentService makePaymentService = context
			.mock(MakePaymentService.class);
	// @Mock
	private TransactingService transactingService = context
			.mock(TransactingService.class);
	private Transaction transaction = context.mock(Transaction.class);

	private final HandlingActivity handlingActivity = new HandlingActivity();
	private Notification notification = new NotificationFixture().build();

	@Before
	public void setUp() {
		target = new ResponseProcedure();
		target.setMakePaymentService(makePaymentService);
		target.setTransactingService(transactingService);
		handlingActivity.setNotification(notification);
	}

	@Test
	public void handlesAChargedSuccessNotificationThenMakesAPaymentThenConcludesTheTransaction()
			throws Exception {
		final String notificationSeq = "12123";
		final String transactionNo = "1";
		final TransactionNo txnNo = new TransactionNo(transactionNo);

		final boolean isSuccess = true;
		final Notification notification = new NotificationFixture()
				.buildWithCheck();
		handlingActivity.setNotification(notification);

		context.checking(new Expectations() {

			{
				{

					allowing(transaction).getTransactionNo();
					will(returnValue(txnNo));

					oneOf(transactingService).handle(notificationSeq);
					will(returnValue(transaction));

					oneOf(transaction).isResponsedSuccess();
					will(returnValue(isSuccess));

					oneOf(makePaymentService).makePayment(transaction);

					oneOf(transactingService).conclude(txnNo);

				}
			}
		});

		target.handle(notificationSeq);
	}

	@Test
	public void handlesAChargedFailureNotification() throws Exception {
		final String notificationSeq = "12123";
		final boolean isSuccess = false;

		context.checking(new Expectations() {

			{
				{

					oneOf(transactingService).handle(notificationSeq);
					will(returnValue(transaction));

					oneOf(transaction).isResponsedSuccess();
					will(returnValue(isSuccess));

				}
			}
		});

		target.handle(notificationSeq);
	}

	@Test(expected = DuplicateResponseException.class)
	public void throwsAnExceptionGivenADuplicateNotification() throws Exception {
		final String notificationSeq = "12123";
		final String transactionNo = "1";
		final TransactionNo txnNo = new TransactionNo(transactionNo);

		context.checking(new Expectations() {

			{
				{
					oneOf(transactingService).handle(notificationSeq);
					will(throwException(new DuplicateResponseException(txnNo,
							notificationSeq)));

				}
			}
		});

		target.handle(notificationSeq);
	}

	@Test(expected = IllegalAmountException.class)
	public void throwsAnExceptionGivenAFakeNotification() throws Exception {
		final String notificationSeq = "12123";
		final String transactionNo = "1";
		final TransactionNo txnNo = new TransactionNo(transactionNo);

		final Money current = Money.valueOf(BigDecimal.ONE);
		final Money illegal = Money.valueOf(BigDecimal.ONE, "USD");

		context.checking(new Expectations() {

			{
				{
					oneOf(transactingService).handle(notificationSeq);
					will(throwException(new IllegalAmountException(txnNo,
							current, illegal)));

				}
			}
		});

		target.handle(notificationSeq);
	}

	@Test(expected = CannotMakePaymentException.class)
	public void handlesAChargedSuccessNotificationThenMakesAPaymentButFails()
			throws Exception {
		final String notificationSeq = "12123";
		final String transactionNo = "1";
		final TransactionNo txnNo = new TransactionNo(transactionNo);

		final boolean isSuccess = true;

		context.checking(new Expectations() {

			{
				{

					oneOf(transactingService).handle(notificationSeq);
					will(returnValue(transaction));

					oneOf(transaction).isResponsedSuccess();
					will(returnValue(isSuccess));

					oneOf(makePaymentService).makePayment(transaction);
					will(throwException(new CannotMakePaymentException(
							transaction, "-1")));
					
					allowing(transaction).getTransactionNo();
					will(returnValue(txnNo));

				}
			}
		});

		target.handle(notificationSeq);

	}
}
