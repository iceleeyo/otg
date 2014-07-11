/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.interfaces.transacting.web;

import com.springtour.otg.application.ApplicationEvents;
import com.springtour.otg.application.NotificationService;
import com.springtour.otg.domain.model.notification.Notification;
import com.springtour.otg.infrastructure.channel.NotificationAcknowledger;
import com.springtour.otg.infrastructure.channel.bill99.ResolveResponseData;
import com.springtour.otg.infrastructure.logging.LoggerFactory;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 *
 * @author Future
 */
public class Bill99NotificationReceiverImpl extends MultiActionController implements NotificationReceiver {

    private NotificationAcknowledger acknowledger;
    private NotificationService notificationService;
    private String channelId;
    private ApplicationEvents applicationEvents;

    @Override
    public void receive(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
            InputStream is = request.getInputStream();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] receiveBuffer = new byte[2048];
            int readBytesSize = is.read(receiveBuffer);
            while (readBytesSize != -1) {
                bos.write(receiveBuffer, 0, readBytesSize);
                readBytesSize = is.read(receiveBuffer);
            }
            String reqData = new String(bos.toByteArray(), "UTF-8");
            Map<String, String> aOriginalNotification = ResolveResponseData.resolvePurchaseTr3PesponseData(reqData);
            Notification notification = notificationService.receive(channelId, aOriginalNotification);
            applicationEvents.notificationWasReceived(notification);
            ack(request, response, notification);
        } catch (Exception ex) {
            LoggerFactory.getLogger().error(ex.getMessage(), ex);
        }
    }

    private void ack(HttpServletRequest request, HttpServletResponse response, Notification notification) throws IOException {
        if (notification != null) {
            acknowledger.acknowledge(request, response, notification);
        }
    }

    public void setApplicationEvents(ApplicationEvents applicationEvents) {
        this.applicationEvents = applicationEvents;
    }

    public void setAcknowledger(NotificationAcknowledger acknowledger) {
        this.acknowledger = acknowledger;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public void setNotificationService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Override
    public String getChannel() {
        return this.channelId;
    }
}
