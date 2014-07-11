package com.springtour.otg.integration;

public class ChinapnrNotificationReceiverHappyPathIntegrationTests extends AbstractNotificationReceiverHappyPathIntegrationTests {

   

    public ChinapnrNotificationReceiverHappyPathIntegrationTests() {
        super();
        super.setReceiverBeanName("otg.ChinapnrNotificationReceiver");
        super.setDbopBeanName("otg.ChinapnrGivesAll");
        super.setChannel("12");
        super.setChargedSuccess("otg.ChinapnrNotificationHttpServletRequestAssemblerImpl");
        super.setChargedFailure("otg.ChinapnrNotificationHttpServletRequestAssemblerWithChargedFailure");
    }
}
