package com.springtour.otg.infrastructure.channel.alipay;

import java.math.BigDecimal;
import java.util.TreeMap;

import com.springtour.otg.domain.model.notification.Charge;
import com.springtour.otg.domain.shared.Money;
import com.springtour.otg.infrastructure.channel.ThrowableExternalTransactionQueryResult;

public class ThrowableExternalTransactionQueryResultConverter {

	// 支付宝交易状态有：trade_success, trade_finished, trade_closed, WAIT_BUYER_PAY
	// TRADE_CLOSED为长时间未付款支付宝认为失败
	// 因支付宝无支付失败的情况，所以这里只有成功以及未付款两种情况
	public static ThrowableExternalTransactionQueryResult convertToExternalTransactionQueryResult(
			TreeMap<String, String> analyzedResponseTreeMap) {
		String tradeStatus = analyzedResponseTreeMap.get("trade_status");
		ThrowableExternalTransactionQueryResult result = new ThrowableExternalTransactionQueryResult(
				convert(tradeStatus), Money.valueOf(new BigDecimal(
						analyzedResponseTreeMap.get("total_fee"))),
				analyzedResponseTreeMap.get("trade_no"));
		return result;

	}

	private static Charge convert(String tradeStatus) {
		if ("TRADE_SUCCESS".equals(tradeStatus)
				|| "TRADE_FINISHED".equals(tradeStatus)) {
			return Charge.SUCCESS;
		}
		return null;
	}
}
