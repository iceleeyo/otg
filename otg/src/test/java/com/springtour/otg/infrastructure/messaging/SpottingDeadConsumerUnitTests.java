package com.springtour.otg.infrastructure.messaging;

import javax.jms.TextMessage;

import org.jmock.Expectations;
import org.junit.Before;
import org.junit.Test;

import com.springtour.otg.application.CheckTransactionService;
import com.springtour.otg.domain.model.transaction.TransactionNo;
import com.springtour.test.AbstractJMockUnitTests;

public class SpottingDeadConsumerUnitTests extends AbstractJMockUnitTests {
	private SpottingDeadConsumer target = new SpottingDeadConsumer();
	private CheckTransactionService checkTransactionService = context
			.mock(CheckTransactionService.class);
	private TextMessage message = context.mock(TextMessage.class);
	private String txnNo = "123";
	private TransactionNo transactionNo = new TransactionNo(txnNo);
	
	@Before
	public void injects() {
		target.setCheckTransactionService(checkTransactionService);
	}
	
	@Test
	public void sendsMessageWithTransactionNoWhenNotifyToCheck()
			throws Throwable {


		context.checking(new Expectations() {
			{
				allowing(message).getText();
				will(returnValue(txnNo));
				
				oneOf(checkTransactionService).retrieveOrMarkAsDead(transactionNo);
			}
		});

		target.onMessage(message);
	}

}
