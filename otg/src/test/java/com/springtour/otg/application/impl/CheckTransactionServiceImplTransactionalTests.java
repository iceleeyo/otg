package com.springtour.otg.application.impl;

import org.junit.*;

import com.springtour.otg.application.*;
import com.springtour.test.*;

public class CheckTransactionServiceImplTransactionalTests extends
		AbstractTransactionalIntegrationTests {

	private CheckTransactionService target;

	@Override
	public void onSetUp() {
		super.onSetUp();
		initTarget();
	}

	private void initTarget() {
		target = (CheckTransactionService) super.getApplicationContext()
				.getBean("otg.CheckTransactionServiceImpl");
	}

	@Test
	public void testTransactionalAppliedWhenCheck() throws Exception {
		try {
			target.check(null);
		} catch (Exception ex) {
			// e = ex;
		}
		transactionalShouldBeAppliedTo("check");
	}
	
	@Test
	public void testTransactionalAppliedWhenMarkAsDead() throws Exception {
		try {
			target.retrieveOrMarkAsDead(null);
		} catch (Exception ex) {
			// e = ex;
		}
		transactionalShouldBeAppliedTo("retrieveOrMarkAsDead");
	}

	@Override
	protected Class<?> targetClass() {
		return CheckTransactionService.class;
	}
}