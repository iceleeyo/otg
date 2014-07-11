package com.springtour.otg.infrastructure.channel.cmbc;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import org.jmock.Expectations;
import org.junit.Before;
import org.junit.Test;

import com.springtour.otg.application.util.Configurations;
import com.springtour.otg.domain.model.channel.Channel;
import com.springtour.otg.domain.model.channel.Gateways;
import com.springtour.otg.domain.model.merchant.Merchant;
import com.springtour.otg.domain.model.partner.Partner;
import com.springtour.otg.domain.model.transaction.OrderIdentity;
import com.springtour.otg.domain.model.transaction.Transaction;
import com.springtour.otg.domain.model.transaction.TransactionNo;
import com.springtour.otg.domain.model.transaction.TransactionType;
import com.springtour.otg.domain.shared.Money;
import com.springtour.otg.infrastructure.time.DateUtils;
import com.springtour.test.AbstractJMockUnitTests;

public class CmbcRequestUrlAssemblerUnitTests extends AbstractJMockUnitTests {
	private CmbcRequestUrlAssembler target;
	private Configurations configurations = context.mock(Configurations.class);
	private Encryption encryption = context.mock(Encryption.class);

	@Before
	public void init() {
		target = new CmbcRequestUrlAssembler();
		target.setConfigurations(configurations);
		target.setEncryption(encryption);
	}

	@Test
	public void testAssemblerRequestUrl() throws Exception {
		TransactionNo transactionNo = new TransactionNo("2011110100020105");
		OrderIdentity orderId = new OrderIdentity("1", "391323",
				"SO-111028-4038");
		Channel channel = new Channel("16");
		Merchant merchant = new Merchant("春秋商旅", channel, "61",
				"305010601390507", "");
		final Date creatDate = Calendar.getInstance().getTime();
		Transaction transaction = new Transaction(transactionNo, new Partner(),
				Money.valueOf(new BigDecimal("100.00")), creatDate, orderId,
				merchant, channel, Gateways.ALIPAY.code());
		String returnUrl = "http://192.168.9.44:7001/cmbcTest.jsp";

		context.checking(new Expectations() {
			{
				allowing(configurations).getCmbcRequestUrl();
				will(returnValue("https://114.255.47.40/pay/merchant_pay.html"));
				// 由于加密结果每次不一样所以采用模拟的方式加密数据
				allowing(encryption)
						.encrypt(
								"2011110100020105|100.00|01|"
										+ DateUtils.getDate(creatDate)
										+ "|"
										+ DateUtils.getTime(creatDate)
										+ "|305010601390507|春秋商旅|||0|http://192.168.9.44:7001/cmbcTest.jsp||0|0||||");
				will(returnValue("encryptStr"));

			}
		});
		String actualUrl = target.assembleUrl(transaction, returnUrl);
		assertEquals(
				"https://114.255.47.40/pay/merchant_pay.html?reqStr=encryptStr&customerId=305010601390507",
				actualUrl);
	}
}
