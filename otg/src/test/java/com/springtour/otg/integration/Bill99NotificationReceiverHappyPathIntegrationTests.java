/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.integration;

/**
 *
 * @author Future
 */
public class Bill99NotificationReceiverHappyPathIntegrationTests extends AbstractNotificationReceiverHappyPathIntegrationTests {

    public Bill99NotificationReceiverHappyPathIntegrationTests() {
        super();
        super.setReceiverBeanName("otg.Bill99NotificationReceiver");
        super.setDbopBeanName("otg.Bill99GivesAll");
        super.setChannel("15");
        super.setChargedSuccess("otg.Bill99NotificationHttpServletRequestAssemblerImpl");
        super.setChargedFailure("otg.Bill99NotificationHttpServletRequestAssemblerWithChargedFailure");
    }
}
