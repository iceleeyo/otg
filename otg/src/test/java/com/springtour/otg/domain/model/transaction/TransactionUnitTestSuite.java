package com.springtour.otg.domain.model.transaction;

import org.junit.runner.*;
import org.junit.runners.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({ DeadTransactionSpecificationUnitTests.class,
		NewBornTransactionSpecificationUnitTests.class,
		UnlivelyAndByeRoundTransactionSpecificationUnitTests.class,
		ToBeCheckedSpecificationUnitTests.class, TransactionMockTests.class,
		TransactionNoMockTests.class,
		CheckingBatchConfigurationsUnitTests.class,
		TransactionTypeUnitTests.class, TransactionFactoryImplMockTests.class,
		CorrelatedCancelSpecificationUnitTests.class })
public class TransactionUnitTestSuite {
}
