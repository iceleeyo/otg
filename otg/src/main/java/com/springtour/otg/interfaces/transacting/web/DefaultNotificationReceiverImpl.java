package com.springtour.otg.interfaces.transacting.web;

import com.springtour.otg.application.ApplicationEvents;
import com.springtour.otg.infrastructure.channel.NotificationAcknowledger;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.springtour.otg.application.NotificationService;
import com.springtour.otg.domain.model.notification.Notification;
import com.springtour.otg.infrastructure.logging.LoggerFactory;

public class DefaultNotificationReceiverImpl extends MultiActionController implements NotificationReceiver {

    private NotificationAcknowledger acknowledger;
    private NotificationService notificationService;
    private ApplicationEvents applicationEvents;
    private String channelId;

    @Override
    public void receive(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Notification notification = null;
        try {
            Map<String, String> aOriginalNotification = sortParametersFromRequest(request);
            LoggerFactory.getLogger().info("Raw notification received:" + aOriginalNotification.toString());
            notification = notificationService.receive(channelId, aOriginalNotification);
            applicationEvents.notificationWasReceived(notification);
            ack(request, response, notification);
        } catch (Exception e) {
            LoggerFactory.getLogger().error(e.getMessage(), e);
        }

    }

    private void ack(HttpServletRequest request, HttpServletResponse response, Notification notification) throws IOException {
        if (notification != null && acknowledger != null) {
            acknowledger.acknowledge(request, response, notification);
        }
    }

    private Map sortParametersFromRequest(HttpServletRequest request) throws UnsupportedEncodingException {
    	LoggerFactory.getLogger().info("CharacterEncoding:"+request.getCharacterEncoding());
    	Map<String, String[]> original = request.getParameterMap();
        SortedMap<String, String> sorted = new TreeMap<String, String>();
        Iterator<String> itUnsorted = original.keySet().iterator();
        while (itUnsorted.hasNext()) {
            String k = (String) itUnsorted.next();
            String v = ((String[]) original.get(k))[0];
            if (null != v) {
                v = v.trim();
                sorted.put(k, v);
            } else {
                sorted.put(k, "");
            }
        }
        return sorted;
    }

    public void setNotificationService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    public void setAcknowledger(NotificationAcknowledger acknowledger) {
        this.acknowledger = acknowledger;
    }

    public void setApplicationEvents(ApplicationEvents applicationEvents) {
        this.applicationEvents = applicationEvents;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    @Override
    public String getChannel() {
        return channelId;
    }
}
