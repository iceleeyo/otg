package com.springtour.otg.infrastructure.channel.tenpay;

import java.math.BigDecimal;
import java.util.Calendar;
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

public class TenpayExternalTransactionQueryObjectIntegrationTests extends
		AbstractOtgIntegrationTests {

	private TenpayExternalTransactionQueryObject target;

	private static final String AN_EXISTED_TXN = "2013012127118905";
	private static final String AN_UNEXISTED_TXN = "2013000000000000";

	private static final String AN_REQUESTED_TXN = "2013031127038205";

	private String txn1 = "2013032032985505";

	private Transaction makeTransaction(String txnNo) {
		TransactionNo transactionNo = new TransactionNo(txnNo);
		Channel channel = new Channel("5");
		Merchant merchant = new Merchant("", channel, "", "1202405801",
				"4a83dbce7f6e88e377b6ce44c4b87001");
		Transaction transaction = new Transaction(transactionNo, new Partner(),
				Money.valueOf(new BigDecimal("1.00")), Calendar.getInstance()
						.getTime(), new OrderIdentity(), merchant, channel,
				Gateways.TENPAY.code());
		return transaction;
	}

	public void testSuccessTxn() {
		target = (TenpayExternalTransactionQueryObject) super
				.getApplicationContext().getBean(
						"otg.TenpayExternalTransactionQueryObject");
		ExternalTransactionQueryResult result = target
				.queryBy(makeTransaction(AN_EXISTED_TXN));
		assertEquals(Charge.SUCCESS, result.chargedFromSupplier());
		assertTrue(BigDecimal.valueOf(1.00).compareTo(
				result.amountFromSupplier().getAmount()) == 0);
	}

	public void testThrowExceptionGivenUnExistedTxn() {
		target = (TenpayExternalTransactionQueryObject) super
				.getApplicationContext().getBean(
						"otg.TenpayExternalTransactionQueryObject");
		Exception expected = null;
		try {
			target.queryBy(makeTransaction(AN_UNEXISTED_TXN));
		} catch (Exception e) {
			expected = e;
		}
		assertTrue(expected != null);

	}

	public void testReturnNullChargeGivenRequestedTxn() {
		target = (TenpayExternalTransactionQueryObject) super
				.getApplicationContext().getBean(
						"otg.TenpayExternalTransactionQueryObject");
		ExternalTransactionQueryResult result = target
				.queryBy(makeTransaction(AN_REQUESTED_TXN));
		assertTrue(result.chargedFromSupplier() == null);
	}

}
