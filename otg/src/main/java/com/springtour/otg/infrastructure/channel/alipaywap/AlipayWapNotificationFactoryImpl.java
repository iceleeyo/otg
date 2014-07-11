package com.springtour.otg.infrastructure.channel.alipaywap;

import static com.springtour.otg.application.util.Constants.EMPTY;

import java.math.BigDecimal;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

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

public class AlipayWapNotificationFactoryImpl extends
		AbstractNotificationFactory {

	@Setter
	private NotificationRepository notificationRepository;

	@Setter
	private Clock clock;

	public AlipayWapNotificationFactoryImpl(String channel) {
		super(channel);
	}

	@Override
	public Notification make(String channelId,
			Map<String, String> aOriginalNotification)
			throws UnavailableChannelException, UnavailableCurrencyException,
			CannotLaunchSecurityProcedureException, FakeNotificationException {
		Document notifyData = null;
		String sign = "";
		try {
			// XML解析notify_data数据
			if("0001".equals(AlipayWapConfig.sign_type)){
				aOriginalNotification = AlipayWapCore.decrypt(aOriginalNotification);
			}
			sign = aOriginalNotification.get("sign");
			notifyData = DocumentHelper.parseText(aOriginalNotification
					.get("notify_data"));
		} catch (DocumentException e) {
			LoggerFactory.getLogger().error(e.getMessage(), e);
			throw new CannotLaunchSecurityProcedureException(e.getMessage(),e);
		}

		// 商户订单号
		final String transactionNo = notifyData.selectSingleNode(
				"//notify/out_trade_no").getText();

		// 支付宝交易号
		final String extTxnNo = notifyData
				.selectSingleNode("//notify/trade_no").getText();

		// 交易金额
		final BigDecimal amount = new BigDecimal(notifyData.selectSingleNode(
				"//notify/total_fee").getText());

		// 交易状态
		final String tradeStatus = notifyData.selectSingleNode(
				"//notify/trade_status").getText();
		Transaction transaction = super.findTransaction(transactionNo);

		String[] keys = transaction.getMerchant().getKey().split(",");
		aOriginalNotification.put("merchantCode", keys[0]);
		aOriginalNotification.put("merchantKey", keys[1]);
		super.validate(aOriginalNotification);
		return Notification.byAsynchronous(
				notificationRepository.nextSequence(), clock.now(),
				transaction, extTxnNo, Money.valueOf(amount),
				isCharged(tradeStatus), tradeStatus, sign, EMPTY);

	}

	private Charge isCharged(String message) {
		if (("TRADE_FINISHED".equals(message))
				|| ("TRADE_SUCCESS".equals(message))) {
			return Charge.SUCCESS;
		} else {
			return null;
		}
	}

}
