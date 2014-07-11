package com.springtour.otg.application.exception;


public class CannotRetrieveSynchronizingMethodException extends AbstractCheckedApplicationExceptionWithStatusCode {

	public CannotRetrieveSynchronizingMethodException(String retrieveType) {
		super("SynchronizingMethod =" + retrieveType);
	}

	@Override
	public String getStatusCode() {
		return StatusCode.CANNOT_RETRIEVE_SYNCHRONIZING_METHOD;
	}

}
