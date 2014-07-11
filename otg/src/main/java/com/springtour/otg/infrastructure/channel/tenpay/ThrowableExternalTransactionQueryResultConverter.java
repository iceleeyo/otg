package com.springtour.otg.infrastructure.channel.tenpay;

import java.math.BigDecimal;
import java.util.TreeMap;

import com.springtour.otg.domain.model.notification.Charge;
import com.springtour.otg.domain.shared.Money;
import com.springtour.otg.infrastructure.channel.ExternalTransactionQueryResult;
import com.springtour.otg.infrastructure.channel.ThrowableExternalTransactionQueryResult;

public class ThrowableExternalTransactionQueryResultConverter {

	public static ExternalTransactionQueryResult convertToExternalTransactionQueryResult(
			TreeMap<String, String> analyzedResponseTreeMap) {
		String tradeStatus = analyzedResponseTreeMap.get("trade_state");
		return new ThrowableExternalTransactionQueryResult(
				convert(tradeStatus), money(analyzedResponseTreeMap
						.get("total_fee")), analyzedResponseTreeMap
						.get("transaction_id"));

	}

	public static Charge convert(String tradeStatus) {
		if (tradeStatus.equals("0")) {
			return Charge.SUCCESS;
		}
		return null;
	}

	public static Money money(String totalFeeFromTenpay) {
		BigDecimal totalFeeLocalStored = new BigDecimal(totalFeeFromTenpay)
				.divide(TenpayConstants.AMOUNT_CONVERTION_SCALE);
		Money money = Money.valueOf(totalFeeLocalStored);
		return money;
	}
}
