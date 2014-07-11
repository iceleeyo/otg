package com.springtour.otg.infrastructure.channel.bill99gateway;

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

public class Bill99GatewayNotificationFactoryImpl extends
		AbstractNotificationFactory {

	private NotificationRepository notificationRepository;
	private Clock clock;

	public Bill99GatewayNotificationFactoryImpl(String channel) {
		super(channel);
	}

	@Override
	public Notification make(String channelId,
			Map<String, String> aOriginalNotification)
			throws UnavailableChannelException, UnavailableCurrencyException,
			CannotLaunchSecurityProcedureException, FakeNotificationException {
		final BigDecimal amount = fromBill99GatewayAmount(aOriginalNotification
				.get("payAmount"));
		final String extTxnNo = aOriginalNotification.get("dealId");
		final String message = aOriginalNotification.get("payResult");
		final String transactionNo = aOriginalNotification.get("orderId");
		final String sign = aOriginalNotification.get("signMsg");
		Transaction transaction = super.findTransaction(transactionNo);
		aOriginalNotification.put("merchantKey", transaction.getMerchant()
				.getKey());
		super.validate(aOriginalNotification);

		return Notification.byAsynchronous(
				notificationRepository.nextSequence(), clock.now(),
				transaction, extTxnNo, Money.valueOf(amount),
				isCharged(message), message, sign, EMPTY);
	}

	private BigDecimal fromBill99GatewayAmount(String amount) {
		return new BigDecimal(amount)
				.divide(Bill99GatewayConstants.AMOUNT_CONVERTION_SCALE);
	}

	private Charge isCharged(String message) {
		if (Bill99GatewayConstants.CHARGED_SUCCESS.equals(message)) {
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
