package com.springtour.otg.infrastructure.channel;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.springtour.otg.infrastructure.channel.alipay.AlipayExternalTransactionQueryObjectIntegrationTests;
import com.springtour.otg.infrastructure.channel.tenpay.TenpayExternalTransactionQueryObjectIntegrationTests;

@RunWith(Suite.class)
@Suite.SuiteClasses( {
		AlipayExternalTransactionQueryObjectIntegrationTests.class })
public class ExternalTransactionQueryObjectIntegrationTestSuite {

}
