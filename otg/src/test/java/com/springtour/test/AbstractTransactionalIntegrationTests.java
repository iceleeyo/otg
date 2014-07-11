package com.springtour.test;

import com.springtour.otg.integration.*;
import com.springtour.test.*;

public abstract class AbstractTransactionalIntegrationTests extends
		AbstractOtgIntegrationTests {

	protected SpringTransactionTestAppender transactionTestAppender;

	public AbstractTransactionalIntegrationTests() {
		super();
	}

	protected void transactionalShouldBeAppliedTo(String method) {
		assertTrue(transactionTestAppender.isTransactionOpened(targetClass(),
				method));
		assertTrue(transactionTestAppender.isTransactionRollbacked(
				targetClass(), method));
	}

	abstract protected Class<?> targetClass();

	@Override
	public void onTearDown() {
		transactionTestAppender.close();
	}

	@Override
	public void onSetUp() {
		transactionTestAppender = new SpringTransactionTestAppender();

	}

}