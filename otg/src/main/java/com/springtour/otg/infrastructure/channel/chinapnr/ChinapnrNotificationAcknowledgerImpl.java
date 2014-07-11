/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.infrastructure.channel.chinapnr;

import com.springtour.otg.domain.model.notification.Notification;
import com.springtour.otg.infrastructure.channel.NotificationAcknowledger;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author 006874
 */
public class ChinapnrNotificationAcknowledgerImpl implements NotificationAcknowledger {

    @Override
    public void acknowledge(HttpServletRequest request, HttpServletResponse response, Notification notification) throws IOException {
        if (notification == null) {
            return;
        }
        String transactionNo = notification.getTxnNo().getNumber();
        String confirmationFlag = ChinapnrConstants.CONFIRMATION_FLAG_PREFIX + transactionNo;
        response.getWriter().write(confirmationFlag);
    }
}
