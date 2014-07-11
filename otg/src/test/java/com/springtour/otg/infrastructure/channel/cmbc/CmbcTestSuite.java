package com.springtour.otg.infrastructure.channel.cmbc;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( { EncryptionUnitTests.class,
		CmbcNotificationFactoryImplUnitTests.class,
		CmbcRequestUrlAssemblerUnitTests.class,
		CmbcTransactionNoGeneratorImplUnitTests.class})
public class CmbcTestSuite {
}
