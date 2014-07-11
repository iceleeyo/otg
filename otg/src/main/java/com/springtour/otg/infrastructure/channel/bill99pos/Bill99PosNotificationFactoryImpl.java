/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.infrastructure.channel.bill99pos;

import static com.springtour.otg.infrastructure.channel.bill99pos.Bill99PosConstants.*;
import com.springtour.otg.application.exception.FakeNotificationException;
import com.springtour.otg.application.exception.UnavailableCurrencyException;
import com.springtour.otg.domain.model.notification.Notification;
import com.springtour.otg.domain.model.notification.NotificationRepository;
import com.springtour.otg.application.exception.CannotLaunchSecurityProcedureException;
import com.springtour.otg.domain.model.notification.Charge;
import com.springtour.otg.domain.model.transaction.Transaction;
import com.springtour.otg.domain.shared.Money;
import com.springtour.otg.infrastructure.channel.AbstractNotificationFactory;
import com.springtour.otg.infrastructure.time.Clock;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Map;

public class Bill99PosNotificationFactoryImpl extends
		AbstractNotificationFactory {

	private NotificationRepository notificationRepository;
	private Clock clock;

	public Bill99PosNotificationFactoryImpl(String channel) {
		super(channel);
	}

	@Override
	public Notification make(String channelId,
			Map<String, String> originalNotification)
			throws CannotLaunchSecurityProcedureException,
			UnavailableCurrencyException, FakeNotificationException {
		final BigDecimal amount = new BigDecimal(
				originalNotification.get("amt"));
		final String message = originalNotification.get("responseCode");
		final String transactionNo = originalNotification
				.get("externalTraceNo");
		final String sign = originalNotification.get("signature");
		final String authCode = originalNotification.get("authCode");
		final String RRN = originalNotification.get("RRN");
		final String termTraceNo = originalNotification.get("termTraceNo");
		final String txnType = originalNotification.get("txnType");
		final String cardId = originalNotification.get("shortPAN") == null ? "" : originalNotification.get("shortPAN");
		final String cardType = getCardType(originalNotification.get("cardType")== null ? "":originalNotification.get("cardType").trim());
		final String cardIssuer = originalNotification.get("issuerIdView") == null ? ""
					: originalNotification.get("issuerIdView").trim();
		String cardInfo=cardIssuer + "," + cardType + "," + cardId;
		final String extTxnNo = authCode + "," + RRN + "," + termTraceNo;
		Transaction transaction = super.findTransaction(transactionNo);
		if (TXN_TYPE_PUR.equals(txnType)) {
			super.validate(originalNotification);
		} else if (TXN_TYPE_VTX.equals(txnType)) {
			// 撤销交易通知为pos机数据模拟而来
		} else {
			throw new IllegalArgumentException("Unavailable TXN_TYPE!");
		}

		return Notification.byAsynchronous(
				notificationRepository.nextSequence(), clock.now(),
				transaction, extTxnNo, Money.valueOf(amount),
				isCharged(message), message, sign, cardInfo);
	}
	
	private String getCardType(String cardType){
		if("0000".equals(cardType)){
			return "银行卡";
		} else if("0001".equals(cardType)){
			return "信用卡";
		} else if("0002".equals(cardType)){
			return "借记卡";
		} else if("0003".equals(cardType)){
			return "私有卡";
		} else {
			return "";
		}
	}

	private Charge isCharged(String message) {
		if (Bill99PosConstants.CHARGED_SUCCESS.equals(message)) {
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
