/*
 * Copyright(c) 2012-2014 SpringTour.Ltd. All Rights Reserved.
 * 
 */
package com.springtour.otg.infrastructure.channel.bosh;

import static com.springtour.otg.application.util.Constants.EMPTY;

import java.math.BigDecimal;
import java.util.Map;

import lombok.Setter;

import com.springtour.otg.application.exception.CannotLaunchSecurityProcedureException;
import com.springtour.otg.application.exception.FakeNotificationException;
import com.springtour.otg.application.exception.UnavailableChannelException;
import com.springtour.otg.application.exception.UnavailableCurrencyException;
import com.springtour.otg.domain.model.notification.Charge;
import com.springtour.otg.domain.model.notification.Notification;
import com.springtour.otg.domain.model.notification.NotificationRepository;
import com.springtour.otg.domain.model.transaction.Transaction;
import com.springtour.otg.domain.shared.Money;
import com.springtour.otg.infrastructure.channel.AbstractNotificationFactory;
import com.springtour.otg.infrastructure.logging.LoggerFactory;
import com.springtour.otg.infrastructure.time.Clock;

/**
 * 此处为类说明
 * @author 010586
 * @date 2014-4-8
 */
public class BoshNotificationFactoryImpl extends AbstractNotificationFactory {

    @Setter
    private NotificationRepository notificationRepository;

    @Setter
    private Clock clock;
    
    /**
     * 此处为构造器说明
     * @author 010586
     * @param channel
     */
    public BoshNotificationFactoryImpl(String channel) {
        super(channel);
    }

    @Override
    public Notification make(String channelId, Map<String, String> originalNotification)
        throws UnavailableChannelException, UnavailableCurrencyException, CannotLaunchSecurityProcedureException,
        FakeNotificationException {
        super.validate(originalNotification);
        String txnNo = originalNotification.get("merOrderNum");
        BigDecimal amount = new BigDecimal(originalNotification.get("merOrderAmt")).divide(new BigDecimal(100));
        String status = originalNotification.get("tranResult");
        String msg = originalNotification.get("comment");
        String extTxnNo = originalNotification.get("tranSerialNo");
        String signature = originalNotification.get("signData");
        Transaction transaction = super.findTransaction(txnNo);
        return Notification.byAsynchronous(notificationRepository.nextSequence(), clock.now(), transaction, extTxnNo,
                Money.valueOf(amount), isCharged(status, msg), status, signature, EMPTY);
    }

    //status 0： 成功,1： 失败,2： 处理中,3： 可疑
    private Charge isCharged(String status, String message) {
        if ("0".equals(status)) {
            return Charge.SUCCESS;
        } else {
            LoggerFactory.getLogger().info("交易失败, 失败原因：" + message);
            return Charge.FAILURE;
        }
    }
    
}
