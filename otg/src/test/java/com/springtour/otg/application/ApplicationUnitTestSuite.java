package com.springtour.otg.application;

import org.junit.runner.*;
import org.junit.runners.*;

import com.springtour.otg.application.exception.*;
import com.springtour.otg.application.impl.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({ ApplicatonExceptionMockTests.class,
		CheckingBatchUnitTests.class,
		CheckTransactionServiceImplUnitTests.class,
		ResponseProcedureMockTests.class,
		NotificationServiceImplMockTests.class,
		MerchantAdminServiceImplMockTests.class,
		TransactingServiceDecoratorUnitTests.class,
		TransactingServiceImplUnitTests.class })
public class ApplicationUnitTestSuite {
}
