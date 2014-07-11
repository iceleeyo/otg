package com.springtour.otg.infrastructure.channel.bill99;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({GenerateRequestDataTests.class,
    ResolveResponseDataTest.class,Bill99KeyManagerUnitTests.class})
public class Bill99UnitTestSuite {
}
