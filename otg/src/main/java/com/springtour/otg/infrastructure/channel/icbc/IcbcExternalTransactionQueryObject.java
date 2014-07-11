package com.springtour.otg.infrastructure.channel.icbc;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.Setter;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import com.springtour.otg.application.util.Configurations;
import com.springtour.otg.domain.model.merchant.Merchant;
import com.springtour.otg.domain.model.transaction.Transaction;
import com.springtour.otg.infrastructure.channel.AbstractChannelExternalTransactionQueryObjectAdapter;
import com.springtour.otg.infrastructure.channel.ExternalTransactionQueryResult;
import com.springtour.otg.infrastructure.logging.LoggerFactory;
import com.springtour.otg.infrastructure.time.DateUtils;

public class IcbcExternalTransactionQueryObject extends
		AbstractChannelExternalTransactionQueryObjectAdapter {

	@Setter
	private Configurations configurations;

	public IcbcExternalTransactionQueryObject(String channel) {
		super(channel);
	}

	protected Logger logger() {
		return LoggerFactory.getLogger();
	}

	@Override
	public ExternalTransactionQueryResult queryBy(Transaction transaction) {
		try {
			String responseText = httpsPost(transaction);
			logger().info(
					"ICBC responsed encrypted data: " + responseText
							+ ",  TXN NUMBER ["
							+ transaction.getTransactionNo().getNumber() + "]");
			return new ThrowableExternalTransactionQueryResultConverter()
					.convertToExternalTransactionQueryResult(responseText);
		} catch (Exception e) {
			throw new IllegalArgumentException(
					"Fail to query from ICBC! transaction number is: "
							+ transaction.getTransactionNo().getNumber(), e);
		}
	}

	private HttpPost postForm(String url, Map<String, String> params) {
		HttpPost httpost = new HttpPost(url);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		Set<String> keySet = params.keySet();
		for (String key : keySet) {
			nvps.add(new BasicNameValuePair(key, params.get(key)));
		}
		try {
			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException("Cannot connect to ICBC!", e);
		}
		return httpost;
	}

	private String httpsPost(Transaction transaction) throws Exception {
		Merchant merchant = transaction.getMerchant();
		Date txnRequestDate = transaction.getWhenRequested();
		String txnDateStr = DateUtils.getDate(txnRequestDate);
		HttpClient httpclient = new DefaultHttpClient();
		httpclient = new WebClientDevWrapper(this.getClass().getResource(
				configurations.getIcbcJksFileName()), "password")
				.wrapClient(httpclient);
		Map<String, String> map = new HashMap<String, String>();
		map.put("APIName", "EAPI");
		map.put("APIVersion", "001.001.002.001");
		map.put("MerReqData",
				"<?xml  version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>"
						+ "<ICBCAPI><in>" + "<orderNum>"
						+ transaction.getTransactionNo().getNumber()
						+ "</orderNum>" + "<tranDate>" + txnDateStr
						+ "</tranDate>" + "<ShopCode>"
						+ merchant.getKey().split(",")[0] + "</ShopCode>"
						+ "<ShopAccount>" + merchant.getCode()
						+ "</ShopAccount>" + "</in></ICBCAPI>");
		HttpPost httpPost = postForm(configurations.getIcbcQueryRequestUrl(),
				map);
		try {
			HttpResponse response = httpclient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			String responseText = EntityUtils.toString(entity);
			String decodedStr = URLDecoder.decode(responseText, "gbk");
			return decodedStr;
		} catch (ClientProtocolException e) {
			throw new IllegalArgumentException("Cannot connect to ICBC!", e);
		} catch (IOException e) {
			throw new IllegalArgumentException(
					"Cannot get message from ICBC of single query service!", e);
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
	}

}
