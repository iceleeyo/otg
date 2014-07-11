package com.springtour.otg.application.exception;

@SuppressWarnings("serial")
public class SimpleApplicationException extends
		AbstractCheckedApplicationException {

	public SimpleApplicationException(String message) {
		super(message);
	}

}