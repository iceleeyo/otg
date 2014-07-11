package com.springtour.otg.integration;

public class SpringCardNotificationReceiverSadPathIntegrationTests extends AbstractNotificationReceiverSadPathIntegrationTests {

    public SpringCardNotificationReceiverSadPathIntegrationTests() {
        super();
        super.setReceiverBeanName("otg.SpringCardNotificationReceiver");
        super.setDbopBeanName("otg.SpringCardGivesAll");
        super.setChannel("2");
        super.setFakeSignatureBeanName("otg.SpringCardNotificationHttpServletRequestAssemblerWithFakeSignature");
        super.setBadAmountBeanName("otg.SpringCardNotificationHttpServletRequestAssemblerWithBadAmount");
        super.setBadTxnNoBeanName("otg.SpringCardNotificationHttpServletRequestAssemblerWithBadTxnNo");
    }
}
