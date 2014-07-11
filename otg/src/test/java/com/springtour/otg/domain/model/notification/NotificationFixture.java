/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.domain.model.notification;

import com.springtour.otg.domain.model.transaction.Transaction;
import com.springtour.otg.domain.model.transaction.TransactionFixture;
import com.springtour.otg.domain.shared.Money;
import com.springtour.otg.infrastructure.time.Clock;
import java.util.Date;

/**
 * 
 * @author 001595
 */
public class NotificationFixture {

	private static final Transaction DEFAULT_TXN = new TransactionFixture()
			.build();
	private static final String DEFAULT_MESSAGE = "00";
	private static final String DEFAULT_SIGNATURE = "DEFAULT_SIGNATURE";

	private Clock clock;
	private String sequence;
	private Transaction transaction = DEFAULT_TXN;
	private String extTxnNo = "ORMUnit";
	private Money amount = DEFAULT_TXN.getAmount();
	private Charge charged = Charge.SUCCESS;
	private String message = DEFAULT_MESSAGE;
	private String signature = DEFAULT_SIGNATURE;
	private String cardInfo = "交通银行：1235664";

	public NotificationFixture(Clock clock) {
		this.clock = clock;
	}

	public NotificationFixture() {
	}

	public NotificationFixture specifySequence(String sequence) {
		this.sequence = sequence;
		return this;
	}

	public NotificationFixture specify(Charge charged) {
		this.charged = charged;
		return this;
	}

	public NotificationFixture specifyExtTxnNo(String extTxnNo) {
		this.extTxnNo = extTxnNo;
		return this;
	}

	public NotificationFixture specify(Transaction transaction) {
		this.transaction = transaction;
		return this;
	}

	public Notification build() {
		Notification notification = Notification.byAsynchronous(sequence,
				now(), transaction, extTxnNo, amount, charged, message,
				signature, cardInfo);
		return notification;
	}

	public Notification buildWithCheck() {
		Notification notification = Notification.byCheck(sequence, now(),
				transaction, extTxnNo, charged);
		return notification;
	}

	private Date now() {
		return clock != null ? clock.now() : new Date();
	}
}
