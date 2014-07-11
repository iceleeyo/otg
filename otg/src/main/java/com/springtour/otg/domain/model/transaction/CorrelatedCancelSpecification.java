package com.springtour.otg.domain.model.transaction;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import com.springtour.otg.infrastructure.persistence.TransactionCriteria;

@ToString
@EqualsAndHashCode
public class CorrelatedCancelSpecification implements
		TransactionSelectionSpecification {

	private String oldTransactionNumber;

	public CorrelatedCancelSpecification(String oldTransactionNumber) {
		this.oldTransactionNumber = oldTransactionNumber;
	}

	@Override
	public List<Transaction> satisfyingElementsFrom(
			TransactionRepository transactionRepository) {
		TransactionCriteria criteria = new TransactionCriteria.Builder()
				.transactionTypeEq(TransactionType.CANCEL.getCode())
				.referenceTxnNumberEq(oldTransactionNumber).build();
		return transactionRepository.find(criteria);
	}

}
