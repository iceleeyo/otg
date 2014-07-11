package com.springtour.otg.infrastructure.channel.ccb;

import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Map;

import com.springtour.otg.application.util.Md5Encrypt;

public class CcbMd5SignForParameters {

	public static String sign(Map<String, String> param)
			throws NoSuchAlgorithmException {
		StringBuilder url = new StringBuilder();
		Iterator<String> it = param.keySet().iterator();
		String mapKey;
		while (it.hasNext()) {
			mapKey = it.next();
			url.append(mapKey);
			url.append("=");
			url.append(param.get(mapKey));
			if (it.hasNext()) {
				url.append("&");
			}
		}
		return Md5Encrypt.md5(url.toString()).toLowerCase();
	}
}
