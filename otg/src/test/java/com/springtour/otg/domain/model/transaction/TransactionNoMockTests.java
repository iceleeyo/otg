package com.springtour.otg.domain.model.transaction;

import java.math.BigDecimal;
import java.util.Date;

import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMock.class)
public class TransactionNoMockTests /* extends JUnit4Mockery */{
	// {
	// setImposteriser(ClassImposteriser.INSTANCE);
	// }

	private Mockery context = new JUnit4Mockery() {
		{
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};

	private TransactionNo target;

	@Before
	public void onSetUp() {
		target = new TransactionNo("111");
	}

	@Test
	public void sameValueAs() {

		TransactionNo txnNo = new TransactionNo("111");

		Assert.assertTrue(target.sameValueAs(txnNo));
	}

	@Test
	public void differentValueAsForDifferentNumber() {

		TransactionNo txnNo = new TransactionNo("222");

		Assert.assertTrue(!target.sameValueAs(txnNo));
	}

	@Test
	public void differentValueAsForNull() {

		TransactionNo txnNo = null;

		Assert.assertTrue(!target.sameValueAs(txnNo));
	}

	@Test
	public void equals4SameValueAs() {

		TransactionNo txnNo = new TransactionNo("111");

		Assert.assertTrue(target.equals(txnNo));
	}

	@Test
	public void notEquals4differentValueAs() {

		TransactionNo txnNo = new TransactionNo("222");

		Assert.assertTrue(!target.equals(txnNo));
	}

	@Test
	public void notEquals4Null() {

		TransactionNo txnNo = null;

		Assert.assertTrue(!target.equals(txnNo));
	}

	@Test
	public void notEquals4DifferentClassType() {

		String txnNo = "111";

		Assert.assertTrue(!target.equals(txnNo));
	}

}
