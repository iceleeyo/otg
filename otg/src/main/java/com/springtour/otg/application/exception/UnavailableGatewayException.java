package com.springtour.otg.application.exception;

public class UnavailableGatewayException extends AbstractCheckedApplicationExceptionWithStatusCode {

    public UnavailableGatewayException(String channelId, String gateway) {
        super("Channel id given=" + channelId + ",gateway code given=" + gateway);
    }

    @Override
    public String getStatusCode() {
        return StatusCode.UNAVAILABLE_GATEWAY;
    }
}
