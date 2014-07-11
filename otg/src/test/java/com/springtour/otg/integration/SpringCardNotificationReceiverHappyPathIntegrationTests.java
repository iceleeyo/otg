package com.springtour.otg.integration;

public class SpringCardNotificationReceiverHappyPathIntegrationTests extends AbstractNotificationReceiverHappyPathIntegrationTests {

   

    public SpringCardNotificationReceiverHappyPathIntegrationTests() {
        super();
        super.setReceiverBeanName("otg.SpringCardNotificationReceiver");
        super.setDbopBeanName("otg.SpringCardGivesAll");
        super.setChannel("2");
        super.setChargedSuccess("otg.SpringCardNotificationHttpServletRequestAssemblerImpl");
        super.setChargedFailure("otg.SpringCardNotificationHttpServletRequestAssemblerWithChargedFailure");
    }
}
