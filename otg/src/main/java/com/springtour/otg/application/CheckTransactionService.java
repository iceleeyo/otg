package com.springtour.otg.application;

import com.springtour.otg.domain.model.transaction.*;

public interface CheckTransactionService {

	void check(final TransactionNo transactionNo);

	/**
	 * 将交易掉单恢复或标记为Dead，一般这样的交易是调单或用户放弃付款的
	 * 
	 * 只有{@link CheckingState#Unchecked}的交易才能完成此标记，否则会抛出异常
	 * 
	 * @see CheckingState
	 * 
	 * @param transactionNo
	 * @throws com.springtour.otg.application.exception.CannotChangeCheckingStateException
	 */
	void retrieveOrMarkAsDead(TransactionNo transactionNo);

}