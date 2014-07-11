package com.springtour.otg.application.exception;

import com.springtour.otg.application.exception.AbstractCheckedApplicationExceptionWithStatusCode;

/**
 * 未找到商户异常
 * 
 * @author Hippoom
 * 
 */
public class UnavailablePaymentApplicationException extends AbstractCheckedApplicationExceptionWithStatusCode {
	private static final long serialVersionUID = 1L;

	public UnavailablePaymentApplicationException(String application) {
		super(new StringBuilder("unavailable payment app:[application:")
				.append(application).append("]").toString());
	}

    @Override
    public String getStatusCode() {
       return StatusCode.UNAVAILABLE_PAYMENT_APP;
    }

}
