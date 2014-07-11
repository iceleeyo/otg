package com.springtour.otg;

import org.junit.runner.*;
import org.junit.runners.*;

import com.springtour.otg.application.impl.*;

@RunWith(Suite.class)
@Suite.SuiteClasses( { Bill99NotificationServiceImplTransactionalTests.class,
		NotificationServiceImplTransactionalTests.class,
		CheckTransactionServiceImplTransactionalTests.class,
		TransactingServiceImplTransactionalTests.class})
public class TransactionalIntegrationTestSuite {
}
