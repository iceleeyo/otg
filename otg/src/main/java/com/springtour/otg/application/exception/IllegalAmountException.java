package com.springtour.otg.application.exception;

import com.springtour.otg.domain.model.transaction.TransactionNo;
import com.springtour.otg.domain.shared.Money;
import java.math.BigDecimal;

/**
 * 非法交易金额异常
 * 
 * @author Hippoom
 * 
 */
public class IllegalAmountException extends AbstractCheckedApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IllegalAmountException(TransactionNo transactionNo, Money current,
			Money illegal) {
		super(new StringBuilder("illegal amount :[transactionNo:").append(
				transactionNo).append(",").append("current:").append(current)
				.append(",").append("illegal:").append(illegal).append("]")
				.toString());
	}

}
