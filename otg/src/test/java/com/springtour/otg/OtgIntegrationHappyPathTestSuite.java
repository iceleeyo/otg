package com.springtour.otg;

import com.springtour.otg.integration.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({GenericRequestingHappyPathIntegrationTests.class, GenericRetrievingHappyPathIntegrationTests.class,
    AlipayRequestWithoutCardInfoHappyPathIntegrationTests.class,
    SpringCardRequestWithoutCardInfoHappyPathIntegrationTests.class,
    TenpayRequestWithoutCardInfoHappyPathIntegrationTests.class, SpringCardNotificationReceiverHappyPathIntegrationTests.class, TenpayNotificationReceiverHappyPathIntegrationTests.class, AlipayNotificationReceiverHappyPathIntegrationTests.class, ChinapnrNotificationReceiverHappyPathIntegrationTests.class})
public class OtgIntegrationHappyPathTestSuite {
}
