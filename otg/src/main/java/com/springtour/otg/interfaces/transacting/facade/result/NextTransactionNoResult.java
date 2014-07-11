package com.springtour.otg.interfaces.transacting.facade.result;

import java.io.Serializable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.StandardToStringStyle;

public class NextTransactionNoResult implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 状态码
	 */
	private String statusCode;
	/**
	 * 交易流水号
	 */
	private String transactionNo;

	/**
	 * @param statusCode
	 * @param transactionNo
	 */
	public NextTransactionNoResult(String statusCode, String transactionNo) {
		this.statusCode = statusCode;
		this.transactionNo = transactionNo;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.reflectionToString(this,
				StandardToStringStyle.SHORT_PREFIX_STYLE);
	}

	public String getStatusCode() {
		return statusCode;
	}

	public String getTransactionNo() {
		return transactionNo;
	}
}
