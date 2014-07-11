package com.springtour.otg.infrastructure.channel.ccb;

import java.math.BigDecimal;
import java.util.Map;

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
import static com.springtour.otg.application.util.Constants.EMPTY;

public class CcbNotificationFactoryImpl extends AbstractNotificationFactory {

	private NotificationRepository notificationRepository;
	private Clock clock;

	public CcbNotificationFactoryImpl(String channel) {
		super(channel);
	}

	@Override
	public Notification make(String channelId,
			Map<String, String> originalNotification)
			throws UnavailableChannelException, UnavailableCurrencyException,
			CannotLaunchSecurityProcedureException, FakeNotificationException {
		final String transactionNo = originalNotification.get("ORDERID");
		Transaction transaction = super.findTransaction(transactionNo);
		final BigDecimal amount = new BigDecimal(
				originalNotification.get("PAYMENT"));
		final String payResult = originalNotification.get("SUCCESS");
		final String sign = originalNotification.get("SIGN");
		originalNotification.put("merchantKey", transaction.getMerchant()
				.getKey());
		super.validate(originalNotification);

		return Notification.byAsynchronous(
				notificationRepository.nextSequence(), clock.now(),
				transaction, "", Money.valueOf(amount), isCharged(payResult),
				payResult, sign, EMPTY);
	}

	private Charge isCharged(String message) {
		if (CcbConstants.CHARGED_SUCCESS.equals(message)) {
			return Charge.SUCCESS;
		} else {
			return Charge.FAILURE;
		}
	}

	public void setNotificationRepository(
			NotificationRepository notificationRepository) {
		this.notificationRepository = notificationRepository;
	}

	public void setClock(Clock clock) {
		this.clock = clock;
	}

}
