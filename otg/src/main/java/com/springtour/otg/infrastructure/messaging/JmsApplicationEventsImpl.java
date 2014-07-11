package com.springtour.otg.infrastructure.messaging;

import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.core.support.JmsGatewaySupport;

import com.springtour.otg.application.ApplicationEvents;
import com.springtour.otg.application.exception.*;
import com.springtour.otg.domain.model.notification.Notification;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * JMS based implementation.
 */
public final class JmsApplicationEventsImpl extends JmsGatewaySupport implements
		ApplicationEvents {

	private Destination notificationReceivedQueue;
	private MqSendFailedMessageRepository mqSendFailedMessageRepository;
	private String qName;

	@Override
	public void notificationWasReceived(final Notification notification) {
		try {
			getJmsTemplate().send(notificationReceivedQueue,
					new MessageCreator() {

						@Override
						public Message createMessage(Session session)
								throws JMSException {
							return session.createTextMessage(notification
									.getSequence());
						}
					});
		} catch (Exception e) {
			// 记录发送失败的消息
			FailedMessage failedMessage = new FailedMessage();
			failedMessage.setMessage(notification.getSequence());
			failedMessage.setqName(qName);
			mqSendFailedMessageRepository.store(failedMessage);
			throw new CannotNotifyNotificationReceivedException(
					notification.getSequence(), e);
		}

		// System.out.println("sending " +
		// notification.getSequence()+", timestamp=" +
		// Calendar.getInstance().getTime());
	}

	public void setNotificationReceivedQueue(
			Destination notificationReceivedQueue) {
		this.notificationReceivedQueue = notificationReceivedQueue;
	}

	public void setMqSendFailedMessageRepository(
			MqSendFailedMessageRepository mqSendFailedMessageRepository) {
		this.mqSendFailedMessageRepository = mqSendFailedMessageRepository;
	}

	public void setqName(String qName) {
		this.qName = qName;
	}

}
