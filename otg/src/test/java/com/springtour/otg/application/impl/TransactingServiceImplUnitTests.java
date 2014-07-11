package com.springtour.otg.application.impl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.junit.Before;
import org.junit.Test;

import com.springtour.otg.domain.model.transaction.CorrelatedCancelSpecification;
import com.springtour.otg.domain.model.transaction.Transaction;
import com.springtour.otg.domain.model.transaction.TransactionFactory;
import com.springtour.otg.domain.model.transaction.TransactionFixture;
import com.springtour.otg.domain.model.transaction.TransactionNo;
import com.springtour.otg.domain.model.transaction.TransactionRepository;
import com.springtour.otg.domain.model.transaction.TransactionSelectionSpecification;
import com.springtour.test.AbstractJMockUnitTests;

public class TransactingServiceImplUnitTests extends AbstractJMockUnitTests {

	private TransactingServiceImpl target;
	private TransactionRepository transactionRepository = context
			.mock(TransactionRepository.class);
	private TransactionFactory transactionFactory = context
			.mock(TransactionFactory.class);

	@Before
	public void prepare() {
		target = new TransactingServiceImpl();
		target.setTransactionFactory(transactionFactory);
		target.setTransactionRepository(transactionRepository);
	}

	@Test
	public void returnCancelledTxnWhenExisted() {
		final String oldTransactionNumber = "12345656";
		final TransactionSelectionSpecification spec = new CorrelatedCancelSpecification(
				oldTransactionNumber);
		final List<Transaction> transactions = new ArrayList<Transaction>();
		final Transaction cancelledTxn = new TransactionFixture().build();
		transactions.add(cancelledTxn);

		context.checking(new Expectations() {
			{
				allowing(transactionRepository).find(
						new TransactionNo(oldTransactionNumber));
				will(returnValue(cancelledTxn));
				
				allowing(transactionFactory).correlatedCancelSpec(
						oldTransactionNumber);
				will(returnValue(spec));

				allowing(transactionRepository).findBy(spec);
				will(returnValue(transactions));
			}
		});

		Transaction result = target.cancelTransaction(oldTransactionNumber);
		assertEquals(cancelledTxn.getTransactionNo(), result.getTransactionNo());
	}

	@Test
	public void returnNewCancelledTxnWhenNotEverCancelled() {
		final String oldTransactionNumber = "12345656";
		final TransactionSelectionSpecification spec = new CorrelatedCancelSpecification(
				oldTransactionNumber);
		final List<Transaction> transactions = new ArrayList<Transaction>();
		final Transaction cancelledTxn = new TransactionFixture().build();
		final Transaction oldTxn = new TransactionFixture().build();
		context.checking(new Expectations() {
			{
				allowing(transactionFactory).correlatedCancelSpec(
						oldTransactionNumber);
				will(returnValue(spec));

				allowing(transactionRepository).findBy(spec);
				will(returnValue(transactions));

				allowing(transactionRepository).find(
						new TransactionNo(oldTransactionNumber));
				will(returnValue(oldTxn));

				allowing(transactionFactory).cancelTransactionBy(oldTxn);
				will(returnValue(cancelledTxn));

				oneOf(transactionRepository).store(oldTxn);

				oneOf(transactionRepository).store(cancelledTxn);
			}
		});

		Transaction result = target.cancelTransaction(oldTransactionNumber);
		assertEquals(cancelledTxn.getTransactionNo(), result.getTransactionNo());

	}
}
