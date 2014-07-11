/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.application.impl;

import com.springtour.otg.application.ResendMessageService;
import com.springtour.otg.infrastructure.logging.LoggerFactory;
import com.springtour.otg.infrastructure.messaging.FailedMessage;
import com.springtour.otg.infrastructure.messaging.MqSendFailedMessageRepository;
import java.util.List;
import java.util.Map;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.core.support.JmsGatewaySupport;

/**
 *
 * @author Future
 */
public class ResendMessageServiceImpl extends JmsGatewaySupport implements ResendMessageService {

    private MqSendFailedMessageRepository mqSendFailedMessageRepository;
    private Map<String, Destination> messageQueuen;

    @Override
    public void resend() {
        List<FailedMessage> list = mqSendFailedMessageRepository.loadAll();
        for (int i = 0; i < list.size(); i++) {
            FailedMessage failedMessage = list.get(i);
            try {
                send(failedMessage.getqName(), failedMessage.getMessage());
                mqSendFailedMessageRepository.delete(failedMessage.getId());
            } catch (Exception e) {
                LoggerFactory.getLogger().error(e.getMessage(), e);
            }
        }
    }

    private void send(String qName, final String message) {
        Destination destination = messageQueuen.get(qName);
        if (destination == null ) {
            throw new IllegalArgumentException("Queuen name Error!qName = " + qName);
        }
        getJmsTemplate().send(destination, new MessageCreator() {

            @Override
            public Message createMessage(Session sn) throws JMSException {
                return sn.createTextMessage(message);
            }
        });
    }

    public void setMessageQueuen(Map<String, Destination> messageQueuen) {
        this.messageQueuen = messageQueuen;
    }

    public void setMqSendFailedMessageRepository(MqSendFailedMessageRepository mqSendFailedMessageRepository) {
        this.mqSendFailedMessageRepository = mqSendFailedMessageRepository;
    }
    
}
