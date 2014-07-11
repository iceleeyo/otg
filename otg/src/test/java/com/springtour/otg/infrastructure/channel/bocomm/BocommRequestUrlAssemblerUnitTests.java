package com.springtour.otg.infrastructure.channel.bocomm;

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

public class BocommRequestUrlAssemblerUnitTests {

	private BocommRequestUrlAssembler target;
    private Configurations configurations;
    
    @Before
    public void init(){
        target = new BocommRequestUrlAssembler();
        configurations = new Configurations();
        configurations.setBocommRequestUrl("https://pbanktest.95559.com.cn/netpay/MerPayB2C");
        configurations.setGlobalApplicationUrl("http://180.168.15.70:7001/");
        configurations.setBocommNotifyUrl("otg/upop/receive.htm");
        configurations.setBocommXmlFileName("B2CMerchant.xml");
        configurations.setBocommPfxFileName("301310063009501.PFX");
        configurations.setBocommCertFileName("test_root.cer");
        target.setConfigurations(configurations);
    }
	
	@Test
	public void test() {
		TransactionNo transactionNo = new TransactionNo("2014042448550733");
        OrderIdentity orderId = new OrderIdentity("9", "8003654",
                "8003654");
        Channel channel = new Channel("36");
//        Merchant merchant = new Merchant("上海春秋国旅", channel, "61",
//                "104110059475555", "111111");
        Merchant merchant = new Merchant("上海春秋国旅", channel, "61",
                "104310183986523", "111111");
        final Date creatDate = Calendar.getInstance().getTime();
        Transaction transaction = new Transaction(transactionNo, new Partner(),
                Money.valueOf(new BigDecimal("1")), creatDate, orderId,
                merchant, channel, Gateways.BOC.code());
        String returnUrl = "http://localhost:7001/otgtest/bocommNotificationTest.jsp";
        target.assembleUrl(transaction , returnUrl, "192.168.1.186");
        System.out.println(target.assembleUrl(transaction , returnUrl, "192.168.1.186"));
	}

}
