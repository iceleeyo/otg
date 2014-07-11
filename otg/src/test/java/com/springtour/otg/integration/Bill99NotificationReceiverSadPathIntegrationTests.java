/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.integration;

/**
 *
 * @author Future
 */
public class Bill99NotificationReceiverSadPathIntegrationTests extends AbstractNotificationReceiverSadPathIntegrationTests {

    public Bill99NotificationReceiverSadPathIntegrationTests() {
        super();
        super.setReceiverBeanName("otg.Bill99NotificationReceiver");
        super.setDbopBeanName("otg.Bill99GivesAll");
        super.setChannel("15");
        super.setFakeSignatureBeanName("otg.Bill99NotificationHttpServletRequestAssemblerImpl");
        super.setBadAmountBeanName("otg.Bill99NotificationHttpServletRequestAssemblerWithBadAmount");
        super.setBadTxnNoBeanName("otg.Bill99NotificationHttpServletRequestAssemblerWithBadTxnNo");
    }
}
