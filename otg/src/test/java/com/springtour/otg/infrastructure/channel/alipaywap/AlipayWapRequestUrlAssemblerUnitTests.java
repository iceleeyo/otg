package com.springtour.otg.infrastructure.channel.alipaywap;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
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

public class AlipayWapRequestUrlAssemblerUnitTests {
	private AlipayWapRequestUrlAssembler target;
	private Configurations configurations = new Configurations(); 
	
	@Before
	public void init(){
		target = new AlipayWapRequestUrlAssembler();
		configurations.setGlobalApplicationUrl("http://180.168.15.70:7001/");
		configurations.setAlipayWapNotifyPath("otg/upop/receive.htm");
		target.setConfigurations(configurations);
	}

	@Test
	public void test() {
		TransactionNo transactionNo = new TransactionNo("2014042441095934");
		OrderIdentity orderId = new OrderIdentity("0", "3120454",
				"SO-140224-1467");
		Channel channel = new Channel("34");
		Merchant merchant = new Merchant("上海春秋国旅", channel, "61",
				"sandbox_zhanhuo@alitest.com","2088102135257262,inxarkpus6te6gyktexh09810enfy4qg");
		final Date creatDate = Calendar.getInstance().getTime();
		Transaction transaction = new Transaction(transactionNo, new Partner(),
				Money.valueOf(new BigDecimal("10")), creatDate, orderId,
				merchant, channel, Gateways.ALIPAY.code());
		String returnUrl = "http://192.168.1.186:7001/otgtest/alipayWapTest.jsp";
		String url = target.assembleUrl(transaction , returnUrl, "192.168.1.186");
		System.out.println("url:"+url);
	}

}
