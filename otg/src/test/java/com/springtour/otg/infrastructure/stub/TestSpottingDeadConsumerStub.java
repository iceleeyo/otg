package com.springtour.otg.infrastructure.stub;

import javax.jms.Message;

import lombok.Getter;

import com.springtour.otg.domain.model.transaction.TransactionNo;
import com.springtour.otg.infrastructure.messaging.SpottingDeadConsumer;

public class TestSpottingDeadConsumerStub extends SpottingDeadConsumer {
	@Getter
	private TransactionNo transactionNo;
	private static TestSpottingDeadConsumerStub instance = new TestSpottingDeadConsumerStub();

	private TestSpottingDeadConsumerStub() {

	}

	public static TestSpottingDeadConsumerStub getInstance() {
		return instance;
	}

	@Override
	public void onMessage(Message message) {
		this.transactionNo = super.transactionNoFrom(message);
	}
}