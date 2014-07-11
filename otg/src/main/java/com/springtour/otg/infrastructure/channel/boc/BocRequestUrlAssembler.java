package com.springtour.otg.infrastructure.channel.boc;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.log4j.Logger;

import lombok.Setter;

import com.bocnet.common.security.PKCS7Tool;
import com.springtour.otg.application.util.Configurations;
import com.springtour.otg.application.util.RequestUtils;
import com.springtour.otg.domain.model.transaction.Transaction;
import com.springtour.otg.infrastructure.logging.LoggerFactory;

public class BocRequestUrlAssembler {

	@Setter
	private Configurations configurations;
	
	Logger log = LoggerFactory.getLogger();

	public String assembleUrl(Transaction transaction, String returnUrl,
			String ip) {
		String merchantNo = transaction.getMerchantCode();
		String orderNo = transaction.getTransactionNo().getNumber();
		BigDecimal orderAmount = transaction.getAmount().getAmount().setScale(2);
		String orderTime = new SimpleDateFormat("yyyyMMddHHmmss")
				.format(transaction.getWhenRequested());
		StringBuilder url = new StringBuilder(configurations.getBocRequestUrl());
		url.append("?");
		Map<String, String> parms = new HashMap<String, String>();
		parms.put("merchantNo", merchantNo);
		parms.put("payType", BocConstants.PAY_TYPE);
		parms.put("orderNo", orderNo);
		parms.put("curCode", BocConstants.CNY);
		parms.put("orderAmount", orderAmount.toString());
		parms.put("orderTime", orderTime);
		parms.put("orderNote", "上海春秋国旅旅游产品");
		parms.put("orderUrl", returnUrl);
		String signDataStr = orderNo + "|" + orderTime + "|" + BocConstants.CNY
				+ "|" + orderAmount.toString() + "|" + merchantNo;
		try {
			String certFilePath = this.getClass().getResource(configurations.getBocCertFileResourceName()).getPath();
			PKCS7Tool tool = PKCS7Tool.getSigner(certFilePath, transaction.getMerchant().getKey(), transaction.getMerchant().getKey());
			parms.put("signData", tool.sign(signDataStr.getBytes()));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		url.append(RequestUtils.joinMapValue(parms, '&'));
		LoggerFactory.getLogger().info("请求中国银行的form：" + RequestUtils.generateAutoSubmitForm(configurations.getBocRequestUrl(), parms));
		return url.toString();
	}

}
