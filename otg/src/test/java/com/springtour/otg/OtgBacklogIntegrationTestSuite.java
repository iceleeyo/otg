/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg;

import com.springtour.otg.integration.OtgAlipayNotificationSadPathTests;
import com.springtour.otg.integration.OtgAmountSadPathTests;
import com.springtour.otg.integration.OtgChannelSadPathTests;
import com.springtour.otg.integration.OtgGatewaySadPathTests;
import com.springtour.otg.integration.OtgOrderInfoSadPathTests;
import com.springtour.otg.integration.OtgOrganizationSadPathTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author Future
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({OtgAlipayNotificationSadPathTests.class, OtgAmountSadPathTests.class,
    OtgChannelSadPathTests.class, OtgGatewaySadPathTests.class, OtgOrderInfoSadPathTests.class,
    OtgOrganizationSadPathTests.class})
public class OtgBacklogIntegrationTestSuite {
}
