package com.springtour.otg.application.impl;

import org.junit.Test;

import com.springtour.otg.application.NotificationService;
import com.springtour.test.AbstractTransactionalIntegrationTests;

public class Bill99NotificationServiceImplTransactionalTests extends
		AbstractTransactionalIntegrationTests {

	private NotificationService target;

	@Override
	public void onSetUp() {
		super.onSetUp();
		initTarget();
	}

	private void initTarget() {
		target = (NotificationService) super.getApplicationContext().getBean(
				"otg.Bill99NotificationServiceImpl");
	}

	@Test
	public void testTransactionalAppliedWhenReceive() throws Exception {
		try {
			target.receive(null, null);
		} catch (Exception ex) {
			// e = ex;
		}
		transactionalShouldBeAppliedTo("receive");
	}

	@Override
	protected Class<?> targetClass() {
		return NotificationService.class;
	}
}
