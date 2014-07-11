package com.springtour.otg.interfaces.transacting.facade.rq;

import lombok.Getter;
import lombok.Setter;

public class CancelTransactionRq extends GenericRq {
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	/**
	 * 原交易流水号（撤销时必填）
	 */
	private String oldTransactionNumber;
}
