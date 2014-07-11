package com.springtour.otg.infrastructure.channel.cmbc;

import java.math.BigDecimal;
import com.springtour.otg.domain.model.notification.Charge;
import com.springtour.otg.domain.shared.Money;
import com.springtour.otg.infrastructure.channel.ThrowableExternalTransactionQueryResult;

public class ThrowableExternalTransactionQueryResultConverter {

	public static ThrowableExternalTransactionQueryResult convertToExternalTransactionQueryResult(
			String[] decryptedData) {
		String tradeStatus = decryptedData[2];
		ThrowableExternalTransactionQueryResult result = new ThrowableExternalTransactionQueryResult(
				convert(tradeStatus), Money.valueOf(new BigDecimal(
						decryptedData[1])), decryptedData[0]);
		return result;

	}

	/**
	 * 0：失败； 1：成功； 6：订单状态异常！请联系银行。 7：银行后台查询失败！ 8：订单处理中。 9：未找到订单。
	 * 其中订单状态为7和8的需要重复查询以得到准确结果。
	 * 
	 * @param tradeStatus
	 * @return
	 */
	private static Charge convert(String tradeStatus) {
		if ("1".equals(tradeStatus)) {
			return Charge.SUCCESS;
		}
		return null;
	}
}
