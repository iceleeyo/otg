/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.domain.model.transaction;

import java.math.*;
import java.text.*;
import java.util.*;

import com.springtour.otg.domain.model.channel.*;
import com.springtour.otg.domain.model.merchant.*;
import com.springtour.otg.domain.model.notification.Notification;
import com.springtour.otg.domain.model.partner.*;
import com.springtour.otg.domain.shared.*;

/**
 * 
 * @author 001595
 */
public class TransactionFixture {

	public static final TransactionNo DEFAULT_TXN_NO = new TransactionNo(
			"2014021312000012");
	public static final OrderIdentity DEFAULT_ORDER_IDENTITY = OrderIdentity
			.valueOf("0", "1", "order_no");
	private TransactionNo transactionNo = DEFAULT_TXN_NO;
	private Partner partner = new PartnerFixture().build();
	private Money money = defaultMoney();
	private Date whenRequested = now();
	private OrderIdentity orderIdentity = DEFAULT_ORDER_IDENTITY;
	private Merchant merchant = new MerchantFixture().build();
	private Channel channel = new ChannelFixture().build();
	private String gateway = ChannelFixture.DEFAULT_GATEWAY;
	private String state = Transaction.State.REQUESTED.getCode();
	private HandlingActivity handlingActivity = new HandlingActivity();
	private String transactionType = TransactionType.NEW.getCode();
	private String referenceTransactionNumber = "";
	private String chargeFor = "0";

	private static Date now() {
		try {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.parse("2014-02-13 12:43:53");
		} catch (ParseException ex) {
			return null;
		}
	}

	public static Money defaultMoney() {
		try {
			return Money.valueOf(BigDecimal.valueOf(100.00));
		} catch (Exception ex) {
			return null;
		}
	}

	public TransactionFixture specify(Money amount) {
		this.money = amount;
		return this;
	}

	public TransactionFixture specify(HandlingActivity handlingActivity) {
		this.handlingActivity = handlingActivity;
		return this;
	}
	
	public TransactionFixture specify(String transactionNo) {
		this.transactionNo = new TransactionNo(transactionNo);
		return this;
	}

	public TransactionFixture specify(Transaction.State state) {
		this.state = state.getCode();
		return this;
	}

	public TransactionFixture specify(TransactionType type) {
		this.transactionType = type.getCode();
		return this;
	}

	public TransactionFixture notification(Notification notification) {
		this.handlingActivity.setNotification(notification);
		return this;
	}

	public TransactionFixture referenceNumber(String transactionNo) {
		this.referenceTransactionNumber = transactionNo;
		return this;
	}
	
	public TransactionFixture chargeFor(String chargeFor) {
		this.chargeFor = chargeFor;
		return this;
	}

	public Transaction build() {
		Transaction transaction = new Transaction(transactionNo, partner,
				money, whenRequested, orderIdentity, merchant, channel, gateway);
		transaction.setState(state);
		transaction.setHandlingActivity(handlingActivity);
		transaction.setTransactionType(transactionType);
		transaction.setReferenceTransactionNumber(referenceTransactionNumber);
		transaction.setChargeFor(chargeFor);
		return transaction;
	}

}
