/**
 * 
 */
package com.springtour.otg.infrastructure.channel.alipay;

import com.springtour.otg.application.exception.UnavailableCurrencyException;
import com.springtour.otg.application.util.Configurations;
import com.springtour.otg.domain.model.channel.Channel;
import com.springtour.otg.domain.model.channel.Gateway;
import com.springtour.otg.domain.model.channel.Gateways;
import com.springtour.otg.domain.model.merchant.Merchant;
import com.springtour.otg.domain.model.partner.Partner;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Calendar;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.springtour.otg.domain.model.transaction.OrderIdentity;
import com.springtour.otg.domain.model.transaction.Transaction;
import com.springtour.otg.domain.model.transaction.TransactionNo;
import com.springtour.otg.domain.model.transaction.TransactionType;
import com.springtour.otg.domain.shared.Money;

/**
 * @author Future
 * 
 */
public class AlipayHandlingServiceImplTests {

	private AlipayHandlingServiceImpl alipayHandlingService;

	@Before
	public void setUp() {
		alipayHandlingService = new AlipayHandlingServiceImpl();
	}

	@Test
	public void testPayConvert() throws NoSuchAlgorithmException,
			UnsupportedEncodingException, UnavailableCurrencyException {
		String key = "2088201324619468,ahmibnzz0njr1xj9laxrthtf2vl933tk";
		String payUrl = "https://mapi.alipay.com/gateway.do";
		String returnUrl = "otg/alipay/responseReturn.htm";
		String notifyUrl = "otg/alipay/responseNotify.htm";
		String globalUrl = "http://192.168.210.53:7001/";
		Configurations configurations = new Configurations();
		configurations.setAlipayRequestUrl(payUrl);
		configurations.setGlobalApplicationUrl(globalUrl);
		alipayHandlingService.setConfigurations(configurations);
		String url = alipayHandlingService.payConvert(makeTransaction(), key,
				notifyUrl, returnUrl);
//		System.out.println("url:"+url);
		Assert.assertTrue(url
				.equals("https://mapi.alipay.com/gateway.do?_input_charset=gbk&credit_card_pay=Y&notify_url=http%3A%2F%2F192.168.210.53%3A7001%2Fotg%2Falipay%2FresponseNotify.htm&out_trade_no=2011110100020105&partner=2088201324619468&payment_type=1&return_url=otg%2Falipay%2FresponseReturn.htm&seller_email=chujing%40china-sss.com&service=create_direct_pay_by_user&sign_type=MD5&subject=%B4%BA%C7%EF%C2%C3%D3%CE%B2%FA%C6%B7&total_fee=100.00&sign=035cffbe2514f4ab3a057fa7e8ae8a48"));
		String urlForTDC = alipayHandlingService.payConvert(makeTransactionForTDC(), key,
				notifyUrl, returnUrl);
//		System.out.println("urlForTDC:"+urlForTDC);
		Assert.assertTrue(urlForTDC
				.equals("https://mapi.alipay.com/gateway.do?_input_charset=gbk&credit_card_pay=Y&notify_url=http%3A%2F%2F192.168.210.53%3A7001%2Fotg%2Falipay%2FresponseNotify.htm&out_trade_no=2011110100020106&partner=2088201324619468&payment_type=1&qr_pay_mode=2&return_url=otg%2Falipay%2FresponseReturn.htm&seller_email=chujing%40china-sss.com&service=create_direct_pay_by_user&sign_type=MD5&subject=%B4%BA%C7%EF%C2%C3%D3%CE%B2%FA%C6%B7&total_fee=100.00&sign=090d4af797e487858b5039735a93ef7b"));
	}

	private Transaction makeTransaction() throws UnavailableCurrencyException {
		TransactionNo transactionNo = new TransactionNo("2011110100020105");
		OrderIdentity orderId = new OrderIdentity("1", "391323",
				"SO-111028-4038");
		Channel channel = new Channel("7");
		channel.updateGateways(Arrays.asList(new Gateway(Gateways.ALIPAY)));
		Merchant merchant = new Merchant("", channel, "61",
				"chujing@china-sss.com", "");

		Transaction transaction = new Transaction(transactionNo, new Partner(),
				Money.valueOf(new BigDecimal("100.00")), Calendar.getInstance()
						.getTime(), orderId, merchant, channel,
				Gateways.ALIPAY.code());
		return transaction;
	}
	
	private Transaction makeTransactionForTDC() throws UnavailableCurrencyException {
		TransactionNo transactionNo = new TransactionNo("2011110100020106");
		OrderIdentity orderId = new OrderIdentity("1", "391323",
				"SO-111028-4038");
		Channel channel = new Channel("7");
		channel.updateGateways(Arrays.asList(new Gateway(Gateways.TDC)));
		Merchant merchant = new Merchant("", channel, "61",
				"chujing@china-sss.com", "");

		Transaction transaction = new Transaction(transactionNo, new Partner(),
				Money.valueOf(new BigDecimal("100.00")), Calendar.getInstance()
						.getTime(), orderId, merchant, channel,
				Gateways.TDC.code());
		return transaction;
	}
}
