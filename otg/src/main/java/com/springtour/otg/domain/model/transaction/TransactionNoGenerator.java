package com.springtour.otg.domain.model.transaction;

import java.util.Date;

import com.springtour.otg.domain.model.merchant.Merchant;

/**
 * 交易流水号生成器
 * 
 * @author Hippoom
 * 
 */
public interface TransactionNoGenerator {
	/**
	 * 
	 * @param transactionNoSequence
	 * @param whenRequested
	 * @return
	 */
	TransactionNo nextTransactionNo(String channelId, Merchant merchant, String transactionNoSequence, Date whenRequested);
}
