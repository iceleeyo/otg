package com.springtour.otg.infrastructure.channel.alipaywap;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import lombok.Setter;

import com.springtour.otg.application.util.Configurations;
import com.springtour.otg.domain.model.transaction.Transaction;
import com.springtour.otg.infrastructure.logging.LoggerFactory;

public class AlipayWapRequestUrlAssembler {

	@Setter
	private Configurations configurations;

	public String assembleUrl(Transaction transaction, String returnUrl,
			String ip) {
		StringBuilder url = new StringBuilder(
				configurations.getAlipayWapRequestUrl());
		url.append("?");
		try {
			String request_token = getRequestToken(transaction, returnUrl);
			// //////////////////////////////////根据授权码token调用交易接口alipay.wap.auth.authAndExecute//////////////////////////////////////
			// 业务详细
			String req_data = "<auth_and_execute_req><request_token>"
					+ request_token + "</request_token></auth_and_execute_req>";
			// 必填
			// 把请求参数打包成数组
			String[] keys = transaction.getMerchant().getKey().split(",");
			Map<String, String> sParaTemp = new HashMap<String, String>();
			sParaTemp.put("service", "alipay.wap.auth.authAndExecute");
			sParaTemp.put("partner", keys[0]);
			sParaTemp.put("_input_charset", AlipayWapConfig.input_charset);
			sParaTemp.put("sec_id", AlipayWapConfig.sign_type);
			sParaTemp.put("format", AlipayWapConfig.format);
			sParaTemp.put("v", AlipayWapConfig.v);
			sParaTemp.put("req_data", req_data);
			url.append(AlipayWapSubmit.buildRequest(sParaTemp,keys[1]));
		} catch (Exception e) {
			LoggerFactory.getLogger().error(e.getMessage(), e);
		}
		return url.toString();
	}

	private String getRequestToken(Transaction transaction, String returnUrl)
			throws Exception, UnsupportedEncodingException {
		String[] keys = transaction.getMerchant().getKey().split(",");
		Map<String, String> sParaTempToken = makeDirectParm(transaction,
				returnUrl);
		String sHtmlTextToken = AlipayWapSubmit.buildRequest(
				configurations.getAlipayWapRequestUrl() + "?", keys[1], "", "",
				sParaTempToken);
		sHtmlTextToken = URLDecoder.decode(sHtmlTextToken,
				AlipayWapConfig.input_charset);
		// 获取token
		String request_token = AlipayWapSubmit.getRequestToken(sHtmlTextToken);
		return request_token;
	}

	private Map<String, String> makeDirectParm(Transaction transaction,
			String returnUrl) {
		String[] keys = transaction.getMerchant().getKey().split(",");
		Map<String, String> sParaTempToken = new HashMap<String, String>();
		sParaTempToken.put("service", "alipay.wap.trade.create.direct");
		sParaTempToken.put("partner", keys[0]);
		sParaTempToken.put("_input_charset", AlipayWapConfig.input_charset);
		sParaTempToken.put("sec_id", AlipayWapConfig.sign_type);
		sParaTempToken.put("format", AlipayWapConfig.format);
		sParaTempToken.put("v", AlipayWapConfig.v);
		sParaTempToken.put("req_id", UtilDate.getOrderNum());
		String req_dataToken = "<direct_trade_create_req><notify_url>"
				+ configurations.getAlipayWapNotifyUrl()
				+ "</notify_url><call_back_url>"
				+ returnUrl
				+ "</call_back_url><seller_account_name>"
				+ transaction.getMerchant().getCode()
				+ "</seller_account_name><out_trade_no>"
				+ transaction.getTransactionNo().getNumber()
				+ "</out_trade_no><subject>春秋旅游产品</subject><total_fee>"
				+ transaction.getAmount().getAmount()
				+ "</total_fee><merchant_url>http://www.springtour.com</merchant_url></direct_trade_create_req>";
		sParaTempToken.put("req_data", req_dataToken);
		return sParaTempToken;
	}

}
