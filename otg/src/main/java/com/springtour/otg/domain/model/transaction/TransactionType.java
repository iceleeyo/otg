package com.springtour.otg.domain.model.transaction;

import lombok.*;

/**
 * 交易类别
 * 
 * 
 */
public enum TransactionType {

	/**
	 * 新生成的交易
	 */
	NEW("0", "支付"),
	/**
	 * 撤销交易
	 */
	CANCEL("1", "撤销");
	/**
	 * 代码
	 */
	@Getter
	private final String code;

	@Getter
	private final String name;

	private TransactionType(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public boolean sameCodeAs(String code) {
		return (code != null) && this.code.equals(code);
	}

	public static TransactionType of(String code) {
		for (TransactionType candidate : TransactionType.values()) {
			if (candidate.sameCodeAs(code)) {
				return candidate;
			}
		}
		return NEW;
	}

}