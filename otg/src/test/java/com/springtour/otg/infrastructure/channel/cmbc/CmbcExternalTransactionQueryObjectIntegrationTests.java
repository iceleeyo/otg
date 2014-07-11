package com.springtour.otg.infrastructure.channel.cmbc;

import java.math.BigDecimal;
import java.util.Calendar;
import com.springtour.otg.application.exception.UnavailableCurrencyException;
import com.springtour.otg.domain.model.channel.Channel;
import com.springtour.otg.domain.model.channel.Gateways;
import com.springtour.otg.domain.model.merchant.Merchant;
import com.springtour.otg.domain.model.notification.Charge;
import com.springtour.otg.domain.model.partner.Partner;
import com.springtour.otg.domain.model.transaction.OrderIdentity;
import com.springtour.otg.domain.model.transaction.Transaction;
import com.springtour.otg.domain.model.transaction.TransactionNo;
import com.springtour.otg.domain.model.transaction.TransactionType;
import com.springtour.otg.domain.shared.Money;
import com.springtour.otg.infrastructure.channel.ExternalTransactionQueryResult;
import com.springtour.otg.integration.AbstractOtgIntegrationTests;

public class CmbcExternalTransactionQueryObjectIntegrationTests extends
		AbstractOtgIntegrationTests {

	private static final String CMBC_MERCHANT_CODE = "305010601390507";
	private static final String AN_EXISTED_TXN = "90507201304261105001";

	private CmbcExternalTransactionQueryObject target;

	private Transaction makeTransaction(String txnNo, String merchantCode)
			throws UnavailableCurrencyException {
		TransactionNo transactionNo = new TransactionNo(txnNo);
		Channel channel = new Channel("17");
		Merchant merchant = new Merchant("", channel, "", merchantCode, "");
		Transaction transaction = new Transaction(transactionNo, new Partner(),
				Money.valueOf(new BigDecimal("100.00")), Calendar.getInstance()
						.getTime(), new OrderIdentity(), merchant, channel,
				Gateways.CMBC.code());
		return transaction;
	}

	public void testSuccessTxn() {
		target = (CmbcExternalTransactionQueryObject) super
				.getApplicationContext().getBean(
						"otg.CmbcExternalTransactionQueryObject");
		ExternalTransactionQueryResult result = target.queryBy(makeTransaction(
				AN_EXISTED_TXN, CMBC_MERCHANT_CODE));
		assertEquals(Charge.SUCCESS, result.chargedFromSupplier());
		assertEquals(Money.valueOf(new BigDecimal(100)),
				result.amountFromSupplier());
		assertEquals(AN_EXISTED_TXN, result.extTxnNo());
	}

}
