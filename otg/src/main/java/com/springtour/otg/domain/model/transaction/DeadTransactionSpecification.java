package com.springtour.otg.domain.model.transaction;

import java.util.*;

public class DeadTransactionSpecification implements
		TransactionValidationSpecification {
	private final CheckingBatchConfigurations checkingConfigs;
	private final Date now;

	public DeadTransactionSpecification(
			CheckingBatchConfigurations checkingConfigs, Date now) {
		this.checkingConfigs = checkingConfigs;
		this.now = now;
	}

	@Override
	public boolean satisfiedBy(Transaction transaction) {
		return isADeadAndRequested(transaction, now);
	}

	private boolean isADeadAndRequested(Transaction transaction, final Date now) {
		return checkingConfigs.isGreaterThanDeadRound(checkingConfigs.roundFor(
				transaction.getWhenRequested(), now))
				&& transaction.isRequested();
	}

}
