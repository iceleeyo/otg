package com.springtour.otg.domain.model.transaction;

import static org.junit.Assert.*;
import org.junit.Test;

public class TransactionTypeUnitTests {

	@Test
	public void of() {
		TransactionType newType = TransactionType.of("0");
		assertEquals(TransactionType.NEW, newType);

		TransactionType refund = TransactionType.of("1");
		assertEquals(TransactionType.CANCEL, refund);

		String aNotValidCode = "3";
		TransactionType newType2 = TransactionType.of(aNotValidCode);
		assertEquals(TransactionType.NEW, newType2);
	}

	@Test
	public void sameCode() {
		assertTrue(TransactionType.NEW.sameCodeAs("0"));
		assertTrue(TransactionType.CANCEL.sameCodeAs("1"));
		assertFalse(TransactionType.NEW.sameCodeAs("3"));

	}

	@Test
	public void name() {
		assertEquals("支付", TransactionType.NEW.getName());
		assertEquals("撤销", TransactionType.CANCEL.getName());
	}
}
