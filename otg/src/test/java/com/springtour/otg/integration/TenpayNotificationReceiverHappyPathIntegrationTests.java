package com.springtour.otg.integration;

public class TenpayNotificationReceiverHappyPathIntegrationTests extends AbstractNotificationReceiverHappyPathIntegrationTests {

   

    public TenpayNotificationReceiverHappyPathIntegrationTests() {
        super();
        super.setReceiverBeanName("otg.TenpayNotificationReceiver");
        super.setDbopBeanName("otg.TenpayGivesAll");
        super.setChannel("5");
        super.setChargedSuccess("otg.TenpayNotificationHttpServletRequestAssemblerImpl");
        super.setChargedFailure("otg.TenpayNotificationHttpServletRequestAssemblerWithChargedFailure");
    }
}
