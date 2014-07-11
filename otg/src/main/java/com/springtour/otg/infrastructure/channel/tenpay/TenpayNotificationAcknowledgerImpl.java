/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.infrastructure.channel.tenpay;

import com.springtour.otg.domain.model.notification.Notification;
import com.springtour.otg.infrastructure.channel.NotificationAcknowledger;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author 006874
 */
public class TenpayNotificationAcknowledgerImpl implements NotificationAcknowledger {

    @Override
    public void acknowledge(HttpServletRequest request, HttpServletResponse response, Notification notification) throws IOException {
        response.getWriter().write(TenpayConstants.CONFIRMATION_FLAG);
    }
}
