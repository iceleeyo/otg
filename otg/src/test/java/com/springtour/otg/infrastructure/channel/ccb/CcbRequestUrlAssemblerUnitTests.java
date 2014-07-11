package com.springtour.otg.infrastructure.channel.ccb;

import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.springtour.otg.application.exception.UnavailableCurrencyException;
import com.springtour.otg.application.util.Configurations;
import com.springtour.otg.domain.model.merchant.Merchant;
import com.springtour.otg.domain.model.transaction.Transaction;
import com.springtour.otg.domain.model.transaction.TransactionNo;
import com.springtour.otg.domain.shared.Money;
import com.springtour.otg.infrastructure.channel.ccb.CcbRequestUrlAssembler;

public class CcbRequestUrlAssemblerUnitTests {
	private CcbRequestUrlAssembler target;
	Configurations configurations = new Configurations();
	private static final String merchantCode = "105290047225050";
	private static final String posId = "100000589,30819c300d06092a864886f70d010101050003818a0030818602818064c2a9cf8e6a17508d435da9470ffd5bfcb9298c07fcc5489cfafa46dac3bc05ce7bd576bc8cf6df796cc18bcc5e3111fb327fca44e7920cee8ae3195cd31a41bda4e7e8c9361ec47c8220b88444e92322bb4d97fdec85a6d93fe14fd746777bd17733a7f596d7086c18a73bd4ce744b9a3dc1acd3d35cccdb12c82b270e65f3020111,310000000";
	private static final String requestUrl = "https://ibsbjstar.ccb.com.cn/app/ccbMain";
	private static final String customIp = "172.0.0.1";
	private static final String transactionNo = "50286";
	private static final BigDecimal transactionAmount = BigDecimal
			.valueOf(0.01);
	private Transaction transaction = new Transaction();

	@Before
	public void init() throws UnavailableCurrencyException {
		target = new CcbRequestUrlAssembler();
		target.setConfigurations(configurations);
		configurations.setCcbRequestUrl(requestUrl);
		transaction.setMerchantCode(merchantCode);
		transaction.setTransactionNo(new TransactionNo(transactionNo));
		transaction.setAmount(Money.valueOf(transactionAmount));
		transaction.setMerchant(new Merchant("", null, "", "", posId));

	}

	@Test
	public void assembleUrl() throws NoSuchAlgorithmException,
			UnsupportedEncodingException {
		Map<String, String> customParams = new HashMap<String, String>();
		customParams.put("surferIp", customIp);
		String url = target.assembleUrl(transaction, customParams);
		String expected = "https://ibsbjstar.ccb.com.cn/app/ccbMain?MERCHANTID=105290047225050&POSID=100000589&BRANCHID=310000000&ORDERID=50286&PAYMENT=0.01&CURCODE=01&TXCODE=520100&REMARK1=&REMARK2=&TYPE=1&GATEWAY=W1Z1&CLIENTIP=172.0.0.1&REGINFO=&PROINFO=%25u6625%25u79CB%25u65C5%25u6E38%25u4EA7%25u54C1&REFERER=&MAC=b540b73260039691ebb64ba27559fe75";
		assertEquals(expected, url);
	}

}
