/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.infrastructure.channel.tenpay;

import com.springtour.otg.application.exception.CannotLaunchSecurityProcedureException;
import com.springtour.otg.application.exception.FakeNotificationException;
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
 * @author 006874
 */
public class TenpayNotificationFactoryImpl extends AbstractNotificationFactory {

	public TenpayNotificationFactoryImpl(String channel) {
		super(channel);
	}

	@Override
	public Notification make(String channelId,
			Map<String, String> originalNotification)
			throws CannotLaunchSecurityProcedureException,
			UnavailableCurrencyException, FakeNotificationException {

		final BigDecimal amount = fromTenPayAmount(originalNotification
				.get("total_fee"));
		final String extTxnNo = originalNotification.get("transaction_id");

		final String transactionNo = originalNotification.get("out_trade_no");
		final String sign = originalNotification.get("sign");

		final String tenpayTradeState = originalNotification.get("trade_state");
		final String tenpayTradeMode = originalNotification.get("trade_mode");

		Transaction transaction = super.findTransaction(transactionNo);
		originalNotification.put("merchantKey", transaction.getMerchant()
				.getKey());
		super.validate(originalNotification);
		final String msg = "TradeState=" + tenpayTradeState + ", TradeMode="
				+ tenpayTradeMode;
		return Notification.byAsynchronous(
				notificationRepository.nextSequence(), clock.now(),
				transaction, extTxnNo, Money.valueOf(amount),
				isCharged(tenpayTradeMode, tenpayTradeState), msg, sign, EMPTY);
	}

	private BigDecimal fromTenPayAmount(String amount) {
		return new BigDecimal(amount)
				.divide(TenpayConstants.AMOUNT_CONVERTION_SCALE);
	}

	private Charge isCharged(String tenpayTradeMode, String tenpayTradeState) {
		if (TenpayConstants.CHARGED_SUCCESS.equals(tenpayTradeState)
				&& TenpayConstants.TRADE_MODE.equals(tenpayTradeMode)) {
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
