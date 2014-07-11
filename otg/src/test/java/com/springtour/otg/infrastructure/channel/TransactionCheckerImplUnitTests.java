package com.springtour.otg.infrastructure.channel;

import static org.junit.Assert.*;

import java.math.*;
import java.util.*;

import org.apache.log4j.*;
import org.jmock.*;
import org.junit.*;

import com.springtour.otg.domain.model.notification.*;
import com.springtour.otg.domain.model.transaction.*;
import com.springtour.otg.domain.shared.*;
import com.springtour.test.*;

public class TransactionCheckerImplUnitTests extends AbstractJMockUnitTests {

	private static final String EXT_TXN_NO = "ext txn no";
	private TransactionCheckerImpl target = new TransactionCheckerStub();
	private ExternalTransactionQueryResult checker = context
			.mock(ExternalTransactionQueryResult.class);
	private ExternalTransactionQueryObject checkerFactory = context
			.mock(ExternalTransactionQueryObject.class);
	private Transaction transaction = new TransactionFixture().build();

	private Logger logger = context.mock(Logger.class);

	@Before
	public void injects() {
		target.setExternalTransactionQueryObject(checkerFactory);
	}

	@Test
	public void returnsValidWhenCheckGivenAConcludedTransactionAndSupplierSaysSameAmountAndSuccessfullyPaymentMaking()
			throws Throwable {
		final Transaction transaction = aConcludedTransaction();
		final Money amount = aSameAmountFrom(transaction);

		final String message = queryResultOf(transaction);

		context.checking(new Expectations() {
			{
				allowing(checkerFactory).queryBy(transaction);
				will(returnValue(checker));

				allowing(checker).chargedFromSupplier();
				will(returnValue(Charge.SUCCESS));

				allowing(checker).amountFromSupplier();
				will(returnValue(amount));

				allowing(checker).extTxnNo();
				will(returnValue(EXT_TXN_NO));

				oneOf(logger).info(message);
			}
		});

		CheckResult result = target.check(transaction);

		assertTrue("unexpected check result", result.isValid());
	}

	@Test
	public void returnsValidWhenCheckGivenAResponsedSuccessTransactionAndSupplierSaysSameAmountAndSuccessfullyPaymentMaking()
			throws Throwable {
		final Transaction transaction = aResponsedSuccessTransaction();
		final Money amount = aSameAmountFrom(transaction);

		final String message = queryResultOf(transaction);

		context.checking(new Expectations() {
			{
				allowing(checkerFactory).queryBy(transaction);
				will(returnValue(checker));

				allowing(checker).chargedFromSupplier();
				will(returnValue(Charge.SUCCESS));

				allowing(checker).amountFromSupplier();
				will(returnValue(amount));

				allowing(checker).extTxnNo();
				will(returnValue(EXT_TXN_NO));

				oneOf(logger).info(message);
			}
		});

		CheckResult result = target.check(transaction);

		assertTrue("unexpected check result", result.isValid());
	}

	@Test
	public void returnsValidWhenCheckGivenAResponsedFailureTransactionAndSupplierSaysSameAmountAndUnsuccessfullyPaymentMaking()
			throws Throwable {
		final Transaction transaction = aResponsedFailureTransaction();

		final Money amount = aSameAmountFrom(transaction);

		final String message = queryResultOf(transaction);

		context.checking(new Expectations() {
			{
				allowing(checkerFactory).queryBy(transaction);
				will(returnValue(checker));

				allowing(checker).chargedFromSupplier();
				will(returnValue(Charge.FAILURE));

				allowing(checker).amountFromSupplier();
				will(returnValue(amount));

				allowing(checker).extTxnNo();
				will(returnValue(EXT_TXN_NO));

				oneOf(logger).info(message);
			}
		});

		CheckResult result = target.check(transaction);

		assertTrue("unexpected check result", result.isValid());
	}

	private String queryResultOf(final Transaction transaction) {
		final String message = "check " + transaction.getTransactionNo()
				+ ",amount=" + transaction.getAmount() + ",state="
				+ transaction.state() + ",result=" + checker;
		return message;
	}

