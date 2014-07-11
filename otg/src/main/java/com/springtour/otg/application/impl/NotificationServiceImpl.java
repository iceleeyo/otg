package com.springtour.otg.application.impl;

import static com.springtour.otg.domain.model.notification.SynchronizingMethod.*;
import java.util.*;

import org.springframework.transaction.annotation.*;

import com.springtour.otg.application.*;
import com.springtour.otg.application.exception.*;
import com.springtour.otg.domain.model.notification.*;
import com.springtour.otg.domain.model.transaction.*;
import com.springtour.otg.infrastructure.time.*;

public class NotificationServiceImpl implements NotificationService {
	@Transactional
	@Override
	public Notification receive(String channelId,
			Map<String, String> aOriginalNotification)
			throws CannotLaunchSecurityProcedureException,
			UnavailableChannelException, FakeNotificationException,
			UnavailableCurrencyException {

		Notification notification = notificationFactory.make(channelId,
				aOriginalNotification);
		notificationRepository.store(notification);
		return notification;
	}

	@Transactional
	@Override
	public Notification retrieve(TransactionNo transactionNo, String extTxnNo,
			Charge charged, SynchronizingMethod retrieveType) {
		Notification notification = null;
		Transaction transaction = transactionRepository.find(transactionNo);
		if (CHECK.sameCodeAs(retrieveType.getCode())) {
			notification = Notification.byCheck(notificationRepository
					.nextSequence(), clock.now(), transaction, extTxnNo,
					charged);
		} else if(MANUAL.sameCodeAs(retrieveType.getCode())){
			notification = Notification.handMake(notificationRepository
					.nextSequence(), clock.now(), transaction, extTxnNo,
					charged);
		} else {
			throw new CannotRetrieveSynchronizingMethodException(retrieveType.getCode());
		}
		notificationRepository.store(notification);
		return notification;
	}

	private NotificationFactory notificationFactory;
	private NotificationRepository notificationRepository;
	private TransactionRepository transactionRepository;
	private Clock clock;

	public void setClock(Clock clock) {
		this.clock = clock;
	}

	public void setTransactionRepository(
			TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}

	public void setNotificationFactory(NotificationFactory notificationFactory) {
		this.notificationFactory = notificationFactory;
	}

	public void setNotificationRepository(
			NotificationRepository notificationRepository) {
		this.notificationRepository = notificationRepository;
	}
}
