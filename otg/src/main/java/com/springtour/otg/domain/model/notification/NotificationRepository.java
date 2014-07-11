package com.springtour.otg.domain.model.notification;

import java.util.List;

import com.springtour.otg.domain.model.transaction.TransactionNo;

public interface NotificationRepository {

	void store(Notification notification);

	Notification find(String sequence);

	List<Notification> find(TransactionNo transactionNo);

	String nextSequence();
}
