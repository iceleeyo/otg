package com.springtour.otg.infrastructure.channel.icbc;

import static org.junit.Assert.*;

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
import com.springtour.otg.infrastructure.channel.ExternalTransactionQueryObject;
import com.springtour.otg.infrastructure.channel.ExternalTransactionQueryResult;

public class IcbcExternalTransactionQueryObjectUnitTests {

	private IcbcExternalTransactionQueryObject target = new IcbcExternalTransactionQueryObject(
			"19");

	private Configurations configurations = new Configurations();

	@Before
	public void init() {
		configurations.setIcbcJksFileName("icbc.jks");
		configurations.setIcbcQueryRequestUrl("https://corporbank3.dccnet.com.cn/servlet/ICBCINBSEBusinessServlet");
		target.setConfigurations(configurations);
	}

	private Transaction makeTransaction(String txnNo, String merchantCode)
			throws UnavailableCurrencyException, ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse("2013-08-28");
		TransactionNo transactionNo = new TransactionNo(txnNo);
		Channel channel = new Channel("19");
		Merchant merchant = new Merchant("", channel, "", merchantCode,
				"1001EC23742710,12345678");
		Transaction transaction = new Transaction(transactionNo, new Partner(),
				Money.valueOf(new BigDecimal("100.00")), date,
				new OrderIdentity(), merchant, channel, Gateways.ICBC.code());
		return transaction;
	}

	@Test
	public void query() throws UnavailableCurrencyException, ParseException {
		ExternalTransactionQueryResult result = target.queryBy(makeTransaction(
				"20130826349822112", "1001223619006890378"));
		assertEquals(Charge.SUCCESS, result.chargedFromSupplier());
		assertThat(result.amountFromSupplier().getAmount(), Matchers.comparesEqualTo(new  BigDecimal(1)));
		assertEquals("HFG000003937748809", result.extTxnNo());
	}

}
