package com.springtour.otg.infrastructure.messaging;

import javax.jms.*;

import lombok.*;

import com.springtour.otg.domain.model.transaction.*;
import com.springtour.otg.infrastructure.logging.*;
import com.springtour.otg.infrastructure.mail.*;

public class SpottingInvalidConsumer implements MessageListener {

	@Setter
	private MailManager mailManager;

	@Override
	public void onMessage(Message message) {
		final TransactionNo transactionNo = transactionNoFrom(message);
		mailManager.send("系统定时核对时发现一笔无效交易！",
				"旅游系统交易流水号为：【" + transactionNo.getNumber()
						+ "】，请与第三方支付核对该笔交易，并人工处理该笔订单！");
		LoggerFactory.getLogger().info(
				"invalid txn[" + transactionNo + "], mail sent");

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
