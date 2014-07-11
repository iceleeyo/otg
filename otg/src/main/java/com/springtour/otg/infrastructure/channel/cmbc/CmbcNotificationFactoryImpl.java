package com.springtour.otg.infrastructure.channel.cmbc;

import java.math.BigDecimal;
import static com.springtour.otg.application.util.Constants.EMPTY;
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
import com.springtour.otg.infrastructure.time.Clock;

public class CmbcNotificationFactoryImpl extends AbstractNotificationFactory {

	@Setter
	private NotificationRepository notificationRepository;
	@Setter
	private Clock clock;
	@Setter
	private Encryption encryption;

	public CmbcNotificationFactoryImpl(String channel) {
		super(channel);
	}

	@Override
	public Notification make(String channelId,
			Map<String, String> originalNotification)
			throws UnavailableChannelException, UnavailableCurrencyException,
			CannotLaunchSecurityProcedureException, FakeNotificationException {
		String encryptedStr = originalNotification.get("reqStr");
		String[] receivedData = encryption.decrypt(encryptedStr).split("\\|");
		String txnNo = receivedData[0];
		String sign = encryptedStr;
		BigDecimal amount = new BigDecimal(receivedData[2]);
		String status = receivedData[5];
		Transaction transaction = super.findTransaction(txnNo);
		// 民生银行未提供其流水号，故放本地流水号
		return Notification.byAsynchronous(
				notificationRepository.nextSequence(), clock.now(),
				transaction, receivedData[0], Money.valueOf(amount),
				isCharged(status), status, sign, EMPTY);
	}

	private Charge isCharged(String message) {
		if ("0".equals(message)) {
			return Charge.SUCCESS;
		} else {
			return Charge.FAILURE;
		}
	}

}
