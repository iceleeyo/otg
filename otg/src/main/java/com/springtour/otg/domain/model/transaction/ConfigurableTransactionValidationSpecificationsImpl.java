package com.springtour.otg.domain.model.transaction;

import java.util.*;

import lombok.*;

public class ConfigurableTransactionValidationSpecificationsImpl implements
		TransactionValidationSpecifications {
	@Setter
	private CheckingBatchConfigurations configurations;

	@Override
	public DeadTransactionSpecification aDeadSpec(Date now) {
		return new DeadTransactionSpecification(configurations, now);
	}

	@Override
	public NewBornTransactionSpecification aNewBornSpec(Date now) {
		return new NewBornTransactionSpecification(configurations, now);
	}

	@Override
	public TransactionValidationSpecification anUnlivelyAndByeRoundSpec(Date now) {
		return new UnlivelyAndByeRoundTransactionSpecification(configurations,
				now);
	}
}
