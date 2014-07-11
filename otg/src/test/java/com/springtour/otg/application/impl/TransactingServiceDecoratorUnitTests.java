package com.springtour.otg.application.impl;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.springtour.otg.application.TransactingService;
import com.springtour.otg.domain.model.notification.Notification;
import com.springtour.otg.domain.model.notification.NotificationFixture;
import com.springtour.otg.domain.model.transaction.HandlingActivity;
import com.springtour.otg.domain.model.transaction.OrderIdentity;
import com.springtour.otg.domain.model.transaction.Transaction;
import com.springtour.otg.domain.model.transaction.TransactionNo;
import com.springtour.otg.infrastructure.logging.LoggingSupport;
import com.springtour.otg.infrastructure.mail.MailManager;

@RunWith(JMock.class)
public class TransactingServiceDecoratorUnitTests {

	private Mockery context = new JUnit4Mockery() {

		{
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};
	private TransactingServiceDecorator target;
	private TransactingService transactingService = context
			.mock(TransactingService.class);
	private MailManager mailManager = context.mock(MailManager.class);
	private LoggingSupport loggingSupport = context.mock(LoggingSupport.class);
	private Transaction transaction = context.mock(Transaction.class);
	private String notificationSeq = "123456";
	private String transactionNo = "654321";
	protected final TransactionNo txnNo = new TransactionNo(transactionNo);
	private final HandlingActivity handlingActivity = new HandlingActivity();
	private final OrderIdentity order = new OrderIdentity("0","13245", "1235543");

	@Before
	public void init() {
		target = new TransactingServiceDecorator();
		target.setLoggingSupport(loggingSupport);
		target.setMailManager(mailManager);
		target.setTarget(transactingService);
	}

	@Test
	public void testHandleACheckNotification() {
		final Notification notification = new NotificationFixture()
				.buildWithCheck();
		handlingActivity.setNotification(notification);

		context.checking(new Expectations() {

			{
				{
					allowing(transaction).getOrderId();
					will(returnValue(order));

					allowing(loggingSupport).notifyMessage(
							"TransactionNo =" + txnNo.getNumber()
									+ ",SynchronizingMethod =C"
									+ " try handle");

					allowing(mailManager).send(
							"旅游系统订单尝试自动掉单恢复！所属应用为：0",
							"旅游系统交易流水号为：【" + txnNo.getNumber()
									+ "】，旅游系统订单尝试自动掉单恢复！", "0");

					allowing(transaction).isNotificationRetrievedByCheck();
					will(returnValue(true));

					allowing(transaction).getTransactionNo();
					will(returnValue(txnNo));
					
					allowing(transaction).getSynchronizingMethod();
					will(returnValue("C"));

					oneOf(transactingService).handle(notificationSeq);
					will(returnValue(transaction));

				}
			}
		});
		target.handle(notificationSeq);
	}

	@Test
	public void testHandleAHandMakeNotification() {
		final Notification notification = new NotificationFixture().build();
		handlingActivity.setNotification(notification);

		context.checking(new Expectations() {

			{
				{

					allowing(loggingSupport).notifyMessage(
							"TransactionNo =" + txnNo.getNumber()
							+ ",SynchronizingMethod =M"
							+ " try handle");

					allowing(transaction).isNotificationRetrievedByCheck();
					will(returnValue(false));

					allowing(transaction).getTransactionNo();
					will(returnValue(txnNo));
					
					allowing(transaction).getSynchronizingMethod();
					will(returnValue("M"));

					oneOf(transactingService).handle(notificationSeq);
					will(returnValue(transaction));

				}
			}
		});
		target.handle(notificationSeq);
	}
}
