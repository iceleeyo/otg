package com.springtour.otg.application.exception;

import com.springtour.otg.application.exception.*;

public class CannotNotifyNotificationReceivedException extends
		AbstractCheckedApplicationException {

	private static final long serialVersionUID = 1L;

	public CannotNotifyNotificationReceivedException(String notificationSeq,
			Throwable cause) {
		super(notificationSeq, cause);
	}

}
