/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.infrastructure.channel;

import com.springtour.otg.application.exception.CannotLaunchSecurityProcedureException;
import com.springtour.otg.application.exception.FakeNotificationException;
import com.springtour.otg.domain.model.notification.NotificationFactory;
import com.springtour.otg.domain.model.transaction.*;
import java.util.Map;

/**
 *
 * @author 006874
 */
public abstract class AbstractNotificationFactory implements NotificationFactory {

    private String channel;
    private NotificationValidator notificationValidator;
    private TransactionRepository transactionRepository;

	public AbstractNotificationFactory(String channel) {
        this.channel = channel;
    }

    public String getChannel() {
        return channel;
    }

    protected Transaction findTransaction(String transactionNo) {
        Transaction transaction = transactionRepository.find(new TransactionNo(
                transactionNo));
        if (transaction == null) {
            throw new IllegalArgumentException("can not find transaction when making notification!");
        }
        return transaction;
    }

    /**
     * 一般情况下，只有异步通知才需要验证
     * @param aOriginalNotification
     * @throws CannotLaunchSecurityProcedureException
     * @throws FakeNotificationException 
     */
    protected void validate(Map<String, String> aOriginalNotification) throws CannotLaunchSecurityProcedureException, FakeNotificationException {
        if (!notificationValidator.validate(aOriginalNotification)) {
            throw new FakeNotificationException(channel, aOriginalNotification);
        }
    }

    public void setNotificationValidator(NotificationValidator notificationValidator) {
        this.notificationValidator = notificationValidator;
    }

    public void setTransactionRepository(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public NotificationValidator getNotificationValidator() {
        return notificationValidator;
    }
    
    public TransactionRepository getTransactionRepository() {
		return transactionRepository;
	}
}
