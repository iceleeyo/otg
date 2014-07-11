package com.springtour.otg.application.exception;

import com.springtour.otg.domain.shared.WrapCurrency;

public class UnavailableCurrencyException extends AbstractCheckedApplicationExceptionWithStatusCode {

    public UnavailableCurrencyException(String channelId, WrapCurrency currency) {
        super("Channel id given=" + channelId + ",currencyCode=" + currency);
    }

    public UnavailableCurrencyException(String currency) {
        super("CurrencyCode=" + currency);
    }

    @Override
    public String getStatusCode() {
        return StatusCode.UNAVAILABLE_CURRENCY;
    }
}
