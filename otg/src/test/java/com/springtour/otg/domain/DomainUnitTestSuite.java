package com.springtour.otg.domain;

import org.junit.runner.*;
import org.junit.runners.*;

import com.springtour.otg.domain.model.channel.*;
import com.springtour.otg.domain.model.notification.NotificationUnitTests;
import com.springtour.otg.domain.model.partner.*;
import com.springtour.otg.domain.model.transaction.*;
import com.springtour.otg.domain.shared.*;

@RunWith(Suite.class)
@Suite.SuiteClasses( { ChannelMockTests.class, GatewaysMockTests.class,
		PartnerMockTests.class, TransactionUnitTestSuite.class,
		IdentityTypeMockTests.class, WrapCurrencyMockTests.class,
		MoneyMockTests.class, NotificationUnitTests.class })
public class DomainUnitTestSuite {
}
