package com.springtour.otg.infrastructure.messaging;


import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.springframework.jms.core.support.JmsGatewaySupport;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;



public class ErrorConsumer extends JmsGatewaySupport implements MessageListener {
	private MailSender mailSender;

	@Override
	public void onMessage(final Message message) {
		//mailSender.send(aMailMessageToAdmin(message));
	}

	private SimpleMailMessage aMailMessageToAdmin(Message message) throws JMSException {
		final ObjectMessage objectMessage = (ObjectMessage) message;
		final Exception exception =  (Exception) objectMessage.getObject();
		
		SimpleMailMessage aMailMessageToAdmin = new SimpleMailMessage();
		//aMailMessageToAdmin.setS
		return aMailMessageToAdmin;
	}
}
