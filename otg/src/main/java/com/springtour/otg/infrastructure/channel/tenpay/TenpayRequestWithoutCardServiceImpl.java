/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.infrastructure.channel.tenpay;

import com.springtour.otg.application.exception.CannotLaunchSecurityProcedureException;
import com.springtour.otg.application.util.Configurations;
import com.springtour.otg.domain.model.transaction.Transaction;
import com.springtour.otg.infrastructure.channel.AbstractRequestWithoutCardAdapter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * 
 * @author 006874
 */
public class TenpayRequestWithoutCardServiceImpl extends
		AbstractRequestWithoutCardAdapter {

	public TenpayRequestWithoutCardServiceImpl(String channelId) {
		super(channelId);
	}

	@Override
	public String request(Transaction transaction, String returnUrl,
			Map customParams) throws CannotLaunchSecurityProcedureException {
		try {

			String ip = (String) customParams.get("surferIp");
			Map<String, String> parameters = makeParameters(transaction,
					transaction.getMerchant().getKey(), returnUrl, ip);
			return assembleUrl(parameters);
		} catch (Exception e) {
			throw new CannotLaunchSecurityProcedureException(e.getMessage(), e);
		}

	}

	private Map<String, String> makeParameters(Transaction transaction,
			String key, String returnUrl, String ip)
			throws NoSuchAlgorithmException {
		if (ip == null || "".equals(ip)) {
			throw new IllegalArgumentException("ip should not be null!");
		}
		Map<String, String> param = new TreeMap<String, String>();
		param.put("body", "春秋旅游产品");
		param.put("return_url", returnUrl);
		param.put("notify_url", configurations.tenpayNotifyUrl());
		param.put("partner", transaction.getMerchantCode());
		param.put("out_trade_no", transaction.getTransactionNo().getNumber());
		param.put("total_fee", convertAmount(transaction.getAmount()
				.getAmount()));
		param.put("fee_type", TenpayConstants.CNY);
		param.put("spbill_create_ip", ip);
		param.put("key", key);
		param.put("sign", TenpayMD5SignForParamters.sign(param));
		return param;
	}

	private String assembleUrl(Map<String, String> parameters)
			throws UnsupportedEncodingException {
		StringBuilder url = new StringBuilder(configurations
				.getTenpayRequestUrl());
		url.append("?");
		Iterator<String> it = parameters.keySet().iterator();
		String mapKey;
		while (it.hasNext()) {
			mapKey = it.next();
			if (!mapKey.equals("sign") && !mapKey.equals("key")) {
				url.append(mapKey);
				url.append("=");
				url.append(URLEncoder.encode(parameters.get(mapKey), "gbk"));
				url.append("&");
			}
		}
		url.append("sign=");
		url.append(parameters.get("sign"));
		return url.toString();
	}

	private String convertAmount(BigDecimal amount) {
		return amount.multiply(TenpayConstants.AMOUNT_CONVERTION_SCALE)
				.toBigInteger().toString();
	}

	private Configurations configurations;

	public void setConfigurations(Configurations configurations) {
		this.configurations = configurations;
	}
}
