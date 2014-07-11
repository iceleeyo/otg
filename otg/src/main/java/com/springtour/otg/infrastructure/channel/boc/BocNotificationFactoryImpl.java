/*
 * Copyright(c) 2012-2014 SpringTour.Ltd. All Rights Reserved.
 * 
 */
package com.springtour.otg.infrastructure.channel.boc;

import static com.springtour.otg.application.util.Constants.EMPTY;

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
import com.springtour.otg.infrastructure.logging.LoggerFactory;
import com.springtour.otg.infrastructure.time.Clock;

/**
 * 此处为类说明
 * 
 * @author 010586
 * @date 2014-4-8
 */
public class BocNotificationFactoryImpl extends AbstractNotificationFactory {

	@Setter
	private NotificationRepository notificationRepository;

	@Setter
	private Clock clock;

	/**
	 * 此处为构造器说明
	 * 
	 * @author 010586
	 * @param channel
	 */
	public BocNotificationFactoryImpl(String channel) {
		super(channel);
	}

	@Override
	public Notification make(String channelId,
			Map<String, String> originalNotification)
			throws UnavailableChannelException, UnavailableCurrencyException,
			CannotLaunchSecurityProcedureException, FakeNotificationException {
		super.validate(originalNotification);
		String txnNo = originalNotification.get("orderNo");
		String amount = originalNotification.get("payAmount");
		String status = originalNotification.get("orderStatus");
		String extTxnNo = originalNotification.get("orderSeq");
		String signature = originalNotification.get("signData");
		Transaction transaction = super.findTransaction(txnNo);
		return Notification.byAsynchronous(
				notificationRepository.nextSequence(), clock.now(),
				transaction, extTxnNo, Money.valueOf(new BigDecimal(amount)),
				isCharged(status), status, signature, EMPTY);
	}

	// status 0：未处理,1：支付,2：撤销,3：退货,4：未明,5：失败

	private Charge isCharged(String status) {
		if ("1".equals(status)) {
			return Charge.SUCCESS;
		} else {
			LoggerFactory.getLogger().info(
					"交易失败, status=" + status
							+ " ,status意义 0：未处理,1：支付,2：撤销,3：退货,4：未明,5：失败");
			return Charge.FAILURE;
		}
	}

}
