package com.springtour.otg.infrastructure.messaging;

import javax.jms.TextMessage;

import org.jmock.Expectations;
import org.junit.Before;
import org.junit.Test;

import com.springtour.otg.domain.model.transaction.TransactionNo;
import com.springtour.otg.infrastructure.mail.MailManager;
import com.springtour.test.AbstractJMockUnitTests;

public class SpottingInvalidConsumerUnitTests extends AbstractJMockUnitTests {
	private SpottingInvalidConsumer target = new SpottingInvalidConsumer();
	private TextMessage message = context.mock(TextMessage.class);
	private String txnNo = "123";
	private TransactionNo transactionNo = new TransactionNo(txnNo);
	private MailManager mailManager = context.mock(MailManager.class);

	@Before
	public void injects() {
		target.setMailManager(mailManager);
	}

	@Test
	public void sendsMessageWithTransactionNoWhenNotifyToCheck()
			throws Throwable {

		context.checking(new Expectations() {
			{
				final String title = "系统定时核对时发现一笔无效交易！";
				final String content = "旅游系统交易流水号为：【"
						+ transactionNo.getNumber()
						+ "】，请与第三方支付核对该笔交易，并人工处理该笔订单！";
				allowing(message).getText();
				will(returnValue(txnNo));

				oneOf(mailManager).send(title, content);
			}
		});

		target.onMessage(message);
	}

}
