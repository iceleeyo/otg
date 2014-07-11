package com.springtour.otg.infrastructure.channel.alipaywap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.springtour.otg.infrastructure.channel.alipaywap.sign.RSA;
import com.springtour.otg.infrastructure.logging.LoggerFactory;

public class AlipayWapCore {

	/**
	 * 除去数组中的空值和签名参数
	 * 
	 * @param sArray
	 *            签名参数组
	 * @return 去掉空值与签名参数后的新签名参数组
	 */
	public static Map<String, String> paraFilter(Map<String, String> sArray) {

		Map<String, String> result = new HashMap<String, String>();

		if (sArray == null || sArray.size() <= 0) {
			return result;
		}

		for (String key : sArray.keySet()) {
			String value = sArray.get(key);
			if (value == null || value.equals("")
					|| key.equalsIgnoreCase("sign")
					|| key.equalsIgnoreCase("sign_type")) {
				continue;
			}
			result.put(key, value);
		}

		return result;
	}

	/**
	 * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
	 * 
	 * @param params
	 *            需要排序并参与字符拼接的参数组
	 * @return 拼接后字符串
	 */
	public static String createLinkString(Map<String, String> params) {

		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);

		String prestr = "";

		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = params.get(key);

			if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
				prestr = prestr + key + "=" + value;
			} else {
				prestr = prestr + key + "=" + value + "&";
			}
		}

		return prestr;
	}

	/**
	 * 把数组所有元素按照固定参数排序，以“参数=参数值”的模式用“&”字符拼接成字符串
	 * 
	 * @param params
	 *            需要参与字符拼接的参数组
	 * @return 拼接后字符串
	 */
	public static String createLinkStringNoSort(Map<String, String> params) {
		StringBuilder sParaSort = new StringBuilder();
		sParaSort.append("service="+params.get("service"));
		sParaSort.append("&v="+params.get("v"));
		sParaSort.append("&sec_id="+params.get("sec_id"));
		sParaSort.append("&notify_data="+params.get("notify_data"));
		return sParaSort.toString();
	}
	
	/**
	 * 解密
	 * 
	 * @param inputPara
	 *            要解密数据
	 * @return 解密后结果
	 */
	public static Map<String, String> decrypt(Map<String, String> inputPara) {
		try {
			inputPara.put("notify_data", RSA.decrypt(
					inputPara.get("notify_data"), AlipayWapConfig.private_key,
					AlipayWapConfig.input_charset));
		} catch (Exception e) {
			LoggerFactory.getLogger().error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
		return inputPara;
	}
}
