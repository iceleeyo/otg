package com.springtour.otg.domain.model.transaction;

import lombok.*;

import com.springtour.otg.domain.model.notification.*;

public class CheckResult {

	private Result result;
	@Getter
	private TransactionNo transactionNo;
	@Getter
	private String extTxnNo;
	@Getter
	private Charge charged;

	public static CheckResult valid(TransactionNo transactionNo,
			String extTxnNo, Charge charged) {
		return new CheckResult(Result.VALID, transactionNo, extTxnNo, charged);
	}

	public static CheckResult invalid(TransactionNo transactionNo,
			String extTxnNo, Charge charged) {
		return new CheckResult(Result.INVALID, transactionNo, extTxnNo, charged);
	}

	public static CheckResult unhandled(TransactionNo transactionNo) {
		return new CheckResult(Result.UNHANDLED, transactionNo, null, null);
	}

	public static CheckResult notificationLost(TransactionNo transactionNo,
			String extTxnNo, Charge charged) {
		return new CheckResult(Result.NOTIFICATION_LOST, transactionNo,
				extTxnNo, charged);
	}

	public boolean isValid() {
		return Result.VALID.equals(this.result);
	}

	public boolean isInvalid() {
		return Result.INVALID.equals(this.result);
	}

	public boolean isNotificationLost() {
		return Result.NOTIFICATION_LOST.equals(this.result);
	}
	
	public boolean isUnhandled() {
		return Result.UNHANDLED.equals(this.result);
	}

	private CheckResult(Result result, TransactionNo transactionNo,
			String extTxnNo, Charge charged) {
		this.result = result;
		this.transactionNo = transactionNo;
		this.extTxnNo = extTxnNo;
		this.charged = charged;
	}

	public enum Result {
		/**
		 * 用户已完成付款（扣款可能是失败的）且与otg中Transaction的信息一致
		 */
		VALID,
		/**
		 * 用户已完成付款（扣款可能是失败的）但与otg中Transaction的信息不一致 或在otg中有Transaction，但在供应商处没有
		 * 
		 */
		INVALID,
		/**
		 * 用户已完成付款（扣款可能是失败的）但otg中的Transaction还未收到通知
		 */
		NOTIFICATION_LOST,
		/**
		 * 用户还未完成付款
		 */
		UNHANDLED;
	}

	

}
