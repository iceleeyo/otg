package com.springtour.otg.integration;

public class TenpayNotificationReceiverSadPathIntegrationTests extends AbstractNotificationReceiverSadPathIntegrationTests {

    public TenpayNotificationReceiverSadPathIntegrationTests() {
        super();
        super.setReceiverBeanName("otg.TenpayNotificationReceiver");
        super.setDbopBeanName("otg.TenpayGivesAll");
        super.setChannel("5");
        super.setFakeSignatureBeanName("otg.TenpayNotificationHttpServletRequestAssemblerWithFakeSignature");
        super.setBadAmountBeanName("otg.TenpayNotificationHttpServletRequestAssemblerWithBadAmount");
        super.setBadTxnNoBeanName("otg.TenpayNotificationHttpServletRequestAssemblerWithBadTxnNo");
    }
}
