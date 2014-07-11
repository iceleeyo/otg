package com.springtour.otg.infrastructure.channel;

import com.springtour.otg.domain.model.notification.*;
import com.springtour.otg.domain.shared.*;

public interface ExternalTransactionQueryResult {
	/**
	 * 返回供应商提供的扣款结果
	 * 
	 * 如果已扣款返回 {@link Charge}
	 * 
	 * 如果还未扣款则返回null
	 * 
	 */
	Charge chargedFromSupplier();

	/**
	 * 返回供应商提供的交易金额
	 * 
	 */
	Money amountFromSupplier();

	/**
	 * 返回供应商提供的外部交易流水号
	 * 
	 */
	String extTxnNo();

}
