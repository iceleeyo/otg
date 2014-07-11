package com.springtour.otg.domain.model.transaction;

import java.util.*;

public class NewBornTransactionSpecification implements
		TransactionValidationSpecification {
	private final CheckingBatchConfigurations configurations;
	private final Date now;

	public NewBornTransactionSpecification(
			CheckingBatchConfigurations configurations, Date now) {
		this.configurations = configurations;
		this.now = now;
	}

	@Override
	public boolean satisfiedBy(Transaction transaction) {
		return isNewBornAndRequested(transaction, now);
	}

	private boolean isNewBornAndRequested(Transaction transaction, final Date now) {
		return configurations.isLessEqualNewBornRound(configurations.roundFor(
				transaction.getWhenRequested(), now))
				&& transaction.isRequested();
	}
}
