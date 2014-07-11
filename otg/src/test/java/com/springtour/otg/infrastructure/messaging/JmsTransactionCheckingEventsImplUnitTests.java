package com.springtour.otg.infrastructure.messaging;

import static org.junit.Assert.*;

import javax.jms.*;

import lombok.*;

import org.junit.*;
import org.springframework.jms.core.*;

import com.springtour.otg.domain.model.transaction.*;
import com.springtour.test.*;

public class JmsTransactionCheckingEventsImplUnitTests extends
		AbstractJMockUnitTests {

	private JmsTransactionCheckingEventsImpl target = new JmsTransactionCheckingEventsImpl();
	private Transaction transaction = new TransactionFixture().build();
	private JmsTemplateStub jmsTemplate = new JmsTemplateStub();
	private Destination checkingQueue = context.mock(Destination.class,
			"checkingQueue");
	private Destination deadSpottedQueue = context.mock(Destination.class,
			"deadSpottedQueue");
	private Destination invalidSpottedQueue = context.mock(Destination.class,
			"invalidSpottedQueue");

	@Before
	public void injects() {
		target.setJmsTemplate(jmsTemplate);
		target.setCheckingQueue(checkingQueue);
		target.setDeadSpottedQueue(deadSpottedQueue);
		target.setInvalidSpottingQueue(invalidSpottedQueue);
	}

	@Test
	public void sendsMessageWithTransactionNoWhenNotifyToCheck()
			throws Throwable {

		target.notifyToCheck(transaction);

		assertEquals(checkingQueue, jmsTemplate.getDestination());
		assertEquals(transaction.getTransactionNo().getNumber(),
				jmsTemplate.getMessage());
	}

	@Test
	public void sendsMessageWithTransactionNoWhenNotifySpottingDead()
			throws Throwable {

		target.notifySpottingDead(transaction);

		assertEquals(deadSpottedQueue, jmsTemplate.getDestination());
		assertEquals(transaction.getTransactionNo().getNumber(),
				jmsTemplate.getMessage());
	}
	
	@Test
	public void sendsMessageWithTransactionNoWhenNotifySpottingInvalid()
			throws Throwable {

		target.notifySpottingInvalid(transaction);

		assertEquals(invalidSpottedQueue, jmsTemplate.getDestination());
		assertEquals(transaction.getTransactionNo().getNumber(),
				jmsTemplate.getMessage());
	}

	public class JmsTemplateStub extends JmsTemplate {
		@Getter
		private Object message;
		@Getter
		private Destination destination;

		@Override
		public void send(Destination destination, MessageCreator messageCreator) {

			this.destination = destination;
			this.message = ((TextMessageCreatorUsingTransactionNo) messageCreator)
					.getTransactionNo();

		}
	}

}
