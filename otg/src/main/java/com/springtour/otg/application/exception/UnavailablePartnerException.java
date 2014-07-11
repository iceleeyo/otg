package com.springtour.otg.application.exception;

import com.springtour.otg.application.exception.AbstractCheckedApplicationExceptionWithStatusCode;

public class UnavailablePartnerException extends AbstractCheckedApplicationExceptionWithStatusCode {
    
    public UnavailablePartnerException(String partnerId) {
        super("Partner id given=" + partnerId);
    }

    @Override
    public String getStatusCode() {
       return StatusCode.UNAVAILABLE_PARTNER;
    }
}
