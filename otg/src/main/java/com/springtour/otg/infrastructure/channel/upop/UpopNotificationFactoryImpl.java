package com.springtour.otg.infrastructure.channel.upop;

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

public class UpopNotificationFactoryImpl extends AbstractNotificationFactory {

    @Setter
    private NotificationRepository notificationRepository;

    @Setter
    private Clock clock;

    public UpopNotificationFactoryImpl(String channel) {
        super(channel);
    }

    @Override
    public Notification make(String channelId, Map<String, String> aOriginalNotification)
        throws UnavailableChannelException, UnavailableCurrencyException, CannotLaunchSecurityProcedureException,
        FakeNotificationException {
        String[] resArr = new String[QuickPayConf.notifyVo.length];
        for (int i = 0; i < QuickPayConf.notifyVo.length; i++) {
            resArr[i] = aOriginalNotification.get((QuickPayConf.notifyVo[i]));
        }
        String signature = aOriginalNotification.get(QuickPayConf.signature);
        String signMethod = aOriginalNotification.get(QuickPayConf.signMethod);
        String txnNo = resArr[8];
        BigDecimal amount = new BigDecimal(resArr[6]).divide(new BigDecimal(100));
        String status = resArr[10];
        String cause = resArr[11];// 如果失败，失败原因
        String extTxnNo = resArr[16];

        Transaction transaction = super.findTransaction(txnNo);
        Boolean signatureCheck =
                new QuickPayUtils().checkSign(resArr, signMethod, signature, transaction.getMerchant().getKey());
        if (signatureCheck) {
            return Notification.byAsynchronous(notificationRepository.nextSequence(), clock.now(), transaction,
                    extTxnNo, Money.valueOf(amount), isCharged(status, cause), status, signature, EMPTY);
        } else {
            throw new FakeNotificationException(super.getChannel(), aOriginalNotification);
        }
    }

    private Charge isCharged(String message, String cause) {
        if ("00".equals(message)) {
            return Charge.SUCCESS;
        } else {
            LoggerFactory.getLogger().info("失败原因:" + cause);
            return Charge.FAILURE;
        }
    }

}
