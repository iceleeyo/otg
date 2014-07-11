package com.springtour.otg.infrastructure.channel.cmbc;

import java.util.ArrayList;

public class AssembleRequestParamsForEncryption {

	private String SEPERETOR = "|";

	/**
	 * 将参数依次拼装成民生银行待加密的字符串
	 * 
	 * 示例1：返回的数据域为卡号，余额，备注（为空）， 对应的封装为123456|200.00|
	 * 
	 * @param params
	 * @return
	 */
	public String assemble(ArrayList<String> params) {
		StringBuilder str = new StringBuilder();
		for (String key : params) {
			str.append(key);
			str.append(SEPERETOR);
		}
		if (params.size() > 0) {
			return str.substring(0, str.length() - 1);
		}
		return str.toString();
	}

}
