package com.springtour.otg.infrastructure.channel;

import static com.springtour.otg.domain.model.transaction.Transaction.State.*;

import java.util.*;

import lombok.*;

import org.apache.log4j.*;

import com.springtour.otg.domain.model.notification.*;
import com.springtour.otg.domain.model.transaction.*;
import com.springtour.otg.domain.service.*;
import com.springtour.otg.domain.shared.*;
import com.springtour.otg.infrastructure.logging.*;

public class TransactionCheckerImpl implements TransactionChecker {
	@Setter
	private ExternalTransactionQueryObject externalTransactionQueryObject;

	@Override
	public CheckResult check(Transaction transaction) {
		ExternalTransactionQueryResult queryResult = externalTransactionQueryObject
				.queryBy(transaction);

		logQueryResult(transaction, queryResult);

		if(queryResult instanceof NonExistedExternalTxnQueryResult){
			if(transaction.is(REQUESTED)){
				return CheckResult.unhandled(transaction.getTransactionNo());
			}else {
				return invalid(transaction, queryResult);
			}
		}
		// 核对金额
		if (!transaction.sameAmountAs(amountFromSupplier(queryResult))) {
			return invalid(transaction, queryResult);
		}
		// 如果otg的transaction还未收到通知且供应商告知还未扣款，返回未处理
		if (anUnhanded(transaction, queryResult)) {
			return CheckResult.unhandled(transaction.getTransactionNo());
		}
		// 如果otg的transaction还未收到通知但供应商告知已扣款，返回掉单
		if (aNotificationLost(transaction, queryResult)) {
			return CheckResult.notificationLost(transaction.getTransactionNo(),
					getExtTxnNo(queryResult), charged(queryResult));
		}
		// 如果otg的transaction已处理过通知，核对交易结果
		if (!aHandled(transaction, queryResult)) {
			return invalid(transaction, queryResult);
		}

		return CheckResult.valid(transaction.getTransactionNo(),
				getExtTxnNo(queryResult), charged(queryResult));
	}

	private void logQueryResult(Transaction transaction,
			ExternalTransactionQueryResult queryResult) {
		getLogger().info(
				"check " + transaction.getTransactionNo() + ",amount="
						+ transaction.getAmount() + ",state="
						+ transaction.state() + ",result=" + queryResult);
	}

	protected Logger getLogger() {
		return LoggerFactory.getLogger();
	}

	private boolean anUnhanded(Transaction transaction,
			ExternalTransactionQueryResult checker) {
		return transaction.is(REQUESTED) && null == charged(checker);
	}

	private boolean aNotificationLost(Transaction transaction,
			ExternalTransactionQueryResult checker) {
		return transaction.is(REQUESTED)
				&& (Charge.SUCCESS.equals(charged(checker)) || Charge.FAILURE
						.equals(charged(checker)));
	}

	private CheckResult invalid(Transaction transaction,
			ExternalTransactionQueryResult checker) {
		return CheckResult.invalid(transaction.getTransactionNo(),
				getExtTxnNo(checker), charged(checker));
	}

	private boolean aHandled(Transaction transaction,
			ExternalTransactionQueryResult checker) {
		return aFailedToMakePayment(transaction, checker)
				|| aPaymentMade(transaction, checker);
	}

	private boolean aPaymentMade(Transaction transaction,
			ExternalTransactionQueryResult checker) {
		return (transaction.is(CONCLUDED) || transaction.is(RESPONSED_SUCCESS))
				&& Charge.SUCCESS.equals(charged(checker));
	}

	private boolean aFailedToMakePayment(Transaction transaction,
			ExternalTransactionQueryResult checker) {
		return transaction.is(RESPONSED_FAILURE)
				&& Charge.FAILURE.equals(charged(checker));
	}

	protected Charge charged(ExternalTransactionQueryResult checker) {
		return checker.chargedFromSupplier();
	}

	protected String getExtTxnNo(ExternalTransactionQueryResult checker) {
		return checker.extTxnNo();
	}

	protected Money amountFromSupplier(ExternalTransactionQueryResult checker) {
		return checker.amountFromSupplier();
	}

	@Override
	public Set<String> supportedChannels() {
		return externalTransactionQueryObject.supportedChannels();
	}

	@Override
	public ToBeCheckedSpecification aToBeCheckedSpec() {
		return new ToBeCheckedSpecification(
				externalTransactionQueryObject.supportedChannels());
	}

}
