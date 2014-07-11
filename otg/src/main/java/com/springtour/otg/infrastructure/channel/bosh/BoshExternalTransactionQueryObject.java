/*
 * Copyright(c) 2012-2014 SpringTour.Ltd. All Rights Reserved.
 */
package com.springtour.otg.infrastructure.channel.bosh;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;

import com.springtour.otg.application.util.Configurations;
import com.springtour.otg.domain.model.transaction.Transaction;
import com.springtour.otg.infrastructure.channel.AbstractChannelExternalTransactionQueryObjectAdapter;
import com.springtour.otg.infrastructure.channel.ExternalTransactionQueryResult;
import com.springtour.otg.infrastructure.channel.NotificationValidator;
import com.springtour.otg.infrastructure.logging.LoggerFactory;

/**
 * 此处为类说明
 * 
 * @author 010586
 * @date 2014-4-8
 */
public class BoshExternalTransactionQueryObject extends
		AbstractChannelExternalTransactionQueryObjectAdapter {

	@Setter
	private Configurations configurations;

	@Setter
	private BoshEncryption encryption;

	@Setter
	private NotificationValidator validator;

	protected Logger logger() {
		return LoggerFactory.getLogger();
	}

	/**
	 * 此处为构造器说明
	 * 
	 * @author 010586
	 * @param channel
	 */
	public BoshExternalTransactionQueryObject(String channel) {
		super(channel);
	}

	@Override
	public ExternalTransactionQueryResult queryBy(Transaction transaction) {
		String responseText = httpPost(transaction);
		logger().info(
				"Bosh responsed encrypted data: " + responseText
						+ ",  TXN NUMBER ["
						+ transaction.getTransactionNo().getNumber() + "]");
		String[] resultData = responseText.split("&");
		Map<String, String> queryNotification = new HashMap<String, String>();
		queryNotification.put("signDataStr", resultData[1]);
		queryNotification.put("signData", resultData[2]);
		if (!validator.validate(queryNotification)) {
			throw new IllegalArgumentException("response validation Failed! ");
		}
		return ThrowableExternalTransactionQueryResultConverter
				.convertToExternalTransactionQueryResult(resultData);
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
		httpclient = WebClientDevWrapper.wrapClient(httpclient);
		try {
			URI uri = new URI(configurations.getBoshQueryUrl());
			List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
			assembleParms(transaction,nvps);
			HttpPost httpPost = new HttpPost(uri);
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			HttpResponse response = httpclient.execute(httpPost);
			HttpEntity entity = response.getEntity();

			String responseText = EntityUtils.toString(entity);
			Document document = DocumentHelper.parseText(responseText);
			Node opResultNode = document.getRootElement()
					.selectSingleNode("opResult");
			String orderListResult = opResultNode.selectSingleNode("orderListResult").getText();
			String signDataStr = opResultNode.selectSingleNode("signDataStr").getText();
			String signData = opResultNode.selectSingleNode("signData").getText();

			return orderListResult + "&" + signDataStr + "&" + signData;
		} catch (ClientProtocolException e) {
			throw new IllegalArgumentException("Cannot connect to Bosh!", e);
		} catch (Exception e) {
			throw new IllegalArgumentException(
					"Cannot get message from Bosh of single query service!", e);
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
	}
	
	private void assembleParms(Transaction transaction,List<BasicNameValuePair> nvps) {
		nvps.add(new BasicNameValuePair("merchantID", transaction.getMerchantCode()));
		nvps.add(new BasicNameValuePair("orderList", transaction.getTransactionNo().getNumber()));
		String orderBeginDate = new SimpleDateFormat("yyyyMMddHHmmss")
				.format(transaction.getWhenRequested());
		nvps.add(new BasicNameValuePair("orderBeginDate", orderBeginDate));
		String orderEndDate = new SimpleDateFormat("yyyyMMddHHmmss")
				.format(new Date());
		nvps.add(new BasicNameValuePair("orderEndDate", orderEndDate));
		// queryParms.put("notifyURL", configurations.getBoshNotifyUrl());
		String signDataStr = transaction.getMerchantCode() + "|"
				+ transaction.getTransactionNo().getNumber() + "|"
				+ orderBeginDate + "|" + orderEndDate;

		encryption.encryptionForList(transaction, signDataStr, nvps);
	}
}
