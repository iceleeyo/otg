package com.springtour.otg.infrastructure.channel;

import com.springtour.otg.domain.model.notification.Notification;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface NotificationAcknowledger {

    void acknowledge(HttpServletRequest request, HttpServletResponse response, Notification notification)
            throws IOException;
}
