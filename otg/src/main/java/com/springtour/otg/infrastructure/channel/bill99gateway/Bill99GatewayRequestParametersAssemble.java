/**
 * 
 */
package com.springtour.otg.infrastructure.channel.bill99gateway;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.springtour.otg.application.util.Configurations;
import com.springtour.otg.domain.model.transaction.Transaction;

/**
 * @author Future
 * 
 */
public class Bill99GatewayRequestParametersAssemble {
	private PkiPair pkiPair;
	private Configurations configurations;

	public String assembleMsg(Transaction transaction, String returnUrl)
			throws UnrecoverableKeyException, InvalidKeyException,
			KeyStoreException, NoSuchAlgorithmException, CertificateException,
			SignatureException, IOException {
		Map<String, String> map = makeParameters(transaction, returnUrl);
		String key = this.encryptParameters(transaction.getMerchantCode(),
				transaction.getMerchant().getKey(), map);
		map.put("signMsg", key);
		return assembleUrl(map, configurations.getBill99GatewayRequestUrl());
	}

	private String encryptParameters(String merchantCode, String merchantKey,
			Map<String, String> map) throws UnrecoverableKeyException,
			InvalidKeyException, KeyStoreException, NoSuchAlgorithmException,
			CertificateException, SignatureException, IOException {
		StringBuilder sbBuilder = new StringBuilder();
		for (Iterator<String> iterator = map.keySet().iterator(); iterator
				.hasNext();) {
			String key = (String) iterator.next();
			String value = map.get(key);
			if (StringUtils.isNotEmpty(value)) {
				if (sbBuilder.length() == 0) {
					sbBuilder.append(key).append("=").append(value);
				} else {
					sbBuilder.append("&").append(key).append("=").append(value);
				}
			}
		}
		return pkiPair.signMsg(merchantCode, merchantKey, sbBuilder.toString());
	}

	private String assembleUrl(Map<String, String> map, String requestUrl) {
		StringBuilder sbBuilder = new StringBuilder();
		sbBuilder.append(requestUrl).append("?");
		for (Iterator<String> iterator = map.keySet().iterator(); iterator
				.hasNext();) {
			String key = (String) iterator.next();
			String value = map.get(key);
			if (StringUtils.isNotEmpty(value)) {
				sbBuilder.append(key).append("=").append(value).append("&");
			}
		}
		return this.removeLastAndToken(sbBuilder.toString());
	}

	private Map<String, String> makeParameters(Transaction transaction,
			String returnUrl) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("inputCharset", Bill99GatewayConstants.INPUT_CHARSET);
		map.put("pageUrl", returnUrl);
		map.put("bgUrl", configurations.bill99GatewayNotifyUrl());
		map.put("version", Bill99GatewayConstants.VERSION);
		map.put("language", Bill99GatewayConstants.LANGUAGE);
		map.put("signType", Bill99GatewayConstants.SIGN_TYPE);
		map.put("signMsg", "");
		map.put("merchantAcctId", transaction.getMerchantCode());
		map.put("orderId", transaction.getTransactionNo().getNumber());
		map.put("orderAmount", convertAmount(transaction.getAmount()
				.getAmount()));
		map.put("orderTime", new SimpleDateFormat("yyyyMMddHHmmss")
				.format(transaction.getWhenRequested()));
		map.put("ext1", returnUrl);
		map.put("payType", Bill99GatewayConstants.PAY_TYPE);
		map.put("bankId", transaction.gatewayChannelDialect());
		return map;
	}

	private String removeLastAndToken(String url) {
		return url.substring(0, url.length() - 1);
	}

	private String convertAmount(BigDecimal amount) {
		return amount.multiply(Bill99GatewayConstants.AMOUNT_CONVERTION_SCALE)
				.toBigInteger().toString();
	}

	public void setPkiPair(PkiPair pkiPair) {
		this.pkiPair = pkiPair;
	}

	public void setConfigurations(Configurations configurations) {
		this.configurations = configurations;
	}

}
