package com.springtour.otg.domain.model.transaction;

import lombok.*;

/**
 * 状态
 * 
 * @author Hippoom
 * 
 */
public enum CheckingState {

	/**
	 * 未核对
	 */
	UNCHECKED("0"),
	/**
	 * 已核对且通过
	 */
	VALID("1"),
	/**
	 * 已核对且信息不一致
	 */
	INVALID("2"),
	/**
	 * 认定为已被用户放弃，不再触发核对，一般是由于双方的交易都处于请求中的状态
	 */
	DEAD("3"),
	/**
	 * 未知，see NullObject Pattern
	 */
	UNKNOWN("-1");
	/**
	 * 代码
	 */
	@Getter
	private final String code;

	private CheckingState(String code) {
		this.code = code;
	}

	public boolean sameCodeAs(String code) {
		return this.code.equals(code);
	}

	public static CheckingState of(String code) {
		for (CheckingState candidate : CheckingState.values()) {
			if (candidate.sameCodeAs(code)) {
				return candidate;
			}
		}
		return UNKNOWN;
	}

	public static CheckingState nameOf(String name) {
		for (CheckingState candidate : CheckingState.values()) {
			if (candidate.name().equals(name)) {
				return candidate;
			}
		}
		return CheckingState.UNKNOWN;
	}
}