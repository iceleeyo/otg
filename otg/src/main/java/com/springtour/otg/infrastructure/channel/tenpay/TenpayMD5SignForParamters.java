package com.springtour.otg.infrastructure.channel.tenpay;

import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Map;

import com.springtour.otg.application.util.Md5Encrypt;

public class TenpayMD5SignForParamters {

	public static String sign(Map<String, String> param)
			throws NoSuchAlgorithmException {
		StringBuilder unSignUrl = new StringBuilder();
		Iterator<String> it = param.keySet().iterator();
		String mapKey;
		while (it.hasNext()) {
			mapKey = it.next();
			if (!mapKey.equals("key")) {
				unSignUrl.append(mapKey);
				unSignUrl.append("=");
				unSignUrl.append(param.get(mapKey));
				unSignUrl.append("&");
			}
		}
		unSignUrl.append("key=");
		unSignUrl.append(param.get("key"));
		return Md5Encrypt.md5(unSignUrl.toString()).toUpperCase();
	}
}
