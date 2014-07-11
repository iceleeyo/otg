package com.springtour.otg.infrastructure.channel.ccb;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Map;

import com.springtour.otg.domain.model.transaction.Transaction;

public abstract class AbstractCcbUrlAssembler {

	public String assembleUrl(Transaction transaction, Map customParams)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		StringBuilder url = requstUrlPrefix();
		Map<String, String> parameters = makeParameters(transaction,
				customParams);
		Iterator<String> it = parameters.keySet().iterator();
		String mapKey;
		while (it.hasNext()) {
			mapKey = it.next();
			if (!mapKey.equals("MAC") && !mapKey.equals("PUB")) {
				url.append(mapKey);
				url.append("=");
				url.append(URLEncoder.encode(parameters.get(mapKey), "utf-8"));
				url.append("&");
			}
		}
		url.append("MAC=");
		url.append(parameters.get("MAC"));
		return url.toString();
	}

	public abstract StringBuilder requstUrlPrefix();

	public abstract Map<String, String> makeParameters(Transaction transaction,
			Map customParams) throws NoSuchAlgorithmException;

}
