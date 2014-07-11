package com.springtour.otg.infrastructure.messaging;

import javax.jms.*;

import lombok.*;

import com.springtour.otg.application.*;
import com.springtour.otg.domain.model.transaction.*;
import com.springtour.otg.infrastructure.logging.*;

public class CheckingConsumer implements MessageListener {
	@Setter
	private CheckTransactionService checkTransactionService;

	@Override
	public void onMessage(Message message) {
		final TransactionNo transactionNo = transactionNoFrom(message);
		check(transactionNo);
	}

	private void check(final TransactionNo transactionNo) {
		try {
			checkTransactionService.check(transactionNo);
		} catch (Exception e) {
			LoggerFactory.getLogger().error(e.getMessage(), e);
		}
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
