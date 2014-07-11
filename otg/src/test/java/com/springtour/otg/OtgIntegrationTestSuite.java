package com.springtour.otg;

import org.junit.runner.*;
import org.junit.runners.*;

import com.springtour.otg.infrastructure.channel.ChannelIntegrationTestSuite;
import com.springtour.otg.infrastructure.channel.ExternalTransactionQueryObjectIntegrationTestSuite;
import com.springtour.otg.integration.*;

@RunWith(Suite.class)
@Suite.SuiteClasses( { ConfigurationsIntegrationTests.class,
		TransactionalIntegrationTestSuite.class,
		OtgPersistenceIntegrationTestSuite.class,
		ExternalTransactionQueryObjectIntegrationTestSuite.class,
		ChannelIntegrationTestSuite.class})
public class OtgIntegrationTestSuite {
}
