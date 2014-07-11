package com.springtour.otg.integration;

import com.springtour.otg.OtgIntegrationTestsConstants;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

public abstract class AbstractOtgIntegrationTests extends
		AbstractDependencyInjectionSpringContextTests {

	@Override
	protected String[] getConfigLocations() {
		return OtgIntegrationTestsConstants.APPLICATION_CONTEXT;
	}

	public AbstractOtgIntegrationTests() {
		super();
		//setAutowireMode(AUTOWIRE_NO);

	}

}
