package com.springtour.otg.infrastructure.messaging;

import javax.jms.*;

import lombok.*;

import org.springframework.jms.core.support.*;

import com.springtour.otg.application.*;
import com.springtour.otg.domain.model.transaction.*;

public class JmsTransactionCheckingEventsImpl extends JmsGatewaySupport
		implements TransactionCheckingEvents {

	@Setter
	private Destination checkingQueue;
	@Setter
	private Destination deadSpottedQueue;
	@Setter
	private Destination invalidSpottingQueue;

	@Override
	public void notifyToCheck(final Transaction transaction) {
		send(transaction.getTransactionNo(), to(checkingQueue));
	}

	@Override
	public void notifySpottingDead(final Transaction transaction) {
		send(transaction.getTransactionNo(), to(deadSpottedQueue));
	}
	
	@Override
	public void notifySpottingInvalid(final Transaction transaction) {
		send(transaction.getTransactionNo(), to(invalidSpottingQueue));
	}

	private Destination to(Destination destination) {
		return destination;
	}

	private void send(final TransactionNo transactionNo,
			final Destination destination) {
		getJmsTemplate().send(destination,
				new TextMessageCreatorUsingTransactionNo(transactionNo));
	}

}
