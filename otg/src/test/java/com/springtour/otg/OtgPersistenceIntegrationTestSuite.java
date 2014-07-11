package com.springtour.otg;

import org.junit.runner.*;
import org.junit.runners.*;

import com.springtour.otg.infrastructure.persistence.ibatis.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({ IBatisChannelRepositoryImplIntegrationTests.class,
		IBatisPartnerRepositoryImplIntegrationTests.class,
		IBatisMerchantRepositoryImplIntegrationTests.class,
		IBatisNotificationRepositoryImplIntegrationTests.class,
		IBatisTransactionRepositoryImplPersistenceTests.class })
public class OtgPersistenceIntegrationTestSuite {
}
