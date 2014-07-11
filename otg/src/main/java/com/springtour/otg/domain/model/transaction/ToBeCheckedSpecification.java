package com.springtour.otg.domain.model.transaction;

import static com.springtour.otg.domain.model.transaction.CheckingState.*;

import java.util.*;

import lombok.*;

import com.springtour.otg.infrastructure.persistence.*;
import com.springtour.util.*;

@ToString
@EqualsAndHashCode
public class ToBeCheckedSpecification implements
		TransactionSelectionSpecification {

	private final Set<String> channels;

	public ToBeCheckedSpecification(Set<String> channels) {
		this.channels = ObjectUtils.nullSafe(channels, new HashSet<String>());
	}

	@Override
	public List<Transaction> satisfyingElementsFrom(
			TransactionRepository transactionRepository) {
		return transactionRepository.find(new TransactionCriteria.Builder()
				.eq(UNCHECKED).through(channels).unpaged().build());
	}
}
