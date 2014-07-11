package com.springtour.otg.infrastructure.channel.bill99pos;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( { Bill99PosRequestAdapterUnitTests.class,
		Bill99PosNotificationAcknowledgerImplUnitTests.class,
		Bill99PosNotificationValidatorImplUnitTests.class,
		Bill99PosNotificationFactoryImplUnitTests.class})
public class Bill99PosTestSuite {
}
