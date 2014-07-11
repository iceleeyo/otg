/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.infrastructure.channel.springcard;

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
public class SpringCardNotificationFactoryImpl extends
		AbstractNotificationFactory {

	private NotificationRepository notificationRepository;
	private Clock clock;

	public SpringCardNotificationFactoryImpl(String channel) {
		super(channel);
	}

	@Override
	public Notification make(String channelId,
			Map<String, String> aOriginalNotification)
			throws UnavailableChannelException, UnavailableCurrencyException,
			CannotLaunchSecurityProcedureException, FakeNotificationException {
		final BigDecimal amount = new BigDecimal(
				aOriginalNotification.get("BillAmount"));
		// final String extTxnNo = aOriginalNotification.get("TrxId");
		final String message = aOriginalNotification.get("SUCC");
		final String transactionNo = aOriginalNotification.get("BillNo");
		final String sign = aOriginalNotification.get("SignMD5");
		Transaction transaction = super.findTransaction(transactionNo);
		aOriginalNotification.put("Key", transaction.getMerchant().getKey());
		super.validate(aOriginalNotification);
		return Notification.byAsynchronous(
				notificationRepository.nextSequence(), clock.now(),
				transaction, transactionNo, Money.valueOf(amount),
				isCharged(message), message, sign, EMPTY);
	}

	private Charge isCharged(String message) {
		if (SpringCardConstants.RESPONSE_SUCC.equals(message)) {
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
