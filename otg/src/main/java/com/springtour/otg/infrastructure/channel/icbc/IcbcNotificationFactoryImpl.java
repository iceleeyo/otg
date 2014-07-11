/**
 * 
 */
package com.springtour.otg.infrastructure.channel.icbc;

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
import com.springtour.otg.infrastructure.channel.icbc.object.IcbcPayResponse;
import com.springtour.otg.infrastructure.time.Clock;
import static com.springtour.otg.application.util.Constants.EMPTY;

/**
 * @author 005073
 * 
 */
public class IcbcNotificationFactoryImpl extends AbstractNotificationFactory {
	@Setter
	private NotificationRepository notificationRepository;
	@Setter
	private Clock clock;

	public IcbcNotificationFactoryImpl(String channel) {
		super(channel);
	}

	@Override
	public Notification make(String channelId,
			Map<String, String> aOriginalNotification)
			throws UnavailableChannelException, UnavailableCurrencyException,
			CannotLaunchSecurityProcedureException, FakeNotificationException {
		String notifyData = aOriginalNotification.get("notifyData");
		DecodeNotifyData2Xml data2Xml = new DecodeNotifyData2Xml();
		IcbcPayResponse rPayResponse = data2Xml.decodeAndTransform(notifyData);
		final BigDecimal amount = icbcMoneyTransform(rPayResponse
				.getOrderInfo().getSubOrderInfos().get(0).getAmount());
		final String extTxnNo = rPayResponse.getOrderInfo().getSubOrderInfos()
				.get(0).getTranSerialNo();
		final String message = rPayResponse.getBank().getTranStat();
		final String transactionNo = rPayResponse.getOrderInfo()
				.getSubOrderInfos().get(0).getTransactionId();
		final String sign = aOriginalNotification.get("signMsg");

		Transaction transaction = super.findTransaction(transactionNo);
		aOriginalNotification.put("merchantKey", transaction.getMerchant()
				.getKey().split(",")[1]);

		super.validate(aOriginalNotification);

		return Notification.byAsynchronous(
				notificationRepository.nextSequence(), clock.now(),
				transaction, extTxnNo, Money.valueOf(amount),
				isCharged(message), message, sign, EMPTY);
	}

	private Charge isCharged(String message) {
		if (message.equals(IcbcConstants.ICBC_RESPONSE_SUC)) {
			return Charge.SUCCESS;
		} else {
			return Charge.FAILURE;
		}
	}

	private BigDecimal icbcMoneyTransform(String amount) {
		return new BigDecimal(amount).divide(IcbcConstants.ICBC_MONEY);
	}

}
