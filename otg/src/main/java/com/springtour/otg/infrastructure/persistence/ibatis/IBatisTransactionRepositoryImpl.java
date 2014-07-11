package com.springtour.otg.infrastructure.persistence.ibatis;

import java.util.*;

import org.springframework.orm.*;
import org.springframework.orm.ibatis.support.*;

import com.springtour.otg.domain.model.transaction.*;
import com.springtour.otg.infrastructure.persistence.*;

/**
 * 
 * @author jker
 * 
 *         2011-5-5
 */
public class IBatisTransactionRepositoryImpl extends SqlMapClientDaoSupport
		implements TransactionRepository {

	private final String nameSpace = "com/springtour/otg/infrastructure/persistence/ibatis/TransactionRepositorySqlMap";

	@Override
	public String nextTransactionNoSequence() {
		return (String) getSqlMapClientTemplate().queryForObject(
				nameSpace + ".nextTransactionNoSeq");
	}

	@Override
	public void store(Transaction transaction) {
		if (transaction.getTransactionId() == null) {
			getSqlMapClientTemplate().insert(nameSpace + ".insert2Transaction",
					transaction);
		} else {
			// if (transaction.isUpdated()) {
			int rowAffected = getSqlMapClientTemplate().update(
					nameSpace + ".updateTransaction", transaction);
			if (rowAffected != 1) {
				throw new ObjectOptimisticLockingFailureException(
						transaction.getClass(), transaction.getTransactionNo()
								.getNumber());
			}
			transaction.resetUpdated();
			// }
		}
	}

	@Override
	public Transaction find(TransactionNo transactionNo) {
		final String transactionNumber = transactionNo.getNumber();
		return (Transaction) getSqlMapClientTemplate().queryForObject(
				nameSpace + ".findByTransactionNo", transactionNumber);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Transaction> find(String application, String orderId,
			int firstResult, int maxResults) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("application", application);
		param.put("orderId", orderId);
		param.put("firstResult", String.valueOf(firstResult));
		param.put("maxResults", String.valueOf(maxResults));
		return getSqlMapClientTemplate().queryForList(
				nameSpace + ".findByOrderId", param);
	}

	@Override
	public long count(String application, String orderId) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("application", application);
		param.put("orderId", orderId);

		Object obj = getSqlMapClientTemplate().queryForObject(
				nameSpace + ".countByOrderId", param);
		return Long.parseLong(obj.toString());
	}

	@Override
	public long count(TransactionCriteria criteria) {
		Object obj = getSqlMapClientTemplate().queryForObject(
				nameSpace + ".countSatisfying", criteria);
		return Long.parseLong(obj.toString());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Transaction> find(TransactionCriteria criteria) {
		return getSqlMapClientTemplate().queryForList(
				nameSpace + ".findSatisfying", criteria);
	}

	@Override
	public List<Transaction> findBy(TransactionSelectionSpecification spec) {
		return spec.satisfyingElementsFrom(this);
	}
}
