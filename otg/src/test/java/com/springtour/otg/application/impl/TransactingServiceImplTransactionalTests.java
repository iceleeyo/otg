package com.springtour.otg.application.impl;

import com.springtour.otg.application.TransactingService;
import com.springtour.test.AbstractTransactionalIntegrationTests;

public class TransactingServiceImplTransactionalTests extends
		AbstractTransactionalIntegrationTests {

	private TransactingService target;

	@Override
	public void onSetUp() {
		super.onSetUp();
		initTarget();
	}

	private void initTarget() {
		target = (TransactingService) super.getApplicationContext().getBean(
				"otg.TransactingServiceImplTarget");
	}

	public void testTransactingServiceConclude() {
		try {
			target.conclude(null);
		} catch (Exception e) {

		}
		transactionalShouldBeAppliedTo("conclude");
	}

	public void testTransactingServiceNewTransaction() {
		try {
			target.newTransaction(null, null, null, null, null, null);
		} catch (Exception e) {

		}
		transactionalShouldBeAppliedTo("newTransaction");
	}

	public void testTransactingServiceForCancelTxn() {
		try {
			target.cancelTransaction(null);
		} catch (Exception e) {

		}
		transactionalShouldBeAppliedTo("cancelTransaction");
	}

	public void testTransactingServiceHandle() {
		try {
			target.handle(null);
		} catch (Exception e) {

		}
		transactionalShouldBeAppliedTo("handle");
	}

	@Override
	protected Class<?> targetClass() {
		return TransactingService.class;
	}

}
