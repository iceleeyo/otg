package com.springtour.otg.domain.model.transaction;

import java.util.List;

import com.springtour.otg.infrastructure.persistence.TransactionCriteria;

/**
 * 交易仓库
 * 
 * @author Hippoom
 * 
 */
public interface TransactionRepository {

	/**
	 * 获取交易流水号序号
	 * 
	 * @return
	 */
	String nextTransactionNoSequence();

	/**
	 * 存储交易
	 * 
	 * @param transaction
	 */
	void store(Transaction transaction);

	/**
	 * 根据交易流水号查询交易
	 * 
	 * @param transactionNo
	 * @return
	 */
	Transaction find(TransactionNo transactionNo);

	// void recordTransacting(String orderId, boolean flag);
	/**
	 * 查询业务应用的订单发起的交易
	 * 
	 * @param application
	 * @param orderId
	 * @param firstResult
	 *            从1开始
	 * @param maxResults
	 */
	List<Transaction> find(String application, String orderId, int firstResult,
			int maxResults);

	/**
	 * 查询业务应用的订单发起的交易记录数量
	 * 
	 * @param application
	 * @param orderId
	 * @return
	 */
	long count(String application, String orderId);

	/**
	 * 根据条件查询
	 * 
	 * @param criteria
	 * @return
	 */
	List<Transaction> find(TransactionCriteria criteria);

	/**
	 * 根据条件计数
	 * 
	 * @param criteria
	 * @return
	 */
	long count(TransactionCriteria criteria);

	List<Transaction> findBy(TransactionSelectionSpecification spec);
}
