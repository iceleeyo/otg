/*
 * Copyright(c) 2012-2014 SpringTour.Ltd. All Rights Reserved.
 */
package com.springtour.otg.infrastructure.channel.boc;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Setter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.bocnet.common.security.PKCS7Tool;
import com.springtour.otg.application.util.Configurations;
import com.springtour.otg.domain.model.transaction.Transaction;
import com.springtour.otg.infrastructure.channel.AbstractChannelExternalTransactionQueryObjectAdapter;
import com.springtour.otg.infrastructure.channel.ExternalTransactionQueryResult;
import com.springtour.otg.infrastructure.logging.LoggerFactory;

/**
 * 此处为类说明
 * 
 * @author 010586
 * @date 2014-4-8
 */
public class BocExternalTransactionQueryObject extends
		AbstractChannelExternalTransactionQueryObjectAdapter {

	@Setter
	private Configurations configurations;

	protected Logger logger() {
		return LoggerFactory.getLogger();
	}

	/**
	 * 此处为构造器说明
	 * 
	 * @author 010586
	 * @param channel
	 */
	public BocExternalTransactionQueryObject(String channel) {
		super(channel);
	}

	@Override
	public ExternalTransactionQueryResult queryBy(Transaction transaction) {
		String responseText = httpPost(transaction);
		logger().info(
				"Boc responsed encrypted data: " + responseText
						+ ",  TXN NUMBER ["
						+ transaction.getTransactionNo().getNumber() + "]");
		return ThrowableExternalTransactionQueryResultConverter
				.convertToExternalTransactionQueryResult(responseText);
	}

	/**
	 * 组装查询订单信息的post请求
	 * 
	 * @param
	 * @return
	 ******************************************************************************* 
	 * @creator ：010586
	 * @date ：2014-4-8
	 * @memo ：
	 ** 
	 */
	private String httpPost(Transaction transaction) {
		HttpClient httpclient = new DefaultHttpClient();
		try {
			URI uri = new URI(configurations.getBocQueryUrl());
			List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
			assembleParms(transaction, nvps);
			HttpPost httpPost = new HttpPost(uri);
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			HttpResponse response = httpclient.execute(httpPost);
			HttpEntity entity = response.getEntity();

			String responseText = EntityUtils.toString(entity);

			return responseText;
		} catch (ClientProtocolException e) {
			throw new IllegalArgumentException("Cannot connect to Bosh!", e);
		} catch (Exception e) {
			throw new IllegalArgumentException(
					"Cannot get message from Bosh of single query service!", e);
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
	}

	private void assembleParms(Transaction transaction,
			List<BasicNameValuePair> nvps) {
		nvps.add(new BasicNameValuePair("merchantNo", transaction
				.getMerchantCode()));
		nvps.add(new BasicNameValuePair("orderNos", transaction
				.getTransactionNo().getNumber()));
		String signDataStr = transaction.getMerchantCode() + ":"
				+ transaction.getTransactionNo().getNumber();
		try {
			String certFilePath = this.getClass()
					.getResource(configurations.getBocCertFileResourceName())
					.getPath();
			PKCS7Tool tool = PKCS7Tool
					.getSigner(certFilePath,
							transaction.getMerchant().getKey(), transaction
									.getMerchant().getKey());
			String signData = tool.sign(signDataStr.getBytes());
			nvps.add(new BasicNameValuePair("signData", signData));
		} catch (Exception e) {
			LoggerFactory.getLogger().error(e.getMessage(), e);
		}
	}
}
