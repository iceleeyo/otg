package com.springtour.otg.infrastructure.logging;

import org.apache.log4j.*;
import org.jmock.*;
import org.junit.*;

import com.springtour.otg.domain.model.transaction.*;
import com.springtour.test.*;

public class SynchronousCheckingBatchNotifierImplUnitTests extends
		AbstractJMockUnitTests {
	private Logger logger = context.mock(Logger.class);
	private SynchronousCheckingBatchNotifierImpl target = new SynchronousCheckingBatchNotifierStub(
			logger);

	private Transaction transaction = new TransactionFixture().build();
	private String txnDescribe = transaction.getTransactionNo()
			+ ",state=REQUESTED";

	@Test
	public void logsError() throws Exception {
		final Exception e = new Exception("111");

		final String message = txnDescribe + ",error=" + e.getMessage();

		context.checking(new Expectations() {
			{
				oneOf(logger).error(message, e);
			}
		});

		target.notifyError(transaction, e);
	}

	@Test
	public void logsSpottingDead() throws Exception {
		final String message = txnDescribe + " is regardes as dead.";

		context.checking(new Expectations() {
			{
				oneOf(logger).info(message);
			}
		});

		target.notifySpottingDead(transaction);
	}

	@Test
	public void logsSpottingNewBorn() throws Exception {
		final String message = txnDescribe + " is regardes as new Born.";

		context.checking(new Expectations() {
			{
				oneOf(logger).info(message);
			}
		});

		target.notifySpottingNewBorn(transaction);
	}

	@Test
	public void logsSpottingUnlivelyAndBye() throws Exception {
		final String message = txnDescribe
				+ " is regardes as unlively and bye round.";

		context.checking(new Expectations() {
			{
				oneOf(logger).info(message);
			}
		});

		target.notifySpottingUnlivelyAndBye(transaction);
	}

	@Test
	public void logsToBeChecked() throws Exception {
		final String message = txnDescribe + " is regardes as to be checked.";

		context.checking(new Expectations() {
			{
				oneOf(logger).info(message);
			}
		});

		target.notifyToBeChecked(transaction);
	}

	public class SynchronousCheckingBatchNotifierStub extends
			SynchronousCheckingBatchNotifierImpl {

		private Logger logger;

		public SynchronousCheckingBatchNotifierStub(Logger logger) {
			this.logger = logger;
		}

		@Override
		protected Logger logger() {
			return logger;
		}
	}
}