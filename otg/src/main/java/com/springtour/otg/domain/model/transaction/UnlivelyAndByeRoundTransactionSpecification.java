package com.springtour.otg.domain.model.transaction;

import java.util.*;

public class UnlivelyAndByeRoundTransactionSpecification implements
		TransactionValidationSpecification {
	private final CheckingBatchConfigurations checkingConfigs;
	private final Date now;

	public UnlivelyAndByeRoundTransactionSpecification(
			CheckingBatchConfigurations checkingConfigs, Date now) {
		this.checkingConfigs = checkingConfigs;
		this.now = now;
	}

	@Override
	public boolean satisfiedBy(Transaction transaction) {
		return isAnUnlivelyAndByeRoundAndRequested(transaction, now);
	}

	private boolean isAnUnlivelyAndByeRoundAndRequested(Transaction transaction, final Date now) {
		return checkingConfigs.isUnlivelyAndByeRound(checkingConfigs.roundFor(
				transaction.getWhenRequested(), now))
				&& transaction.isRequested();
	}
}