	private Money aSameAmountFrom(Transaction transaction) {
		return transaction.getAmount();
	}

	@Test
	public void returnsInvalidWhenCheckGivenAResponsedFailureTransactionAndSupplierSaysUnsuccessfullyPaymentMakingButDifferentAmount()
			throws Throwable {
		final Transaction transaction = aResponsedFailureTransaction();

		final Money amount = aDifferentAmountFrom(transaction);

		final String message = queryResultOf(transaction);

		context.checking(new Expectations() {
			{
				allowing(checkerFactory).queryBy(transaction);
				will(returnValue(checker));

				allowing(checker).chargedFromSupplier();
				will(returnValue(Charge.FAILURE));

				allowing(checker).amountFromSupplier();
				will(returnValue(amount));

				allowing(checker).extTxnNo();
				will(returnValue(EXT_TXN_NO));

				oneOf(logger).info(message);
			}
		});

		CheckResult result = target.check(transaction);

		assertTrue("unexpected check result", result.isInvalid());
	}

	@Test
	public void returnsInvalidWhenCheckGivenAResponsedFailureTransactionAndSupplierSaysSameAmountButDifferentState()
			throws Throwable {
		final Transaction transaction = aResponsedFailureTransaction();

		final Money amount = aSameAmountFrom(transaction);

		final String message = queryResultOf(transaction);

		context.checking(new Expectations() {
			{
				allowing(checkerFactory).queryBy(transaction);
				will(returnValue(checker));

				allowing(checker).chargedFromSupplier();
				will(returnValue(Charge.SUCCESS));

				allowing(checker).amountFromSupplier();
				will(returnValue(amount));

				allowing(checker).extTxnNo();
				will(returnValue(EXT_TXN_NO));

				oneOf(logger).info(message);
			}
		});

		CheckResult result = target.check(transaction);

		assertTrue("unexpected check result", result.isInvalid());
	}

	@Test
	public void returnsInvalidWhenCheckGivenAResponsedSuccessTransactionAndSupplierSaysSameAmountButDifferentState()
			throws Throwable {
		final Transaction transaction = aResponsedSuccessTransaction();

		final Money amount = aSameAmountFrom(transaction);

		final String message = queryResultOf(transaction);

		context.checking(new Expectations() {
			{
				allowing(checkerFactory).queryBy(transaction);
				will(returnValue(checker));

				allowing(checker).chargedFromSupplier();
				will(returnValue(Charge.FAILURE));

				allowing(checker).amountFromSupplier();
				will(returnValue(amount));

				allowing(checker).extTxnNo();
				will(returnValue(EXT_TXN_NO));

				oneOf(logger).info(message);
			}
		});

		CheckResult result = target.check(transaction);

		assertTrue("unexpected check result", result.isInvalid());
	}

	@Test
	public void returnsNotificationLostWhenCheckGivenARequestedTransactionAndSupplierSaysSameAmountAndCharged()
			throws Throwable {
		final Transaction transaction = aRequestedTransaction();

		final Money amount = aSameAmountFrom(transaction);

		final String message = queryResultOf(transaction);

		context.checking(new Expectations() {
			{
				allowing(checkerFactory).queryBy(transaction);
				will(returnValue(checker));

				allowing(checker).chargedFromSupplier();
				will(returnValue(Charge.SUCCESS));

				allowing(checker).amountFromSupplier();
				will(returnValue(amount));

				allowing(checker).extTxnNo();
				will(returnValue(EXT_TXN_NO));

				oneOf(logger).info(message);
			}
		});

		CheckResult result = target.check(transaction);

		assertTrue("unexpected check result", result.isNotificationLost());
	}

