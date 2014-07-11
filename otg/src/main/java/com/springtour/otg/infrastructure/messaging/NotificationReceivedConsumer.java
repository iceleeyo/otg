package com.springtour.otg.infrastructure.messaging;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.core.support.JmsGatewaySupport;

import com.springtour.otg.application.ResponseProcedure;
import com.springtour.otg.application.exception.DuplicateResponseException;
import com.springtour.otg.infrastructure.logging.LoggerFactory;

public class NotificationReceivedConsumer extends JmsGatewaySupport implements
        MessageListener {

    private Destination errorQueue;
    private ResponseProcedure responseProcedure;

    @Override
    public void onMessage(final Message message) {
        
        try {
            responseProcedure.handle(getNotificationSeq(message));
        } catch (DuplicateResponseException dne) {//response procedure will throw dne？
            //duplicate notification is normal，just leave it
        } catch (final Exception e) {
            LoggerFactory.getLogger().error(e.getMessage(), e);
            throw new RuntimeException("OTG handles message failed!");
        }
    }

    private String getNotificationSeq(final Message message)
            throws JMSException {
        final TextMessage textMessage = (TextMessage) message;
        //System.out.println("receiving " + textMessage.getText()+", timestamp=" + Calendar.getInstance().getTime());
        return textMessage.getText();
    }

    private void notifyError(final Exception e) {
        logger.error(e.getMessage(), e);
        getJmsTemplate().send(errorQueue, new MessageCreator() {

            public Message createMessage(Session session) throws JMSException {
                return session.createObjectMessage(e);
            }
        });
    }

    public void setErrorQueue(Destination errorQueue) {
        this.errorQueue = errorQueue;
    }

    public void setResponseProcedure(ResponseProcedure responseProcedure) {
        this.responseProcedure = responseProcedure;
    }
}
