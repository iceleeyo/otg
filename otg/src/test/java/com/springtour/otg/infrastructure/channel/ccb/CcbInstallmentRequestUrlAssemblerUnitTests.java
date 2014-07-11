package com.springtour.otg.infrastructure.channel.ccb;

import static org.junit.Assert.assertEquals;

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

public class CcbInstallmentRequestUrlAssemblerUnitTests {

	private CcbInstallmentRequestUrlAssembler target;
	Configurations configurations = new Configurations();
	private static final String merchantCode = "105290047225049";
												   
	private static final String posId = "901684510,30819c300d06092a864886f70d010101050003818a003081860281805b94871e33428c0baf7468f515c45945551d25b32723da29ed7179ffe113bc88ffe64ab339a84abb0e1ea5dcd7104081735b61cfd295ed52d9ff84a152431e92541784fce2d8879417eea98f1d2c1f576366541df5eef8209cea22a60b5ef4a7f03151302bc0e1a0ff4149771a1012fb3fdf78c226fb79f0e3aca9fca1486593020111,310000000";
	private static final String requestUrl = "https://ibsbjstar.ccb.com.cn/app/B2CMainPlat?CCB_IBSVersion=V5";
	private static final String customIp = "180.168.15.70";
	private static final String transactionNo = "50286";
	private static final BigDecimal transactionAmount = BigDecimal
			.valueOf(0.01);
	private Transaction transaction = new Transaction();

	@Before
	public void init() throws UnavailableCurrencyException {
		target = new CcbInstallmentRequestUrlAssembler();
		target.setConfigurations(configurations);
		configurations.setCcbInstallmentRequestUrl(requestUrl);
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
		customParams.put("installment", "6");
		String url = target.assembleUrl(transaction, customParams);
		String expected = "https://ibsbjstar.ccb.com.cn/app/B2CMainPlat?CCB_IBSVersion=V5&MERCHANTID=105290047225049&POSID=901684510&BRANCHID=310000000&ORDERID=50286&PAYMENT=0.01&CURCODE=01&TXCODE=410800&REMARK1=&REMARK2=&TYPE=1&GATEWAY=0&CLIENTIP=180.168.15.70&REGINFO=&PROINFO=%25u6625%25u79CB%25u65C5%25u6E38%25u4EA7%25u54C1&REFERER=&INSTALLNUM=6&MAC=daa4114cbca284493676cc4bf10056dd";
		assertEquals(expected, url);
	}
}
