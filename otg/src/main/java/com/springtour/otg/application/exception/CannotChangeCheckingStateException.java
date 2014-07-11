package com.springtour.otg.application.exception;

import com.springtour.otg.domain.model.transaction.*;

public class CannotChangeCheckingStateException extends
		AbstractCheckedApplicationExceptionWithStatusCode {
	private static final long serialVersionUID = 1L;

	public CannotChangeCheckingStateException(CheckingState current,
			CheckingState attempt) {
		super("current checking state[" + current + "] cannot be changed to ["
				+ attempt + "]");
	}

	@Override
	public String getStatusCode() {
		return StatusCode.CANNOT_CHANGE_CHECKING_STATE;
	}

}
