package com.springtour.otg.domain.model.transaction;

public interface TransactionValidationSpecification {

	boolean satisfiedBy(Transaction transaction);

}