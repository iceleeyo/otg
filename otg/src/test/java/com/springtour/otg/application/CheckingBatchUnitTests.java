package com.springtour.otg.application;

import java.text.*;
import java.util.*;

import org.jmock.*;
import org.junit.*;

import com.springtour.otg.application.impl.*;
import com.springtour.otg.domain.model.transaction.*;
import com.springtour.otg.domain.service.*;
import com.springtour.otg.infrastructure.time.*;
import com.springtour.test.*;

public class CheckingBatchUnitTests extends AbstractJMockUnitTests {

	private CheckingBatch target;

	private TransactionRepository transactionRepository = context
			.mock(TransactionRepository.class);
	private TransactionCheckingEvents transactionCheckingEvents = context
			.mock(TransactionCheckingEvents.class);
	private TransactionChecker transactionChecker = context
			.mock(TransactionChecker.class);
	private TransactionValidationSpecifications validationSpecifications = context
			.mock(TransactionValidationSpecifications.class);

	private Clock clock = context.mock(Clock.class);

	private CheckingBatchNotifier batchNotifier = context
			.mock(CheckingBatchNotifier.class);

	private Set<String> supportedChannels = new HashSet<String>();

	private DeadTransactionSpecification deadSpec = context.mock(
			DeadTransactionSpecification.class, "deadSpec");
	private NewBornTransactionSpecification newBornSpec = context.mock(
			NewBornTransactionSpecification.class, "newBornSpec");
	private UnlivelyAndByeRoundTransactionSpecification unlivelyAndByeSpec = context
			.mock(UnlivelyAndByeRoundTransactionSpecification.class,
					"unlivelyAndByeSpec");

	private Transaction transaction = context.mock(Transaction.class);

	private List<Transaction> transactions = new ArrayList<Transaction>();

	private ToBeCheckedSpecification spec = context
			.mock(ToBeCheckedSpecification.class);

	private Date now() throws ParseException {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.parse("2013-02-18 16:39:30");
	}

	@Before
	public void setUp() {
		target = new CheckingBatch();
		target.setTransactionRepository(transactionRepository);
		target.setTransactionCheckingEvents(transactionCheckingEvents);
		target.setTransactionChecker(transactionChecker);
		target.setClock(clock);
		target.setValidationSpecifications(validationSpecifications);
		target.setBatchNotifier(batchNotifier);
	}

	@Before
	public void specifySupportedChannels() {
		supportedChannels.add("channel1");
		supportedChannels.add("channel2");
	}

	@Before
	public void specifyToBeCheckedTransactions() {
		this.transactions.add(transaction);
	}

	@Test
	public void notifiesSpottingDeadGivenATransactionSatisfiesDeadSpec()
			throws Exception {
		final Date now = now();

		context.checking(new Expectations() {

			{
				{

					allowing(transactionChecker).aToBeCheckedSpec();
					will(returnValue(spec));

					allowing(transactionRepository).findBy(spec);
					will(returnValue(transactions));

					allowing(validationSpecifications).aDeadSpec(now);
					will(returnValue(deadSpec));

					allowing(deadSpec).satisfiedBy(transaction);
					will(returnValue(true));

					allowing(clock).now();
					will(returnValue(now));

					oneOf(batchNotifier).notifySpottingDead(transaction);

					oneOf(transactionCheckingEvents).notifySpottingDead(
							transaction);
				}
			}
		});

		target.run();
	}

	@Test
	public void skipGivenATransactionSatisfiesNewBornSpec() throws Exception {
		final Date now = now();

		context.checking(new Expectations() {

			{
				{

					allowing(transactionChecker).aToBeCheckedSpec();
					will(returnValue(spec));

					allowing(transactionRepository).findBy(spec);
					will(returnValue(transactions));

					allowing(clock).now();
					will(returnValue(now));

					allowing(validationSpecifications).aDeadSpec(now);
					will(returnValue(deadSpec));

					allowing(validationSpecifications).aNewBornSpec(now);
					will(returnValue(newBornSpec));

					allowing(deadSpec).satisfiedBy(transaction);
					will(returnValue(false));

					allowing(newBornSpec).satisfiedBy(transaction);
					will(returnValue(true));
					
					oneOf(batchNotifier).notifySpottingNewBorn(transaction);
				}
			}
		});

		target.run();
	}

