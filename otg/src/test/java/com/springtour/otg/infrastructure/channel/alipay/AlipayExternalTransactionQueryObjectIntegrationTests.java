package com.springtour.otg.infrastructure.channel.alipay;

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

public class AlipayExternalTransactionQueryObjectIntegrationTests extends
		AbstractOtgIntegrationTests {

	private static final String ALIPAY_MERCHANT_CODE1 = "2088101909164661,9irdgr6sx8a7s20905ndvqhp77jeeerg";
	private static final String ALIPAY_MERCHANT_CODE2 = "2088201324619468,ahmibnzz0njr1xj9laxrthtf2vl933tk";
	private static final String AN_EXISTED_TXN = "2012120326994307";
	private static final String AN_EXISTED_TXN2 = "2012120226939807";
	private static final String AN_UNEXISTED_TXN = "2013000000000000";
	private static final String AN_REQUESTED_TXN = "2013031127040207";

	private AlipayExternalTransactionQueryObject target;

	private Transaction makeTransaction(String txnNo, String merchantCode)
			throws UnavailableCurrencyException {
		TransactionNo transactionNo = new TransactionNo(txnNo);
		Channel channel = new Channel("7");
		Merchant merchant = new Merchant("", channel, "", "", merchantCode);
		Transaction transaction = new Transaction(transactionNo, new Partner(),
				Money.valueOf(new BigDecimal("1.00")), Calendar.getInstance()
						.getTime(), new OrderIdentity(), merchant, channel,
				Gateways.ALIPAY.code());
		return transaction;
	}

	public void testSuccessB2bAlipayTxn() {
		target = (AlipayExternalTransactionQueryObject) super
				.getApplicationContext().getBean(
						"otg.b2c.AlipayExternalTransactionQueryObject");
		ExternalTransactionQueryResult result = target.queryBy(makeTransaction(
				AN_EXISTED_TXN, ALIPAY_MERCHANT_CODE1));
		assertEquals(Charge.SUCCESS, result.chargedFromSupplier());
	}

	public void testSuccessB2cAlipayTxn() {
		target = (AlipayExternalTransactionQueryObject) super
				.getApplicationContext().getBean(
						"otg.b2c.AlipayExternalTransactionQueryObject");
		ExternalTransactionQueryResult result = target.queryBy(makeTransaction(
				AN_EXISTED_TXN2, ALIPAY_MERCHANT_CODE2));
		assertEquals(Charge.SUCCESS, result.chargedFromSupplier());
	}

	public void testThrowsExceptionGivenUnExistedTxn() {
		target = (AlipayExternalTransactionQueryObject) super
				.getApplicationContext().getBean(
						"otg.b2c.AlipayExternalTransactionQueryObject");
		Exception expected = null;
		try {
			target.queryBy(makeTransaction(AN_UNEXISTED_TXN,
					ALIPAY_MERCHANT_CODE1));
		} catch (Exception e) {
			expected = e;
		}
		assertTrue(expected != null);
	}

	public void testReturnNullChargeGivenRequestedTxn() {
		target = (AlipayExternalTransactionQueryObject) super
				.getApplicationContext().getBean(
						"otg.b2c.AlipayExternalTransactionQueryObject");
		ExternalTransactionQueryResult result = target.queryBy(makeTransaction(
				AN_REQUESTED_TXN, ALIPAY_MERCHANT_CODE1));
		assertTrue(result.chargedFromSupplier() == null);
	}

}
