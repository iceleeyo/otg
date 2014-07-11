/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.infrastructure.channel.alipay;

import com.springtour.otg.domain.model.notification.Notification;
import com.springtour.otg.infrastructure.channel.NotificationAcknowledger;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Future
 */
public class AlipayNotificationAcknowledgerImpl implements NotificationAcknowledger{
    private AlipayHandlingService alipayHandlingService;

    @Override
    public void acknowledge(HttpServletRequest request, HttpServletResponse response, Notification notification) throws IOException {
        PrintWriter out = response.getWriter();
        out.println(alipayHandlingService.confirmationFlag());
        out.flush();
        out.close();
    }

    public void setAlipayHandlingService(AlipayHandlingService alipayHandlingService) {
        this.alipayHandlingService = alipayHandlingService;
    }
    
}
