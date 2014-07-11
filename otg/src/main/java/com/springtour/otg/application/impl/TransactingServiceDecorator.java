package com.springtour.otg.application.impl;

import lombok.Setter;

import com.springtour.otg.application.TransactingService;
import com.springtour.otg.application.exception.UnavailableChannelException;
import com.springtour.otg.application.exception.UnavailableCurrencyException;
import com.springtour.otg.application.exception.UnavailableGatewayException;
import com.springtour.otg.application.exception.UnavailableMerchantException;
import com.springtour.otg.application.exception.UnavailablePartnerException;
import com.springtour.otg.application.exception.UnavailablePaymentApplicationException;
import com.springtour.otg.domain.model.merchant.Merchant;
import com.springtour.otg.domain.model.transaction.OrderIdentity;
import com.springtour.otg.domain.model.transaction.Transaction;
import com.springtour.otg.domain.model.transaction.TransactionNo;
import com.springtour.otg.domain.shared.Money;
import com.springtour.otg.infrastructure.logging.LoggingSupport;
import com.springtour.otg.infrastructure.mail.MailManager;

public class TransactingServiceDecorator implements TransactingService {
	@Setter
	private TransactingService target;
	@Setter
	private MailManager mailManager;
	@Setter
	private LoggingSupport loggingSupport;

	public Transaction handle(String notificationSeq) {
		Transaction transaction = target.handle(notificationSeq);
		loggingSupport.notifyMessage("TransactionNo ="
				+ transaction.getTransactionNo().getNumber()
				+ ",SynchronizingMethod ="
				+ transaction.getSynchronizingMethod() + " try handle");
		if (transaction.isNotificationRetrievedByCheck()) {
			mailManager.send("旅游系统订单尝试自动掉单恢复！所属应用为："+transaction.getOrderId().getApplication(), "旅游系统交易流水号为：【"
					+ transaction.getTransactionNo().getNumber()
					+ "】，旅游系统订单尝试自动掉单恢复！", transaction.getOrderId().getApplication());
		}
		return transaction;
	}

	@Override
	public Transaction conclude(TransactionNo transactionNo) {
		return target.conclude(transactionNo);
	}

	@Override
	public Merchant findMerchant(String channelId, String orgId)
			throws UnavailableMerchantException {
		return target.findMerchant(channelId, orgId);
	}

	@Override
	public Transaction newTransaction(String partnerId, String channelId,
			String gateway, String orgId, Money amount, OrderIdentity orderId)
			throws UnavailableChannelException, UnavailableCurrencyException,
			UnavailableGatewayException, UnavailableMerchantException,
			UnavailablePartnerException, UnavailablePaymentApplicationException {
		return target.newTransaction(partnerId, channelId, gateway, orgId,
				amount, orderId);
	}

	@Override
	public Transaction cancelTransaction(String oldTransactionNumber) {
		return target.cancelTransaction(oldTransactionNumber);
	}

	@Override
	public Transaction newTransactionWithChargeFor(String partnerId,
			String channelId, String gateway, String orgId, Money amount,
			OrderIdentity orderId, String chargeFor)
			throws UnavailableChannelException, UnavailableCurrencyException,
			UnavailableGatewayException, UnavailableMerchantException,
			UnavailablePartnerException, UnavailablePaymentApplicationException {
		return target.newTransactionWithChargeFor(partnerId, channelId, gateway, orgId, amount, orderId, chargeFor);
	}
}
