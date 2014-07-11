package com.springtour.otg.infrastructure.persistence;

import org.jmock.*;
import org.jmock.integration.junit4.*;
import org.jmock.lib.legacy.*;
import org.junit.*;
import org.junit.runner.*;

@RunWith(JMock.class)
public class TransactionCriteriaMockTests /* extends JUnit4Mockery */{
	// {
	// setImposteriser(ClassImposteriser.INSTANCE);
	// }

	private Mockery context = new JUnit4Mockery() {

		{
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};
	private TransactionCriteria target;

	@Test
	public void testBuilder_empty_good() {

		target = new TransactionCriteria.Builder().build();
		Assert.assertTrue(target.getFirstResult() == 1);
		Assert.assertTrue(target.getMaxResults() == 50);
	}

	@Test
	public void testBuilder_transactionNoEq_good() {
		final String transactionNo = "111";
		target = new TransactionCriteria.Builder().transactionNoEq(
				transactionNo).build();
		Assert.assertEquals(transactionNo, target.getTransactionNoEq());
		Assert.assertTrue(target.getFirstResult() == 1);
		Assert.assertTrue(target.getMaxResults() == 50);
	}
}
