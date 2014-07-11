package com.springtour.otg.domain.model.transaction;

import static org.junit.Assert.assertSame;
import java.util.Arrays;
import java.util.List;
import org.jmock.Expectations;
import org.junit.Before;
import org.junit.Test;

import com.springtour.otg.infrastructure.persistence.TransactionCriteria;
import com.springtour.test.AbstractJMockUnitTests;

public class CorrelatedCancelSpecificationUnitTests extends
		AbstractJMockUnitTests {

	private TransactionSelectionSpecification spec;
	private String oldTxnNo = "1232423";

	private TransactionRepository transactionRepository = context
			.mock(TransactionRepository.class);

	@Before
	public void init() {
		spec = new CorrelatedCancelSpecification(oldTxnNo);
	}

	@Test
	public void delegatesToRepositoryWhenSelectSatisfyingElements()
			throws Throwable {

		final List<Transaction> expect = Arrays.asList(new TransactionFixture()
				.build());

		context.checking(new Expectations() {
			{
				allowing(transactionRepository).find(
						new TransactionCriteria.Builder()
								.transactionTypeEq(
										TransactionType.CANCEL.getCode())
								.referenceTxnNumberEq(oldTxnNo).build());
				will(returnValue(expect));
			}
		});

		List<Transaction> actual = spec
				.satisfyingElementsFrom(transactionRepository);
		assertSame(expect, actual);
	}
}
