package com.springtour.otg.domain.model.transaction;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.StandardToStringStyle;

import com.springtour.shared.ValueObject;

/**
 * 交易流水号
 * </p>
 * 
 * 交易流水号是交易的标识
 * 
 * @author Hippoom
 * 
 */
public class TransactionNo implements ValueObject<TransactionNo> {
	/**
	 * 流水号
	 */
	private String number;

	public TransactionNo(String number) {
		this.number = number;
	}

	public TransactionNo() {
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Override
	public boolean sameValueAs(final TransactionNo other) {
		return (other != null) && (this.number.equals(other.getNumber()));
	}

	@Override
	public boolean equals(Object t) {
		if (this == t) {
			return true;
		}
		if (t instanceof TransactionNo) {
			return this.sameValueAs((TransactionNo) t);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return number.hashCode();
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.reflectionToString(this,
				StandardToStringStyle.SHORT_PREFIX_STYLE);
	}
}
