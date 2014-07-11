package com.springtour.otg.infrastructure.channel.ccb;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( { CcbNotificationValidatorImplUnitTests.class,
		CcbRequestUrlAssemblerUnitTests.class,
		CcbInstallmentRequestUrlAssemblerUnitTests.class })
public class CcbTestSuite {
}
