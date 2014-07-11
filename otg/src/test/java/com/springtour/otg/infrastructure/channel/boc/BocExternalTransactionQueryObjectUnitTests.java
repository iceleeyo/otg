package com.springtour.otg.infrastructure.channel.boc;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import com.springtour.otg.application.util.Configurations;
import com.springtour.otg.domain.model.channel.Channel;
import com.springtour.otg.domain.model.channel.Gateways;
import com.springtour.otg.domain.model.merchant.Merchant;
import com.springtour.otg.domain.model.notification.Charge;
import com.springtour.otg.domain.model.partner.Partner;
import com.springtour.otg.domain.model.transaction.OrderIdentity;
import com.springtour.otg.domain.model.transaction.Transaction;
import com.springtour.otg.domain.model.transaction.TransactionNo;
import com.springtour.otg.domain.shared.Money;
import com.springtour.otg.infrastructure.channel.ExternalTransactionQueryResult;

public class BocExternalTransactionQueryObjectUnitTests {
	private BocExternalTransactionQueryObject target;
	private Configurations configurations;
	
	@Before
	public void init(){
		configurations = new Configurations();
		configurations.setBocQueryUrl("https://ebspay.boc.cn/PGWPortal/QueryOrder.do");
		configurations.setBocCertFileName("bocproductcer.pfx");
		target = new BocExternalTransactionQueryObject("32");
		target.setConfigurations(configurations);
	}
	
	@Test
	public void test() {
		TransactionNo transactionNo = new TransactionNo("2014070841128335");
        OrderIdentity orderId = new OrderIdentity("9", "8015381",
                "8015381");
        Channel channel = new Channel("32");
//        Merchant merchant = new Merchant("上海春秋国旅", channel, "61",
//                "104110059475555", "111111");
        Merchant merchant = new Merchant("上海春秋国旅", channel, "61",
                "104310183986523", "111111");
        final Date creatDate = Calendar.getInstance().getTime();
        Transaction transaction = new Transaction(transactionNo, new Partner(),
                Money.valueOf(new BigDecimal("1")), creatDate, orderId,
                merchant, channel, Gateways.BOC.code());
        ExternalTransactionQueryResult result = target.queryBy(transaction);
        assertEquals(Charge.SUCCESS, result.chargedFromSupplier());
        assertThat(result.amountFromSupplier().getAmount(), Matchers.comparesEqualTo(new BigDecimal(0.1).setScale(2, RoundingMode.HALF_UP)));
	}

}
