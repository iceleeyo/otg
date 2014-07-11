package com.springtour.otg.infrastructure.channel.alipay;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import lombok.Setter;
import com.springtour.otg.application.util.Configurations;
import com.springtour.otg.domain.model.transaction.Transaction;
import com.springtour.otg.infrastructure.channel.*;
import com.springtour.otg.infrastructure.logging.LoggerFactory;

public class AlipayExternalTransactionQueryObject extends
		AbstractChannelExternalTransactionQueryObjectAdapter {

	@Setter
	private Configurations configurations;

	public AlipayExternalTransactionQueryObject(String channel) {
		super(channel);
	}

	protected Logger logger() {
		return LoggerFactory.getLogger();
	}

	@Override
	public ExternalTransactionQueryResult queryBy(Transaction transaction) {
		try {
			String responseText = httpGet(transaction);
			logger().info(
					"ALIPAY responsed encrypted data: " + responseText
							+ ",  TXN NUMBER ["
							+ transaction.getTransactionNo().getNumber() + "]");
			// analyze response to treeMap
			AlipayAnalyseSynResponseForSingleTxnQuery analyzer = new AlipayAnalyseSynResponseForSingleTxnQuery();
			TreeMap<String, String> analyzedResponseTreeMap = analyzer
					.analyzingSynchronousResults(responseText);
			analyzedResponseTreeMap.put("key", transaction.getMerchant()
					.getKey().split(",")[1]);
			// validate signature
			validateSynResponse(analyzedResponseTreeMap);
			return ThrowableExternalTransactionQueryResultConverter
					.convertToExternalTransactionQueryResult(analyzedResponseTreeMap);
		} catch (Exception e) {
			throw new IllegalArgumentException("Fail to query from Alipay!", e);
		}
	}

	private void validateSynResponse(
			TreeMap<String, String> analyzedResponseTreeMap)
			throws NoSuchAlgorithmException {
		String remoteSign = analyzedResponseTreeMap.get("sign");
		analyzedResponseTreeMap.remove("sign");
		String localSign = AlipayMD5SignForParameters
				.sign(analyzedResponseTreeMap);
		if (!localSign.equals(remoteSign)) {
			throw new IllegalArgumentException("response validation Failed! ");
		}
	}

	private String httpGet(Transaction transaction)
			throws NoSuchAlgorithmException {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(assembleUrl(transaction));
		try {
			HttpResponse response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			String responseText = EntityUtils.toString(entity);
			return responseText;
		} catch (ClientProtocolException e) {
			throw new IllegalArgumentException("Cannot connect to Alipay!", e);
		} catch (IOException e) {
			throw new IllegalArgumentException(
					"Cannot get message from Alipay of single query service!",
					e);
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
	}

	private String assembleUrl(Transaction transaction)
			throws NoSuchAlgorithmException {
		StringBuilder url = new StringBuilder(
				configurations.getAlipayRequestUrl());
		// 对原对象进行签名
		Map<String, String> param = makeParameters(transaction);
		// 拼装URL
		url.append("?");
		Iterator<String> it = param.keySet().iterator();
		String mapKey;
		while (it.hasNext()) {
			mapKey = it.next();
			if (!mapKey.equals("sign") && !mapKey.equals("key")) {
				url.append(mapKey);
				url.append("=");
				url.append(param.get(mapKey));
				url.append("&");
			}
		}
		url.append("sign=");
		url.append(param.get("sign"));
		return url.toString();
	}

	private Map<String, String> makeParameters(Transaction transaction)
			throws NoSuchAlgorithmException {
		String Merchantkey = transaction.getMerchant().getKey();
		Map<String, String> param = new TreeMap<String, String>();
		String[] keys = Merchantkey.split(",");
		param.put("service", AlipayConstants.QUERY_SERVICE);
		param.put("_input_charset", AlipayConstants.ALIPAY_ENCODER);
		param.put("sign_type", AlipayConstants.SIGN_TYPE);
		param.put("partner", keys[0]);
		param.put("out_trade_no", transaction.getTransactionNo().getNumber());
		// 计算签名
		param.put("key", keys[1]);
		param.put("sign", AlipayMD5SignForParameters.sign(param));
		return param;
	}

}
