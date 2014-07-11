package com.springtour.otg.application;

import com.springtour.otg.domain.model.transaction.*;

public interface TransactionCheckingEvents {

	void notifyToCheck(Transaction transaction);

	void notifySpottingDead(Transaction transaction);

	/**
	 * 通知发现了核对状态{@link CheckingState#INVALID}的交易
	 * 
	 * @See CheckingState
	 */
	void notifySpottingInvalid(Transaction transaction);

}
