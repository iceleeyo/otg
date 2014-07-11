package com.springtour.otg.domain.model.notification;

import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;

import com.springtour.otg.domain.model.transaction.Transaction;
import com.springtour.otg.domain.model.transaction.TransactionFixture;

public class NotificationUnitTests {
	private Notification target;
	private String sequence = "123456";
	private Date whenReceived = new Date();
	private Transaction transaction = new TransactionFixture().build();
	private String extTxnNo = "654321";
	private Charge charged = Charge.of("1");

	@Test
	public void testIsByCheck() {
		target = Notification.byCheck(sequence, whenReceived, transaction, extTxnNo, charged);
		Assert.assertTrue(target.isByCheck());
	}
	
	@Test
	public void testIsNotByCheck() {
		target = Notification.handMake(sequence, whenReceived, transaction, extTxnNo, charged);
		Assert.assertFalse(target.isByCheck());
	}

}
