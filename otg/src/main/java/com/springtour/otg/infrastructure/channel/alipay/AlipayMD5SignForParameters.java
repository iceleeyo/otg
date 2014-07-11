package com.springtour.otg.infrastructure.channel.alipay;

import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Map;

import com.springtour.otg.application.util.Md5Encrypt;

public class AlipayMD5SignForParameters {

	/**
	 * 生成签名
	 * 
	 * @param TreeMap param
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static String sign(Map<String, String> param)
			throws NoSuchAlgorithmException {
		StringBuilder unSignUrl = new StringBuilder();
		Iterator<String> it = param.keySet().iterator();
		String mapKey;
		while (it.hasNext()) {
			mapKey = it.next();
			if (!mapKey.equals("key") && !mapKey.equals("sign_type")) {
				unSignUrl.append(mapKey);
				unSignUrl.append("=");
				unSignUrl.append(param.get(mapKey));
				unSignUrl.append("&");
			}
		}
	 	unSignUrl = new StringBuilder(unSignUrl.substring(0, unSignUrl.length()-1));
		unSignUrl.append(param.get("key"));
		return Md5Encrypt.md5(unSignUrl.toString());
	}
	
}
