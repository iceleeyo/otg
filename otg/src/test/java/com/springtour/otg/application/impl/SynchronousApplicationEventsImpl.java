package com.springtour.otg.application.impl;

import java.util.Map;
import com.springtour.otg.TestEngine;
import com.springtour.otg.application.ApplicationEvents;
import com.springtour.otg.domain.model.notification.Notification;

public class SynchronousApplicationEventsImpl implements ApplicationEvents {

    private TestEngine testEngine;

    

    @Override
    public void notificationWasReceived(Notification notification) {
        //testEngine.setNotificationSequence(notification.getSequence());
    }

    public void setTestEngine(TestEngine testEngine) {
        this.testEngine = testEngine;
    }
}