	@Test
	public void returnsUnhandledWhenCheckGivenARequestedTransactionAndSupplierSaysSameAmountAndUncharged()
			throws Throwable {
		final Transaction transaction = aRequestedTransaction();

		final Money amount = aSameAmountFrom(transaction);

		final Charge uncharged = null;

		final String message = queryResultOf(transaction);

		context.checking(new Expectations() {
			{
				allowing(checkerFactory).queryBy(transaction);
				will(returnValue(checker));

				allowing(checker).chargedFromSupplier();
				will(returnValue(uncharged));

				allowing(checker).amountFromSupplier();
				will(returnValue(amount));

				oneOf(logger).info(message);

			}
		});

		CheckResult result = target.check(transaction);

		assertTrue("unexpected check result", result.isUnhandled());
	}

	@Test
	public void returnsInvalidWhenCheckGivenAConcludedTransactionAndSupplierSaysNonExistedExternalTxnQueryResult()
			throws Throwable {

		final NonExistedExternalTxnQueryResult checker = context
				.mock(NonExistedExternalTxnQueryResult.class);

		final Transaction transaction = aConcludedTransaction();

		final String message = "check " + transaction.getTransactionNo()
				+ ",amount=" + transaction.getAmount() + ",state="
				+ transaction.state() + ",result=" + checker;

		context.checking(new Expectations() {
			{
				allowing(checkerFactory).queryBy(transaction);
				will(returnValue(checker));

				allowing(checker).extTxnNo();
				will(returnValue(null));
				
				allowing(checker).chargedFromSupplier();
				will(returnValue(null));
				
				oneOf(logger).info(message);

			}
		});

		CheckResult result = target.check(transaction);

		assertTrue("unexpected check result", result.isInvalid());
	}

	@Test
	public void returnsUnhandledWhenCheckGivenARequestedTransactionAndSupplierSaysNonExistedExternalTxnQueryResult()
			throws Throwable {

		final NonExistedExternalTxnQueryResult checker = context
				.mock(NonExistedExternalTxnQueryResult.class);

		final Transaction transaction = aRequestedTransaction();

		final String message = "check " + transaction.getTransactionNo()
				+ ",amount=" + transaction.getAmount() + ",state="
				+ transaction.state() + ",result=" + checker;

		context.checking(new Expectations() {
			{
				allowing(checkerFactory).queryBy(transaction);
				will(returnValue(checker));

				oneOf(logger).info(message);

			}
		});

		CheckResult result = target.check(transaction);

		assertTrue("unexpected check result", result.isUnhandled());
	}

	@Test
	public void returnsSupportedChannelByQueryObject() throws Throwable {
		final Set<String> expect = new HashSet<String>();
		expect.add("channel");

		context.checking(new Expectations() {
			{
				allowing(checkerFactory).supportedChannels();
				will(returnValue(expect));

			}
		});

		Set<String> actual = target.supportedChannels();

		assertSame(expect, actual);
	}

	@Test
	public void returnsToBeCheckedSpec() throws Throwable {

		final Set<String> channels = new HashSet<String>();
		channels.add("1");
		channels.add("2");

		final ToBeCheckedSpecification expect = new ToBeCheckedSpecification(
				channels);

		context.checking(new Expectations() {
			{
				allowing(checkerFactory).supportedChannels();
				will(returnValue(channels));

			}
		});

		ToBeCheckedSpecification actual = target.aToBeCheckedSpec();

		assertEquals(expect, actual);
	}

	private Transaction aRequestedTransaction() {
		return transaction;
	}

	private Money aDifferentAmountFrom(final Transaction transaction) {
		return Money.valueOf(BigDecimal.ZERO);
	}

	private Transaction aConcludedTransaction() {
		transaction.handle(new NotificationFixture().build(), new Date());
		transaction.conclude(new Date());
		return transaction;
	}

	private Transaction aResponsedSuccessTransaction() {
		transaction.handle(new NotificationFixture().build(), new Date());
		return transaction;
	}

	private Transaction aResponsedFailureTransaction() {
		transaction.handle(new NotificationFixture().specify(Charge.FAILURE)
				.build(), new Date());
		return transaction;
	}

	public class TransactionCheckerStub extends TransactionCheckerImpl {
		@Override
		protected Logger getLogger() {
			return logger;
		}
	}
}
