package com.springtour.otg.application.exception;

import com.springtour.otg.domain.model.transaction.Transaction.State;

public class CannotCancelTransactionException extends
		AbstractCheckedApplicationExceptionWithStatusCode {

	public CannotCancelTransactionException(State state) {
		super("state given is : "+state.getName());
	}

	@Override
	public String getStatusCode() {
		   return StatusCode.INVALID_TXN_STATE;
	}

}
