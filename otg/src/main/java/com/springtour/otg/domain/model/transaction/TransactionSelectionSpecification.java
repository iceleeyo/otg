package com.springtour.otg.domain.model.transaction;

import java.util.*;

public interface TransactionSelectionSpecification {

	List<Transaction> satisfyingElementsFrom(
			TransactionRepository transactionRepository);

}