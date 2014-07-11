package com.springtour.otg.domain.model.transaction;

import java.util.*;

public interface TransactionValidationSpecifications {

	DeadTransactionSpecification aDeadSpec(Date now);

	NewBornTransactionSpecification aNewBornSpec(Date now);

	TransactionValidationSpecification anUnlivelyAndByeRoundSpec(
			Date now);

}
