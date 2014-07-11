package com.springtour.otg.infrastructure.channel.alipaywap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import com.springtour.otg.application.exception.UnavailableCurrencyException;
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

public class AlipayWapExternalTransactionQueryObjectUnitTests {
	private AlipayWapExternalTransactionQueryObject target = new AlipayWapExternalTransactionQueryObject(
			"34");
	private Configurations configurations = new Configurations();
	Transaction transaction = null;
	@Before
	public void init() {
		target.setConfigurations(configurations);
	}
	
	private Transaction makeTransaction(String txnNo, String merchantCode)
			throws UnavailableCurrencyException, ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = sdf.parse("20140227104347");
		TransactionNo transactionNo = new TransactionNo(txnNo);
		Channel channel = new Channel("20");
		Merchant merchant = new Merchant("上海春秋国旅", channel, "61",
				"sandbox_zhanhuo@alitest.com","2088102135257262,inxarkpus6te6gyktexh09810enfy4qg");
		transaction = new Transaction(transactionNo, new Partner(),
				Money.valueOf(new BigDecimal("0.01")), date,
				new OrderIdentity(), merchant, channel, Gateways.ALIPAY.code());
		return transaction;
	}
	
	@Test
	public void query() throws UnavailableCurrencyException, ParseException {
		ExternalTransactionQueryResult result = target.queryBy(makeTransaction(
				"2014022641076234", "105550149170027"));
		assertEquals(Charge.SUCCESS, result.chargedFromSupplier());
		assertThat(result.amountFromSupplier().getAmount(), Matchers.comparesEqualTo(transaction.getAmount().getAmount()));
		assertEquals("2014042348155341", result.extTxnNo());
	}
}
