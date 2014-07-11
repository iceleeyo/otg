/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.infrastructure.channel.alipay;

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
import com.springtour.otg.infrastructure.time.Clock;
import java.math.BigDecimal;
import java.util.Map;
import static com.springtour.otg.application.util.Constants.EMPTY;

/**
 * 
 * @author Future
 */
public class AlipayNotificationFactoryImpl extends AbstractNotificationFactory {

	private NotificationRepository notificationRepository;
	private Clock clock;

	public AlipayNotificationFactoryImpl(String channel) {
		super(channel);
	}

	@Override
	public Notification make(String channelId,
			Map<String, String> aOriginalNotification)
			throws UnavailableChannelException, UnavailableCurrencyException,
			CannotLaunchSecurityProcedureException, FakeNotificationException {
		final BigDecimal amount = new BigDecimal(
				aOriginalNotification.get("total_fee"));
		final String extTxnNo = aOriginalNotification.get("trade_no");
		final String message = aOriginalNotification.get("trade_status");
		final String transactionNo = aOriginalNotification.get("out_trade_no");
		final String sign = aOriginalNotification.get("sign");

		Transaction transaction = super.findTransaction(transactionNo);
		aOriginalNotification.put("merchantKey", transaction.getMerchant()
				.getKey());
		super.validate(aOriginalNotification);

		return Notification.byAsynchronous(
				notificationRepository.nextSequence(), clock.now(),
				transaction, extTxnNo, Money.valueOf(amount),
				isCharged(message), message, sign, EMPTY);
	}

	private Charge isCharged(String message) {
		if (AlipaySynchronizingStatus.chargedSuccess.getValue().equals(message)
				|| AlipaySynchronizingStatus.concluded.getValue().equals(
						message)) {
			return Charge.SUCCESS;
		} else {
			return Charge.FAILURE;
		}
	}

	public void setClock(Clock clock) {
		this.clock = clock;
	}

	public void setNotificationRepository(
			NotificationRepository notificationRepository) {
		this.notificationRepository = notificationRepository;
	}
}
