package com.springtour.otg.application.impl;

import java.util.*;

import lombok.*;

import org.springframework.transaction.annotation.*;

import com.springtour.otg.application.*;
import com.springtour.otg.domain.model.notification.*;
import com.springtour.otg.domain.model.transaction.*;
import com.springtour.otg.domain.service.*;
import com.springtour.otg.infrastructure.time.*;

public class CheckTransactionServiceImpl implements CheckTransactionService {
	@Setter
	private TransactionRepository transactionRepository;
	@Setter
	private TransactionChecker transactionChecker;
	@Setter
	private NotificationService notificationService;
	@Setter
	private ApplicationEvents applicationEvents;
	@Setter
	private TransactionCheckingEvents checkingEvents;
	@Setter
	private Clock clock;

	@Transactional
	@Override
	public void check(final TransactionNo transactionNo) {
		Transaction transaction = transactionRepository.find(transactionNo);

		CheckResult result = transactionChecker.check(transaction);
		if (result.isValid()) {
			transaction.regardedAsValidAt(now());
			transactionRepository.store(transaction);
		} else if (result.isInvalid()) {
			transaction.regardedAsInvalidAt(now());
			transactionRepository.store(transaction);
			checkingEvents.notifySpottingInvalid(transaction);
		} else if (result.isNotificationLost()) {
			Notification notification = notificationService.retrieve(result
					.getTransactionNo(), result.getExtTxnNo(), result
					.getCharged(), SynchronizingMethod.CHECK);
			applicationEvents.notificationWasReceived(notification);
		} else {
			// the transaction is not handled
		}

	}

	private Date now() {
		return clock.now();
	}

	@Transactional
	@Override
	public void retrieveOrMarkAsDead(TransactionNo transactionNo) {
		Transaction transaction = transactionRepository.find(transactionNo);
		CheckResult result = null;
		try {
			result = transactionChecker.check(transaction);
		} catch (Exception e) {
			transaction.regardedAsDeadAt(now());
			transactionRepository.store(transaction);
			return;
		}
		if (result.isNotificationLost()) {
			Notification notification = notificationService.retrieve(result
					.getTransactionNo(), result.getExtTxnNo(), result
					.getCharged(), SynchronizingMethod.CHECK);
			applicationEvents.notificationWasReceived(notification);
		} else {
			transaction.regardedAsDeadAt(now());
			transactionRepository.store(transaction);
		}
	}
}
