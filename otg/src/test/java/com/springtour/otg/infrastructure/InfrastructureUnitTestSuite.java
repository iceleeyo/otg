package com.springtour.otg.infrastructure;

import org.junit.runner.*;
import org.junit.runners.*;

import com.springtour.otg.infrastructure.channel.*;
import com.springtour.otg.infrastructure.logging.*;
import com.springtour.otg.infrastructure.messaging.*;
import com.springtour.otg.infrastructure.payment.*;
import com.springtour.otg.infrastructure.persistence.*;
import com.springtour.otg.infrastructure.time.*;

@RunWith(Suite.class)
@Suite.SuiteClasses( { TransactionCriteriaMockTests.class,
		LoggingAdvicesUnitTests.class, Slf4jLoggingSupportImplUnitTests.class,
		SynchronousCheckingBatchNotifierImplUnitTests.class,
		ExternalMakePaymentServiceImplMockTests.class,
		PaymentDispatcherMockTests.class, ChannelUnitTestSuite.class,
		MessagingUnitTestSuite.class, DateUtilsUnitTests.class })
public class InfrastructureUnitTestSuite {
}
