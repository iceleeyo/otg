package com.springtour.otg.infrastructure.channel.chinapnr;

import com.springtour.otg.application.exception.UnavailableCurrencyException;
import com.springtour.otg.application.exception.CannotLaunchSecurityProcedureException;
import java.math.BigDecimal;
import java.util.Map;

import com.springtour.otg.domain.model.notification.Notification;
import com.springtour.otg.domain.model.notification.NotificationRepository;
import com.springtour.otg.application.exception.FakeNotificationException;
import com.springtour.otg.domain.model.notification.Charge;
import com.springtour.otg.domain.model.transaction.Transaction;
import com.springtour.otg.domain.shared.Money;
import com.springtour.otg.infrastructure.channel.AbstractNotificationFactory;
import com.springtour.otg.infrastructure.time.Clock;
import static com.springtour.otg.application.util.Constants.EMPTY;

public class ChinapnrNotificationFactoryImpl extends
		AbstractNotificationFactory {

	public ChinapnrNotificationFactoryImpl(String channel) {
		super(channel);
	}

	@Override
	public Notification make(String channelId,
			Map<String, String> originalNotification)
			throws CannotLaunchSecurityProcedureException,
			UnavailableCurrencyException, FakeNotificationException {
		super.validate(originalNotification);
		final BigDecimal amount = new BigDecimal(
				originalNotification.get("OrdAmt"));
		final String extTxnNo = originalNotification.get("TrxId");
		final String message = originalNotification.get("RespCode");
		final String transactionNo = originalNotification.get("OrdId");
		final String sign = originalNotification.get("ChkValue");

		Transaction transaction = super.findTransaction(transactionNo);
		return Notification.byAsynchronous(
				notificationRepository.nextSequence(), clock.now(),
				transaction, extTxnNo, Money.valueOf(amount),
				isCharged(message), message, sign, EMPTY);
	}

	private Charge isCharged(String message) {
		if (ChinapnrConstants.CHARGED_SUCCESS.equals(message)) {
			return Charge.SUCCESS;
		} else {
			return Charge.FAILURE;
		}
	}

	private NotificationRepository notificationRepository;
	private Clock clock;

	public void setNotificationRepository(
			NotificationRepository notificationRepository) {
		this.notificationRepository = notificationRepository;
	}

	public void setClock(Clock clock) {
		this.clock = clock;
	}
}
