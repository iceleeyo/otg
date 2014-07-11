package com.springtour.otg.application;

import com.springtour.otg.domain.model.notification.Notification;
import com.springtour.otg.domain.model.notification.SynchronizingMethod;
import com.springtour.otg.application.exception.*;
import com.springtour.otg.domain.model.notification.Charge;
import com.springtour.otg.domain.model.transaction.TransactionNo;
import java.util.Map;

public interface NotificationService {

    Notification receive(String channelId, Map<String, String> aOriginalNotification) throws UnavailableChannelException, UnavailableCurrencyException, CannotLaunchSecurityProcedureException, FakeNotificationException;

    Notification retrieve(TransactionNo transactionNo, String extTxnNo, Charge charged, SynchronizingMethod retrieveType);
}
