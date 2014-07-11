package com.springtour.otg.infrastructure.channel.icbc;

import java.math.BigDecimal;

import com.springtour.otg.domain.model.notification.Charge;
import com.springtour.otg.domain.shared.Money;
import com.springtour.otg.infrastructure.channel.ExternalTransactionQueryResult;
import com.springtour.otg.infrastructure.channel.ThrowableExternalTransactionQueryResult;
import com.springtour.otg.infrastructure.channel.icbc.object.IcbcQueryResponse;
import com.thoughtworks.xstream.XStream;

public class ThrowableExternalTransactionQueryResultConverter {

	public ExternalTransactionQueryResult convertToExternalTransactionQueryResult(
			String responseText) {
		IcbcQueryResponse response = xmlToData(responseText);
		ThrowableExternalTransactionQueryResult result = new ThrowableExternalTransactionQueryResult(
				convert(response.getOut().getTranStat()), money(response
						.getOut().getAmount()), response.getOut()
						.getTranSerialNum());
		return result;
	}

	// 0-支付成功，未清算 1-支付成功，已清算 　2-支付失败 3-支付可疑交易
	private Charge convert(String tranStat) {
		if ("1".equals(tranStat) || "0".equals(tranStat)) {
			return Charge.SUCCESS;
		}
		return null;
	}

	private Money money(String totalFeeFromIcbc) {
		BigDecimal totalFeeLocalStored = new BigDecimal(totalFeeFromIcbc)
				.divide(IcbcConstants.ICBC_MONEY);
		Money money = Money.valueOf(totalFeeLocalStored);
		return money;
	}

	protected IcbcQueryResponse xmlToData(String xmlData) {
		XStream xStream = new XStream();
		xStream.autodetectAnnotations(true);
		xStream.alias("ICBCAPI", IcbcQueryResponse.class);
		return (IcbcQueryResponse) xStream.fromXML(xmlData);
	}

}
