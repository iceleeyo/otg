package com.springtour.otg.infrastructure.channel.boc;

import java.math.BigDecimal;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;

import com.springtour.otg.domain.model.notification.Charge;
import com.springtour.otg.domain.shared.Money;
import com.springtour.otg.infrastructure.channel.ThrowableExternalTransactionQueryResult;
import com.springtour.otg.infrastructure.logging.LoggerFactory;

public class ThrowableExternalTransactionQueryResultConverter {

	public static ThrowableExternalTransactionQueryResult convertToExternalTransactionQueryResult(
			String decryptedData) {
		String tradeStatus = "";
		String orderAmount = "";
        String extTxnNo = "";
		try {
			Document document = DocumentHelper.parseText(decryptedData);
			Node orderTran = document.getRootElement().selectSingleNode("body").selectSingleNode("orderTrans");
			tradeStatus = orderTran.selectSingleNode("orderStatus").getText();
			orderAmount = orderTran.selectSingleNode("payAmount").getText();
	        extTxnNo = orderTran.selectSingleNode("orderSeq").getText();
		} catch (DocumentException e) {
			LoggerFactory.getLogger().error(e.getMessage(),e);
		}
		
		ThrowableExternalTransactionQueryResult result = new ThrowableExternalTransactionQueryResult(
				convert(tradeStatus), Money.valueOf(new BigDecimal(
						orderAmount)), extTxnNo);
		return result;

	}

	/**
	 * 1：成功； 5：失败。
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
