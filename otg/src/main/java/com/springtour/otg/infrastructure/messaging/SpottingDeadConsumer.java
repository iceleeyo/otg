package com.springtour.otg.infrastructure.messaging;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import lombok.Setter;

import com.springtour.otg.application.CheckTransactionService;
import com.springtour.otg.domain.model.transaction.TransactionNo;
import com.springtour.otg.infrastructure.logging.LoggerFactory;

public class SpottingDeadConsumer implements MessageListener {
	@Setter
	private CheckTransactionService checkTransactionService;

	@Override
	public void onMessage(Message message) {
		final TransactionNo transactionNo = transactionNoFrom(message);
		retrieveOrmarkAsDead(transactionNo);
	}

	private void retrieveOrmarkAsDead(final TransactionNo transactionNo) {
		checkTransactionService.retrieveOrMarkAsDead(transactionNo);
	}

	protected TransactionNo transactionNoFrom(Message message) {
		try {
			TextMessage text = (TextMessage) message;
			return new TransactionNo(text.getText());
		} catch (JMSException e) {
			LoggerFactory.getLogger().error(
					"error extracting content from message=" + message + ","
							+ e.getMessage(), e);
			throw new IllegalArgumentException(
					"cannot get content from message", e);
		}
	}

}
