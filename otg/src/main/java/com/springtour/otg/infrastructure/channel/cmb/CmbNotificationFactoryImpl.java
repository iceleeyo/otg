package com.springtour.otg.infrastructure.channel.cmb;

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

public class CmbNotificationFactoryImpl extends AbstractNotificationFactory {

    @Setter
    private NotificationRepository notificationRepository;

    @Setter
    private Clock clock;

    public CmbNotificationFactoryImpl(String channel) {
        super(channel);
    }

    @Override
    public Notification make(String channelId, Map<String, String> originalNotification)
        throws UnavailableChannelException, UnavailableCurrencyException, CannotLaunchSecurityProcedureException,
        FakeNotificationException {
        super.validate(originalNotification);
        String txnNo = originalNotification.get("BillNo");
        BigDecimal amount = new BigDecimal(originalNotification.get("Amount"));
        String status = originalNotification.get("Succeed");
        String msg = originalNotification.get("Msg");// 信息的前38个字符格式为：4位分行号＋6位商户号＋8位银行接受交易的日期＋20位银行流水号；
        String extTxnNo = msg.substring(17, 37);
        String signature = originalNotification.get("Signature");
        Transaction transaction = super.findTransaction(txnNo);
        return Notification.byAsynchronous(notificationRepository.nextSequence(), clock.now(), transaction, extTxnNo,
                Money.valueOf(amount), isCharged(status), status, signature, EMPTY);
    }

    private Charge isCharged(String message) {
        if ("Y".equals(message)) {
            return Charge.SUCCESS;
        } else {
            LoggerFactory.getLogger().info("交易失败");
            return Charge.FAILURE;
        }
    }
}
