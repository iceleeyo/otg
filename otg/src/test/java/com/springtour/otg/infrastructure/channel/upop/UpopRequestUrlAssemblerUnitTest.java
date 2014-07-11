package com.springtour.otg.infrastructure.channel.upop;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

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
import com.springtour.otg.domain.shared.Money;

public class UpopRequestUrlAssemblerUnitTest {
	private UpopRequestUrlAssembler target;
	private Configurations configurations;
	@Before
	public void init(){
		target = new UpopRequestUrlAssembler();
		configurations = new Configurations();
		configurations.setUpopNotifyUrl("http://127.0.0.1:7001/otgtest/upopNotificationTest.jsp");
		target.setConfigurations(configurations);
	}
	@Test
	public void test() {
		TransactionNo transactionNo = new TransactionNo("2014022641076214");
		OrderIdentity orderId = new OrderIdentity("0", "3120454",
				"SO-140224-1467");
		Channel channel = new Channel("20");
		Merchant merchant = new Merchant("上海春秋国旅", channel, "61",
				"105550149170027", "88888888");
		final Date creatDate = Calendar.getInstance().getTime();
		Transaction transaction = new Transaction(transactionNo, new Partner(),
				Money.valueOf(new BigDecimal("100.00")), creatDate, orderId,
				merchant, channel, Gateways.ALIPAY.code());
		String returnUrl = "http://localhost:7001/otgtest/upopTest.jsp";
		String url = target.assembleUrl(transaction , returnUrl, "192.168.1.106");
		System.out.println("url:"+url);
	}

}