	@Test
	public void skipGivenATransactionSatisfiesUnlivelyAndByeSpec()
			throws Exception {
		final Date now = now();

		context.checking(new Expectations() {

			{
				{

					allowing(transactionChecker).aToBeCheckedSpec();
					will(returnValue(spec));

					allowing(transactionRepository).findBy(spec);
					will(returnValue(transactions));

					allowing(clock).now();
					will(returnValue(now));

					allowing(validationSpecifications).aDeadSpec(now);
					will(returnValue(deadSpec));

					allowing(validationSpecifications).aNewBornSpec(now);
					will(returnValue(newBornSpec));

					allowing(validationSpecifications)
							.anUnlivelyAndByeRoundSpec(now);
					will(returnValue(unlivelyAndByeSpec));

					allowing(deadSpec).satisfiedBy(transaction);
					will(returnValue(false));

					allowing(newBornSpec).satisfiedBy(transaction);
					will(returnValue(false));

					allowing(unlivelyAndByeSpec).satisfiedBy(transaction);
					will(returnValue(true));
					
					oneOf(batchNotifier).notifySpottingUnlivelyAndBye(transaction);
				}
			}
		});

		target.run();
	}

	@Test
	public void notifiesToCheckGivenATransactionDoesntSatisfyOtherSpecs()
			throws Exception {
		final Date now = now();

		context.checking(new Expectations() {

			{
				{

					allowing(transactionChecker).aToBeCheckedSpec();
					will(returnValue(spec));

					allowing(transactionRepository).findBy(spec);
					will(returnValue(transactions));

					allowing(clock).now();
					will(returnValue(now));

					allowing(validationSpecifications).aDeadSpec(now);
					will(returnValue(deadSpec));

					allowing(validationSpecifications).aNewBornSpec(now);
					will(returnValue(newBornSpec));

					allowing(validationSpecifications)
							.anUnlivelyAndByeRoundSpec(now);
					will(returnValue(unlivelyAndByeSpec));

					allowing(deadSpec).satisfiedBy(transaction);
					will(returnValue(false));

					allowing(newBornSpec).satisfiedBy(transaction);
					will(returnValue(false));

					allowing(unlivelyAndByeSpec).satisfiedBy(transaction);
					will(returnValue(false));

					oneOf(transactionCheckingEvents).notifyToCheck(transaction);
					
					oneOf(batchNotifier).notifyToBeChecked(transaction);
				}
			}
		});

		target.run();
	}

	@Test
	public void continuesCheckingIgnoringError() throws Exception {
		final Date now = now();

		final Transaction transaction1 = context.mock(Transaction.class,
				"transaction1");
		this.transactions.add(transaction1);

		final Exception e = new IllegalArgumentException();

		context.checking(new Expectations() {

			{
				{

					allowing(transactionChecker).aToBeCheckedSpec();
					will(returnValue(spec));

					allowing(transactionRepository).findBy(spec);
					will(returnValue(transactions));

					allowing(clock).now();
					will(returnValue(now));

					allowing(validationSpecifications).aDeadSpec(now);
					will(returnValue(deadSpec));

					allowing(deadSpec).satisfiedBy(transaction);
					will(returnValue(true));

					oneOf(transactionCheckingEvents).notifySpottingDead(
							transaction);
					will(throwException(e));

					oneOf(deadSpec).satisfiedBy(transaction1);
					will(returnValue(true));

					oneOf(transactionCheckingEvents).notifySpottingDead(
							transaction1);

					oneOf(batchNotifier).notifyError(transaction, e);

					oneOf(batchNotifier).notifySpottingDead(transaction);

					oneOf(batchNotifier).notifySpottingDead(transaction1);
				}
			}
		});

		target.run();
	}
}
