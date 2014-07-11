package com.springtour.otg.infrastructure.channel;

import org.junit.runner.*;
import org.junit.runners.*;

import com.springtour.otg.infrastructure.channel.alipay.*;
import com.springtour.otg.infrastructure.channel.bill99.*;
import com.springtour.otg.infrastructure.channel.bill99gateway.*;
import com.springtour.otg.infrastructure.channel.bill99pos.Bill99PosTestSuite;
import com.springtour.otg.infrastructure.channel.ccb.CcbTestSuite;
import com.springtour.otg.infrastructure.channel.cmbc.CmbcTestSuite;
import com.springtour.otg.infrastructure.channel.icbc.IcbcTestSuite;
import com.springtour.otg.infrastructure.channel.tenpay.*;
import com.springtour.otg.infrastructure.channel.upop.UpopTestSuite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ StatusCodeTranslatorMockTests.class,
		AlipayHandlingServiceImplTests.class,
		TenpayNotificationValidatorImplUnitTests.class,
		Bill99UnitTestSuite.class, Bill99GatewayTestSuite.class,
		ChannelDispatcherUnitTests.class,
		TransactionCheckerImplUnitTests.class, CmbcTestSuite.class,
		CommonTransactionNoGeneratorImplUnitTests.class, CcbTestSuite.class,
		IcbcTestSuite.class,
		Bill99PosTestSuite.class,
		UpopTestSuite.class })

public class ChannelUnitTestSuite {
}
