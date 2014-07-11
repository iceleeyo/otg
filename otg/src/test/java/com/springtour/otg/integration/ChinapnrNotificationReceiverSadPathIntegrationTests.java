package com.springtour.otg.integration;

import com.springtour.otg.*;
import com.springtour.otg.application.exception.FakeNotificationException;
import com.springtour.otg.infrastructure.channel.NotificationHttpServletRequestAssembler;
import org.junit.Assert;

public class ChinapnrNotificationReceiverSadPathIntegrationTests extends AbstractNotificationReceiverSadPathIntegrationTests {

   

    public ChinapnrNotificationReceiverSadPathIntegrationTests() {
        super();
        super.setReceiverBeanName("otg.ChinapnrNotificationReceiver");
        super.setDbopBeanName("otg.ChinapnrGivesAll");
        super.setChannel("12");
        super.setFakeSignatureBeanName("otg.ChinapnrNotificationHttpServletRequestAssemblerImpl");
        super.setBadAmountBeanName("otg.ChinapnrNotificationHttpServletRequestAssemblerWithBadAmount");
        super.setBadTxnNoBeanName("otg.ChinapnrNotificationHttpServletRequestAssemblerWithBadTxnNo");
    }
}
