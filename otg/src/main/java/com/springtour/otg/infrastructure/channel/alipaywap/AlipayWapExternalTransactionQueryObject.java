package com.springtour.otg.infrastructure.channel.alipaywap;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;

import lombok.Setter;

import com.springtour.otg.application.util.Configurations;
import com.springtour.otg.application.util.Constants;
import com.springtour.otg.domain.model.notification.Charge;
import com.springtour.otg.domain.model.transaction.Transaction;
import com.springtour.otg.domain.shared.Money;
import com.springtour.otg.infrastructure.channel.AbstractChannelExternalTransactionQueryObjectAdapter;
import com.springtour.otg.infrastructure.channel.ExternalTransactionQueryResult;
import com.springtour.otg.infrastructure.channel.ThrowableExternalTransactionQueryResult;
import com.springtour.otg.infrastructure.logging.LoggerFactory;

public class AlipayWapExternalTransactionQueryObject extends AbstractChannelExternalTransactionQueryObjectAdapter {

	@Setter
    private Configurations configurations;

    protected Logger logger() {
        return LoggerFactory.getLogger();
    }

    public AlipayWapExternalTransactionQueryObject(String channel) {
		super(channel);
	}

    @Override
    public ExternalTransactionQueryResult queryBy(Transaction transaction) {
    	String[] keys = transaction.getMerchant().getKey().split(",");
    	Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "single_trade_query");
        sParaTemp.put("partner", keys[0]);
        sParaTemp.put("_input_charset", AlipayWapConfig.input_charset);
		sParaTemp.put("out_trade_no", transaction.getTransactionNo().getNumber());
		String orderAmount = null;
		String extTxnNo = null;
		String tradeStatus = null;
		try {
			String responseText = AlipayWapSubmit.buildQueryRequest(configurations.getAlipayWapQueryUrl(),keys[1], sParaTemp);
			Document document = DocumentHelper.parseText(responseText);
			Node result = document.getRootElement().selectSingleNode("response").selectSingleNode("trade");
			orderAmount  = result.selectSingleNode("total_fee").getText();
			extTxnNo = result.selectSingleNode("trade_no").getText();
			tradeStatus = result.selectSingleNode("trade_status").getText();
		} catch (Exception e) {
			LoggerFactory.getLogger().error(e.getMessage(), e);
		}
		return new ThrowableExternalTransactionQueryResult(convert(tradeStatus), Money.valueOf(new BigDecimal(
                orderAmount)), extTxnNo);
    }
    
    //tradeStatus TRADE_FINISHED和TRADE_SUCCESS： 成功,其它失败
    private static Charge convert(String tradeStatus) {
    	if (("TRADE_FINISHED".equals(tradeStatus))
				|| ("TRADE_SUCCESS".equals(tradeStatus))) {
			return Charge.SUCCESS;
		} else {
			return null;
		}
    }

}
