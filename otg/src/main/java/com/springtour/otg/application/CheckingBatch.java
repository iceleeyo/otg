package com.springtour.otg.application;

import java.util.*;

import lombok.*;

import com.springtour.otg.application.impl.*;
import com.springtour.otg.domain.model.transaction.*;
import com.springtour.otg.domain.service.*;
import com.springtour.otg.infrastructure.time.*;

public class CheckingBatch {

	@Setter
	private TransactionRepository transactionRepository;
	@Setter
	private TransactionCheckingEvents transactionCheckingEvents;
	@Setter
	private Clock clock;
	@Setter
	private TransactionValidationSpecifications validationSpecifications;
	@Setter
	private TransactionChecker transactionChecker;
	@Setter
	private CheckingBatchNotifier batchNotifier;

	public void run() {
		List<Transaction> transactions = transactionsToBeChecked();
		final Date now = now();// 尽可能减少获取时间的次数，以及这样对每一笔交易，它计算依赖的时间是一样的，而不会因为其在排在transactions末尾导致时间不一致
		for (Transaction transaction : transactions) {
			try {
				doCheck(transaction, now);
			} catch (Exception e) {
				batchNotifier.notifyError(transaction, e);
			}
		}
	}

	private void doCheck(Transaction transaction, final Date now) {
		if (isADeadAndRequested(transaction, now)) {
			batchNotifier.notifySpottingDead(transaction);
			transactionCheckingEvents.notifySpottingDead(transaction);
		} else if (isANewBornAndRequested(transaction, now)) {
			batchNotifier.notifySpottingNewBorn(transaction);
			return;
		} else if (isAnUnlivelyAndByeAndRequested(transaction, now)) {
			batchNotifier.notifySpottingUnlivelyAndBye(transaction);
			return;
		} else {
			batchNotifier.notifyToBeChecked(transaction);
			transactionCheckingEvents.notifyToCheck(transaction);
		}
	}

	private List<Transaction> transactionsToBeChecked() {
		return transactionRepository.findBy(transactionChecker
				.aToBeCheckedSpec());
	}

	/**
	 * 判断是否为不活跃的交易且本轮轮空
	 */
	private boolean isAnUnlivelyAndByeAndRequested(Transaction transaction,
			final Date now) {
		return validationSpecifications.anUnlivelyAndByeRoundSpec(now)
				.satisfiedBy(transaction);
	}

	/**
	 * 判断是否用户正在处理中的交易
	 */
	private boolean isANewBornAndRequested(Transaction transaction,
			final Date now) {
		return validationSpecifications.aNewBornSpec(now).satisfiedBy(
				transaction);
	}

	/**
	 * 判断是否用户已放弃的交易的检查轮数
	 */
	private boolean isADeadAndRequested(Transaction transaction, final Date now) {
		return validationSpecifications.aDeadSpec(now).satisfiedBy(transaction);
	}

	private Date now() {
		return clock.now();
	}

}
