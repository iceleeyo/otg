package com.springtour.otg.interfaces;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.springtour.otg.interfaces.transacting.facade.internal.AutoCheckingBatchFacadeImplUnitTests;
import com.springtour.otg.interfaces.transacting.facade.internal.RequestHandlerFacadeImplUnitTests;
import com.springtour.otg.interfaces.transacting.facade.internal.TransactingServiceFacadeImplMockTests;

@RunWith(Suite.class)
@Suite.SuiteClasses({ AutoCheckingBatchFacadeImplUnitTests.class,
		TransactingServiceFacadeImplMockTests.class,
		RequestHandlerFacadeImplUnitTests.class })
public class InterfacesUnitTestSuite {
}
