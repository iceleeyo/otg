/*
 * Copyright(c) 2012-2014 SpringTour.Ltd. All Rights Reserved.
 * 
 */
package com.springtour.otg.infrastructure.channel.bocomm;

import static com.springtour.otg.application.util.Constants.EMPTY;

import java.math.BigDecimal;
import java.util.Map;

import lombok.Setter;

import com.springtour.otg.application.exception.*;
import com.springtour.otg.domain.model.notification.*;
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
public class BocommNotificationFactoryImpl extends AbstractNotificationFactory {

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
	public BocommNotificationFactoryImpl(String channel) {
		super(channel);
	}

	@Override
	public Notification make(String channelId,
			Map<String, String> originalNotification)
			throws UnavailableChannelException, UnavailableCurrencyException,
			CannotLaunchSecurityProcedureException, FakeNotificationException {
		String notifyMsg = originalNotification.get("notifyMsg");// 获取银行通知结果
		int lastIndex = notifyMsg.lastIndexOf("|");
		String signMsg = notifyMsg.substring(lastIndex + 1, notifyMsg.length());// 获取签名信息
		String srcMsg = notifyMsg.substring(0, lastIndex + 1);
		originalNotification.put("signMsg", signMsg);
		originalNotification.put("srcMsg", srcMsg);
		super.validate(originalNotification);
		String[] data = srcMsg.split("\\|");
		String txnNo = data[1];
		BigDecimal amount = new BigDecimal(data[2]);
		String status = data[9];
		String msg = data[13];
		String extTxnNo = data[8];
		Transaction transaction = super.findTransaction(txnNo);
		return Notification.byAsynchronous(
				notificationRepository.nextSequence(), clock.now(),
				transaction, extTxnNo, Money.valueOf(amount),
				isCharged(status, msg), status, signMsg, EMPTY);
	}

	private Charge isCharged(String status, String message) {
		if ("1".equals(status)) {
			return Charge.SUCCESS;
		} else {
			LoggerFactory.getLogger().info("交易失败, 失败原因：" + message);
			return Charge.FAILURE;
		}
	}

}
