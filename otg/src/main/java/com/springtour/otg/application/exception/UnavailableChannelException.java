package com.springtour.otg.application.exception;

public class UnavailableChannelException extends AbstractCheckedApplicationExceptionWithStatusCode {

    public UnavailableChannelException(String channelId) {
        super("Channel id given=" + channelId);
    }

    public UnavailableChannelException(String partnerId, String channelId) {
        super("Partner id given=" + partnerId + ",channel id given=" + channelId);
    }

    @Override
    public String getStatusCode() {
        return StatusCode.UNAVAILABLE_CHANNEL;
    }
}
