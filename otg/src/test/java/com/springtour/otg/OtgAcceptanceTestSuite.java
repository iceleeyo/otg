package com.springtour.otg;

import org.junit.runner.*;
import org.junit.runners.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({ OtgMessagingIntegrationTestSuite.class,// 这里不把messaging测试加入到集成测试中，否则有可能因为流水线后面的step由于也在监听queue导致错误
		OtgPersistenceIntegrationTestSuite.class, CucumberAcceptanceTests.class })
public class OtgAcceptanceTestSuite {
}
