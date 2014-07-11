package com.springtour.otg.infrastructure.messaging;

import javax.jms.*;

import org.jmock.*;
import org.junit.*;

import com.springtour.otg.application.*;
import com.springtour.otg.domain.model.transaction.*;
import com.springtour.otg.infrastructure.logging.*;
import com.springtour.test.*;

public class CheckingConsumerUnitTests extends AbstractJMockUnitTests {

	private CheckingConsumer target = new CheckingConsumer();
	private CheckTransactionService checkTransactionService = context
			.mock(CheckTransactionService.class);
	private TextMessage message = context.mock(TextMessage.class);
	private String txnNo = "123";
	private TransactionNo transactionNo = new TransactionNo(txnNo);
	//private LoggingSupport loggingSupport = context.mock(LoggingSupport.class);

	@Before
	public void injects() {
		target.setCheckTransactionService(checkTransactionService);
	}

	@Test
	public void sendsMessageWithTransactionNoWhenNotifyToCheck()
			throws Throwable {

		// 感觉这里这样测试的意义不大，是否改为集成测试验证Wiring会更好一些？

		context.checking(new Expectations() {
			{
				allowing(message).getText();
				will(returnValue(txnNo));

				oneOf(checkTransactionService).check(transactionNo);
			}
		});

		target.onMessage(message);
	}

	@Test
	public void catchesExceptionToAviodMessageingTransactionRollingBackWhenNotifyToCheck()
			throws Throwable {

		final IllegalArgumentException e = new IllegalArgumentException();

		context.checking(new Expectations() {
			{
				allowing(message).getText();
				will(returnValue(txnNo));

				oneOf(checkTransactionService).check(transactionNo);
				will(throwException(e));
				
				//oneOf(loggingSupport).notifyCatching(entry, message, e);
			}
		});

		target.onMessage(message);
	}
}
