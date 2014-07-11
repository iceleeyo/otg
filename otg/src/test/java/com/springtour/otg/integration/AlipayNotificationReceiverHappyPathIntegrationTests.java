package com.springtour.otg.integration;

public class AlipayNotificationReceiverHappyPathIntegrationTests extends AbstractNotificationReceiverHappyPathIntegrationTests {

   

    public AlipayNotificationReceiverHappyPathIntegrationTests() {
        super();
        super.setReceiverBeanName("otg.b2c.AlipayNotificationReceiver");
        super.setDbopBeanName("otg.AlipayGivesAll");
        super.setChannel("7");
        super.setChargedSuccess("otg.AlipayNotificationHttpServletRequestAssemblerImpl");
        super.setChargedFailure("otg.AlipayNotificationHttpServletRequestAssemblerWithChargedFailure");
    }
}
