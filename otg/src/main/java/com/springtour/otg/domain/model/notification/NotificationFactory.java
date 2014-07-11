package com.springtour.otg.domain.model.notification;

import com.springtour.otg.application.exception.*;
import java.util.Map;

public interface NotificationFactory {

    Notification make(String channelId, Map<String, String> aOriginalNotification) throws UnavailableChannelException, UnavailableCurrencyException, CannotLaunchSecurityProcedureException, FakeNotificationException;
}
