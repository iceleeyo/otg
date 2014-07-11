/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.infrastructure.channel.bill99;

import com.springtour.otg.application.exception.FakeNotificationException;
import com.springtour.otg.application.exception.UnavailableCurrencyException;
import com.springtour.otg.domain.model.notification.Notification;
import com.springtour.otg.domain.model.notification.NotificationRepository;
import com.springtour.otg.application.exception.CannotLaunchSecurityProcedureException;
import com.springtour.otg.domain.model.notification.Charge;
import com.springtour.otg.domain.model.notification.SynchronizingMethod;
import com.springtour.otg.domain.model.transaction.Transaction;
import com.springtour.otg.domain.shared.Money;
import com.springtour.otg.infrastructure.channel.AbstractNotificationFactory;
import com.springtour.otg.infrastructure.channel.NotificationValidator;
import com.springtour.otg.infrastructure.time.Clock;
import java.math.BigDecimal;
import java.util.Map;
import static com.springtour.otg.application.util.Constants.EMPTY;

/**
 * 
 * @author Future
 */
public class Bill99NotificationFactoryImpl extends AbstractNotificationFactory {

	private NotificationRepository notificationRepository;
	private Clock clock;

	public Bill99NotificationFactoryImpl(String channel) {
		super(channel);
	}

	@Override
	public Notification make(String channelId,
			Map<String, String> originalNotification)
			throws CannotLaunchSecurityProcedureException,
			UnavailableCurrencyException, FakeNotificationException {
		final BigDecimal amount = new BigDecimal(
				originalNotification.get("OrdAmt"));
		final String extTxnNo = originalNotification.get("TrxId");
		final String message = originalNotification.get("RespCode");
		final String transactionNo = originalNotification.get("OrdId");
		final String interactiveStatus = originalNotification
				.get("InteractiveStatus");
		final String sign = originalNotification.get("ChkValue");

		Transaction transaction = super.findTransaction(transactionNo);

		if (Bill99Constants.INTERACTIVE_STATUS_NOTIFY.equals(interactiveStatus)) {
			super.validate(originalNotification);
			return Notification.byAsynchronous(
					notificationRepository.nextSequence(), clock.now(),
					transaction, extTxnNo, Money.valueOf(amount),
					isCharged(message), message, sign, EMPTY);
		} else {
			return Notification.bySynchronous(
					notificationRepository.nextSequence(), clock.now(),
					transaction, extTxnNo, Money.valueOf(amount),
					isCharged(message), message);
		}

	}

	private Charge isCharged(String message) {
		if (Bill99Constants.CHARGED_SUCCESS.equals(message)) {
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
