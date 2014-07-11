package com.springtour.otg.infrastructure.channel.tenpay;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import lombok.Setter;

import com.springtour.otg.application.util.Configurations;
import com.springtour.otg.domain.model.transaction.Transaction;
import com.springtour.otg.infrastructure.channel.AbstractChannelExternalTransactionQueryObjectAdapter;
import com.springtour.otg.infrastructure.channel.ExternalTransactionQueryResult;
import com.springtour.otg.infrastructure.logging.LoggerFactory;

public class TenpayExternalTransactionQueryObject extends
		AbstractChannelExternalTransactionQueryObjectAdapter {

	@Setter
	private Configurations configurations;

	public TenpayExternalTransactionQueryObject(String channel) {
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
					"TENPAY responsed encrypted data: " + responseText
							+ ",  TXN NUMBER ["
							+ transaction.getTransactionNo().getNumber() + "]");
			// analyze response to treeMap
			TenpayAnalyseSynResponseForSingleTxnQuery analyzer = new TenpayAnalyseSynResponseForSingleTxnQuery();
			TreeMap<String, String> analyzedResponseTreeMap = analyzer
					.analyzingSynchronousResults(responseText);
			analyzedResponseTreeMap.put("key", transaction.getMerchant()
					.getKey());
			// validate signature
			validateSynResponse(analyzedResponseTreeMap);
			return ThrowableExternalTransactionQueryResultConverter
					.convertToExternalTransactionQueryResult(analyzedResponseTreeMap);
		} catch (Exception e) {
			throw new IllegalArgumentException("Fail to query from Tenpay!", e);
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
			throw new IllegalArgumentException("Cannot connect to Tenpay!", e);
		} catch (IOException e) {
			throw new IllegalArgumentException(
					"Cannot get message from Tenpay of query service!", e);
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
	}

	private void validateSynResponse(
			TreeMap<String, String> analyzedResponseTreeMap)
			throws NoSuchAlgorithmException {
		String remoteSign = analyzedResponseTreeMap.get("sign");
		analyzedResponseTreeMap.remove("sign");
		String localSign = TenpayMD5SignForParamters
				.sign(analyzedResponseTreeMap);
		if (!localSign.equals(remoteSign)) {
			throw new IllegalArgumentException("response validation Failed! ");
		}
	}

	private String assembleUrl(Transaction transaction)
			throws NoSuchAlgorithmException {
		StringBuilder url = new StringBuilder(
				configurations.getTenpayQueryUrl());
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
		Map<String, String> param = new TreeMap<String, String>();
		param.put("partner", transaction.getMerchant().getCode());
		param.put("out_trade_no", transaction.getTransactionNo().getNumber());
		// 计算签名
		param.put("key", transaction.getMerchant().getKey());
		param.put("sign", TenpayMD5SignForParamters.sign(param));
		return param;
	}
}
