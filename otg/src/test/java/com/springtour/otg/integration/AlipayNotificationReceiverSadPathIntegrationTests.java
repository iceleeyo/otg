package com.springtour.otg.integration;

public class AlipayNotificationReceiverSadPathIntegrationTests extends AbstractNotificationReceiverSadPathIntegrationTests {

    public AlipayNotificationReceiverSadPathIntegrationTests() {
        super();
        super.setReceiverBeanName("otg.b2c.AlipayNotificationReceiver");
        super.setDbopBeanName("otg.AlipayGivesAll");
        super.setChannel("7");
        super.setFakeSignatureBeanName("otg.AlipayNotificationHttpServletRequestAssemblerWithFakeSignature");
        super.setBadAmountBeanName("otg.AlipayNotificationHttpServletRequestAssemblerWithBadAmount");
        super.setBadTxnNoBeanName("otg.AlipayNotificationHttpServletRequestAssemblerWithBadTxnNo");
    }
}
