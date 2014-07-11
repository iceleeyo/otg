package com.springtour.otg;

import com.springtour.otg.integration.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({GenericRetrievingSadPathIntegrationTests.class, OtgCurrencySadPathTests.class,
    OtgApplicationSadPathTests.class, OtgRequestWithoutCardInfoSadPathTests.class, SpringCardNotificationReceiverSadPathIntegrationTests.class, TenpayNotificationReceiverSadPathIntegrationTests.class, AlipayNotificationReceiverSadPathIntegrationTests.class, ChinapnrNotificationReceiverSadPathIntegrationTests.class})
public class OtgIntegrationSadPathTestSuite {
}
