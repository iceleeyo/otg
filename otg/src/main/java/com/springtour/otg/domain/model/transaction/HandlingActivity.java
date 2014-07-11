package com.springtour.otg.domain.model.transaction;

import java.util.Date;

import com.springtour.otg.domain.model.notification.Notification;

public class HandlingActivity {

    private Notification notification;
    private Date whenHandled;

    /**
     * @param notification
     * @param whenHandled
     */
    public HandlingActivity(Notification notification, Date whenHandled) {
        this.notification = notification;
        this.whenHandled = whenHandled;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public Date getWhenHandled() {
        return whenHandled;
    }

    public void setWhenHandled(Date whenHandled) {
        this.whenHandled = whenHandled;
    }

    /**
     * 
     */
    public HandlingActivity() {
    }
}
