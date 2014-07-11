package com.springtour.otg;

import org.junit.runner.*;
import org.junit.runners.*;

import com.springtour.otg.infrastructure.messaging.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({ JmsTransactionCheckingEventsImplIntegrationTests.class })
public class OtgMessagingIntegrationTestSuite {
}
