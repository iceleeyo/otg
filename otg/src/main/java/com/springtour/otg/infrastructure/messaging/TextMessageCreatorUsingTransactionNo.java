package com.springtour.otg.infrastructure.messaging;

import javax.jms.*;

import org.springframework.jms.core.*;

import com.springtour.otg.domain.model.transaction.*;

public class TextMessageCreatorUsingTransactionNo implements MessageCreator {
	private final TransactionNo transactionNo;

	public TextMessageCreatorUsingTransactionNo(TransactionNo transactionNo) {
		this.transactionNo = transactionNo;
	}

	@Override
	public Message createMessage(Session session) throws JMSException {
		return session.createTextMessage(getTransactionNo());
	}

	/**
	 * 用来单元测试的Api
	 */
	public String getTransactionNo() {
		return transactionNo.getNumber();
	}
}
