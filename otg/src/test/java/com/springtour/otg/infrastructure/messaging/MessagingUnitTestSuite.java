package com.springtour.otg.infrastructure.messaging;

import org.junit.runner.*;
import org.junit.runners.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({ CheckingConsumerUnitTests.class,
		JmsTransactionCheckingEventsImplUnitTests.class,
		SpottingDeadConsumerUnitTests.class,
		SpottingInvalidConsumerUnitTests.class})
public class MessagingUnitTestSuite {
}
