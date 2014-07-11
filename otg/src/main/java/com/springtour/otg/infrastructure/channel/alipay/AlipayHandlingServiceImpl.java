package com.springtour.otg.infrastructure.channel.alipay;

import com.springtour.otg.application.util.Configurations;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import com.springtour.otg.domain.model.channel.Gateways;
import com.springtour.otg.domain.model.transaction.Transaction;

public class AlipayHandlingServiceImpl implements AlipayHandlingService {
	private Configurations configurations;

	public void setConfigurations(Configurations configurations) {
		this.configurations = configurations;
	}

	@Override
	public String confirmationFlag() {
		return AlipayConstants.CONFIRMATION_FLAG;
	}

	@Override
	public String payConvert(Transaction transaction, String key,
			String notifyUrl, String returnUrl)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		StringBuilder url = new StringBuilder(configurations
				.getAlipayRequestUrl());
		// 对原对象进行签名
		Map<String, String> param = makeParameters(transaction, key, notifyUrl,
				returnUrl);
		// 拼装URL
		url.append("?");
		Iterator<String> it = param.keySet().iterator();
		String mapKey;
		while (it.hasNext()) {
			mapKey = it.next();
			if (!mapKey.equals("sign") && !mapKey.equals("key")) {
				url.append(mapKey);
				url.append("=");
				url.append(URLEncoder.encode(param.get(mapKey), "GBK"));
				url.append("&");
			}
		}
		url.append("sign=");
		url.append(param.get("sign"));
		return url.toString();
	}

	/**
	 * 解析参数
	 * 
	 * @param transaction
	 * @param key
	 * @param returnUrl
	 * @param notifyUrl
	 * @param ip
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	private Map<String, String> makeParameters(Transaction transaction,
			String key, String notifyUrl, String returnUrl)
			throws NoSuchAlgorithmException {
		Map<String, String> param = new TreeMap<String, String>();
		String[] keys = key.split(",");
		param.put("service", "create_direct_pay_by_user");
		param.put("_input_charset", "gbk");
		param.put("sign_type", "MD5");
		param.put("return_url", returnUrl);
		param.put("notify_url", configurations.globalApplicationUrl()
				+ notifyUrl);
		param.put("partner", keys[0]);
		param.put("out_trade_no", transaction.getTransactionNo().getNumber());
		param.put("subject", "春秋旅游产品");
		param.put("payment_type", "1");
		param.put("total_fee", transaction.getAmount().getAmount().toString());
		param.put("key", keys[1]);
		param.put("seller_email", transaction.getMerchantCode());
		
		//支持大额支付
		//param.put("paymethod", "CREDITCARD");
		param.put("credit_card_pay", "Y");
		//param.put("credit_card_default_display", "Y");

		// 网关支付
		if (isGatewaySelected(transaction)&&!isTDCSelected(transaction)) {
			param.put("defaultbank", transaction.gatewayChannelDialect());
			param.put("paymethod", "bankPay");
		}
		
		//扫描支付
		if (isTDCSelected(transaction)) {
			param.put("qr_pay_mode", "2");
		}

		// 计算签名
		param.put("sign", AlipayMD5SignForParameters.sign(param));
		return param;
	}

	private boolean isGatewaySelected(Transaction transaction){
		return !transaction.getGateway().equals(Gateways.ALIPAY.code());
	}
	
	private boolean isTDCSelected(Transaction transaction){
		return transaction.getGateway().equals(Gateways.TDC.code());
	}
	
}
