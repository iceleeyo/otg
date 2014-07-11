package com.springtour.otg.domain.model.transaction;

import static com.springtour.otg.domain.model.transaction.CheckingState.*;
import static org.junit.Assert.*;

import java.util.*;

import org.jmock.*;
import org.junit.*;

import com.springtour.otg.infrastructure.persistence.*;
import com.springtour.test.*;

public class ToBeCheckedSpecificationUnitTests extends AbstractJMockUnitTests {

	private TransactionSelectionSpecification spec;

	private Set<String> channels;

	private TransactionRepository transactionRepository = context
			.mock(TransactionRepository.class);

	@Before
	public void init() {
		channels = new HashSet<String>();
		channels.add("channel1");
		channels.add("channel2");
		spec = new ToBeCheckedSpecification(channels);
	}

	@Test
	public void delegatesToRepositoryWhenSelectSatisfyingElements()
			throws Throwable {

		final List<Transaction> expect = Arrays.asList(new TransactionFixture()
				.build());

		context.checking(new Expectations() {
			{
				allowing(transactionRepository).find(
						new TransactionCriteria.Builder().eq(UNCHECKED)
								.through(channels).unpaged().build());
				will(returnValue(expect));
			}
		});

		List<Transaction> actual = spec
				.satisfyingElementsFrom(transactionRepository);
		assertSame(expect, actual);
	}
}
