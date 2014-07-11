package com.springtour.otg.infrastructure.channel.cmbc;

import java.io.IOException;

import java.util.ArrayList;
import lombok.Setter;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.springtour.otg.application.util.Configurations;
import com.springtour.otg.domain.model.transaction.Transaction;
import com.springtour.otg.infrastructure.channel.AbstractChannelExternalTransactionQueryObjectAdapter;
import com.springtour.otg.infrastructure.channel.ExternalTransactionQueryResult;
import com.springtour.otg.infrastructure.logging.LoggerFactory;

public class CmbcExternalTransactionQueryObject extends
		AbstractChannelExternalTransactionQueryObjectAdapter {

	protected Logger logger() {
		return LoggerFactory.getLogger();
	}

	@Setter
	private Configurations configurations;
	@Setter
	private Encryption encryption;

	public CmbcExternalTransactionQueryObject(String channel) {
		super(channel);
	}

	@Override
	public ExternalTransactionQueryResult queryBy(Transaction transaction) {
		try {
			String responseText = httpGet(transaction);
			String decrypedDataStr = encryption.decrypt(responseText);
			logger().info(
					"CMBC responsed encrypted data: " + decrypedDataStr
							+ ",  TXN NUMBER ["
							+ transaction.getTransactionNo().getNumber() + "]");
			String[] decryptedArr = decrypedDataStr.split("\\|");
			return ThrowableExternalTransactionQueryResultConverter
					.convertToExternalTransactionQueryResult(decryptedArr);
		} catch (Exception e) {
			throw new IllegalArgumentException(
					"Fail to query from CMBC! transaction number is: "
							+ transaction.getTransactionNo().getNumber(), e);
		}
	}

	private String httpGet(Transaction transaction) throws Exception {
		HttpClient httpclient = new DefaultHttpClient();
		httpclient = WebClientDevWrapper.wrapClient(httpclient);
		HttpGet httpget = new HttpGet(assembleUrl(transaction));
		try {
			HttpResponse response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			String responseText = EntityUtils.toString(entity);
			return responseText;
		} catch (ClientProtocolException e) {
			throw new IllegalArgumentException("Cannot connect to CMBC!", e);
		} catch (IOException e) {
			throw new IllegalArgumentException(
					"Cannot get message from CMBC of single query service!", e);
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
	}

	private String assembleUrl(Transaction transaction) throws Exception {
		StringBuilder url = new StringBuilder(configurations.getCmbcQueryUrl());
		url.append("?");
		url.append("reqStr=");

		ArrayList<String> params = new ArrayList<String>();
		params.add(transaction.getMerchantCode());
		params.add(transaction.getTransactionNo().getNumber());
		AssembleRequestParamsForEncryption assembler = new AssembleRequestParamsForEncryption();
		String strToEncrypt = assembler.assemble(params);
		encryption.encrypt(strToEncrypt);
		url.append(encryption.encrypt(strToEncrypt));
		url.append("&");
		url.append("customerId=");
		url.append(transaction.getMerchantCode());
		return url.toString();
	}

}
