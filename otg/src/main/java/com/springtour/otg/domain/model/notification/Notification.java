package com.springtour.otg.domain.model.notification;

import java.util.Date;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import com.springtour.otg.domain.model.transaction.Transaction;
import com.springtour.otg.domain.model.transaction.TransactionNo;
import com.springtour.otg.domain.shared.Money;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@EqualsAndHashCode(of = "sequence")
public class Notification {

	private static final String EMPTY_STRING = "";
	private String sequence;
	private Date whenReceived;
	private TransactionNo txnNo;
	private String extTxnNo;
	private Money amount;
	private String charged;
	private String message;
	private String signature;
	private String synchronizingMethod;
	@Getter
	@Setter
	private String cardInfo;

	public static Notification handMake(String sequence, Date whenReceived,
			Transaction transaction, String extTxnNo, Charge charged) {
		return new Notification(sequence, whenReceived,
				transaction.getTransactionNo(), extTxnNo,
				transaction.getAmount(), charged, EMPTY_STRING, EMPTY_STRING,
				EMPTY_STRING, SynchronizingMethod.MANUAL);
	}

	public static Notification byCheck(String sequence, Date whenReceived,
			Transaction transaction, String extTxnNo, Charge charged) {
		return new Notification(sequence, whenReceived,
				transaction.getTransactionNo(), extTxnNo,
				transaction.getAmount(), charged, EMPTY_STRING, EMPTY_STRING,
				EMPTY_STRING, SynchronizingMethod.CHECK);
	}

	public static Notification bySynchronous(String sequence,
			Date whenReceived, Transaction transaction, String extTxnNo,
			Money amount, Charge charged, String message) {
		return new Notification(sequence, whenReceived,
				transaction.getTransactionNo(), extTxnNo, amount, charged,
				message, EMPTY_STRING, EMPTY_STRING,
				SynchronizingMethod.SYNCHRONOUS);
	}

	public static Notification byAsynchronous(String sequence,
			Date whenReceived, Transaction transaction, String extTxnNo,
			Money amount, Charge charged, String message, String signature,
			String cardInfo) {
		return new Notification(sequence, whenReceived,
				transaction.getTransactionNo(), extTxnNo, amount, charged,
				message, signature, cardInfo, SynchronizingMethod.ASYNCHRONOUS);
	}

	private Notification(String sequence, Date whenReceived,
			TransactionNo txnNo, String extTxnNo, Money amount, Charge charged,
			String message, String signature, String cardInfo,
			SynchronizingMethod synchronizingMethod) {
		this.sequence = sequence;
		this.whenReceived = new Date(whenReceived.getTime());
		this.txnNo = txnNo;
		this.extTxnNo = extTxnNo;
		this.amount = amount;
		this.charged = charged.getCode();
		this.message = message;
		this.signature = signature;
		this.cardInfo = cardInfo;
		this.synchronizingMethod = synchronizingMethod.getCode();
	}

	public boolean isCharged() {
		return Charge.SUCCESS.sameCodeAs(charged) ? true : false;
	}

	public boolean mustBeValidated() {
		return SynchronizingMethod.SYNCHRONOUS
				.sameCodeAs(this.synchronizingMethod);
	}

	public boolean isByCheck() {
		return SynchronizingMethod.CHECK.sameCodeAs(this.synchronizingMethod);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append(sequence).append(whenReceived).append(txnNo)
				.append(extTxnNo).append(amount).append(charged)
				.append(message).append(cardInfo).append(synchronizingMethod)
				.toString();
	}

	public String getSequence() {
		return sequence;
	}

	public TransactionNo getTxnNo() {
		return txnNo;
	}

	public String getExtTxnNo() {
		return extTxnNo;
	}

	public Money getAmount() {
		return amount;
	}

	public String getCharged() {
		return charged;
	}

	public String getMessage() {
		return message;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public Notification() {
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public void setTxnNo(TransactionNo txnNo) {
		this.txnNo = txnNo;
	}

	public void setExtTxnNo(String extTxnNo) {
		this.extTxnNo = extTxnNo;
	}

	public void setAmount(Money amount) {
		this.amount = amount;
	}

	public void setCharged(String charged) {
		this.charged = charged;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSynchronizingMethod() {
		return synchronizingMethod;
	}

	public Date getWhenReceived() {
		return whenReceived;
	}

	public void setWhenReceived(Date whenReceived) {
		this.whenReceived = whenReceived;
	}

	public void setSynchronizingMethod(String synchronizingMethod) {
		this.synchronizingMethod = synchronizingMethod;
	}
}
