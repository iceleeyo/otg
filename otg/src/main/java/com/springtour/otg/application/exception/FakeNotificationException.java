package com.springtour.otg.application.exception;

import java.util.Map;

public class FakeNotificationException extends AbstractCheckedApplicationException {

    public FakeNotificationException(String channel, Map<String, String> aOriginalNotification) {
        super("Channel=" + channel + "notification attempt="
                + aOriginalNotification.toString());
    }
}
