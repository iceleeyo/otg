package com.springtour.otg.infrastructure.channel.cmb;

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
import com.springtour.otg.infrastructure.channel.upop.UpopRequestUrlAssembler;

public class CmbRequestUrlAssemblerUnitTest {
	private CmbRequestUrlAssembler target;
	private Configurations configurations;
	
	@Before
	public void init(){
		target = new CmbRequestUrlAssembler();
		configurations = new Configurations();
		configurations.setCmbRequestUrl("https://netpay.cmbchina.com/netpayment/BaseHttp.dll");
		configurations.setCmbConnectionType("TestPrePayC2");
		configurations.setCmbBranchId("0021");
		target.setConfigurations(configurations);
	}
	
	@Test
	public void test() {
		TransactionNo transactionNo = new TransactionNo("2014022640");
		OrderIdentity orderId = new OrderIdentity("0", "3120557",
				"SO-140315-1564");
		Channel channel = new Channel("21");
		Merchant merchant = new Merchant("上海春秋国旅", channel, "61",
				"000056", "Aa12345678901234");
		final Date creatDate = Calendar.getInstance().getTime();
		Transaction transaction = new Transaction(transactionNo, new Partner(),
				Money.valueOf(new BigDecimal("250")), creatDate, orderId,
				merchant, channel, Gateways.CMB.code());
		String returnUrl = "http://localhost:7001/otgtest/cmbNotificationTest.jsp";
		String url = target.assembleUrl(transaction , returnUrl, "192.168.1.106");
		System.out.println("url:"+url);
	}
}
