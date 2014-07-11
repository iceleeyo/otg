package com.springtour.otg.infrastructure.channel.ccb;

import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.Setter;
import org.apache.commons.lang.StringEscapeUtils;
import com.springtour.otg.application.util.Configurations;
import com.springtour.otg.domain.model.transaction.Transaction;

public class CcbInstallmentRequestUrlAssembler extends AbstractCcbUrlAssembler {

	@Setter
	private Configurations configurations;

	public StringBuilder requstUrlPrefix() {
		StringBuilder url = new StringBuilder(
				configurations.getCcbInstallmentRequestUrl());
		url.append("&");
		return url;
	}

	public Map<String, String> makeParameters(Transaction transaction,
			Map customParams) throws NoSuchAlgorithmException {
		String ip = (String) customParams.get("surferIp");
		if (ip == null || "".equals(ip)) {
			throw new IllegalArgumentException("ip should not be null!");
		}
		String installment = (String) customParams.get("installment");
		if (installment == null || "".equals(installment)) {
			throw new IllegalArgumentException(
					"installment should not be null!");
		}
		Map<String, String> param = new LinkedHashMap<String, String>();
		param.put("MERCHANTID", transaction.getMerchantCode());
		param.put("POSID", transaction.getMerchant().getKey().split(",")[0]);
		param.put("BRANCHID", transaction.getMerchant().getKey().split(",")[2]);
		param.put("ORDERID", transaction.getTransactionNo().getNumber());
		param.put("PAYMENT", transaction.getAmount().getAmount().toString());
		param.put("CURCODE", CcbConstants.CURCODE);
		param.put("TXCODE", CcbConstants.TXCODE_CREDIT_CARD);
		param.put("REMARK1", "");
		param.put("REMARK2", "");
		param.put("TYPE", CcbConstants.TYPE);
		String privateKey = transaction.getMerchant().getKey().split(",")[1];
		String pub = privateKey.substring(privateKey.length() - 30);
		param.put("PUB", pub);
		param.put("GATEWAY", CcbConstants.GATEWAY_DEFAULT_CARD);
		param.put("CLIENTIP", ip);
		param.put("REGINFO", "");
		param.put("PROINFO", escapeChar("春秋旅游产品"));
		param.put("REFERER", "");
		param.put("INSTALLNUM", installment);
		param.put("MAC", CcbMd5SignForParameters.sign(param));
		return param;
	}

	public static String escapeChar(String string) {
		String escapedString = StringEscapeUtils.escapeJava(string);
		return escapedString.replaceAll("\\\\", "%");
	}

}
