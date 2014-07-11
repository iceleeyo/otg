package com.springtour.otg.application.impl;

import org.junit.*;

import com.springtour.otg.application.*;
import com.springtour.test.*;

public class NotificationServiceImplTransactionalTests extends
		AbstractTransactionalIntegrationTests {

	private NotificationService target;

	@Override
	public void onSetUp() {
		super.onSetUp();
		initTarget();
	}

	private void initTarget() {
		target = (NotificationService) super.getApplicationContext().getBean(
				"otg.NotificationServiceImpl");
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
	
	@Test
	public void testTransactionalAppliedWhenRetrieve() throws Exception {
		try {
			target.retrieve(null, null, null, null);
		} catch (Exception ex) {
			// e = ex;
		}
		transactionalShouldBeAppliedTo("retrieve");
	}

	@Override
	protected Class<?> targetClass() {
		return NotificationService.class;
	}
}