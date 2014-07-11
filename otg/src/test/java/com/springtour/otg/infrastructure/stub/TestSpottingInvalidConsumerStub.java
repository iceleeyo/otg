package com.springtour.otg.infrastructure.stub;

import javax.jms.Message;

import lombok.Getter;

import com.springtour.otg.domain.model.transaction.TransactionNo;
import com.springtour.otg.infrastructure.messaging.SpottingInvalidConsumer;

public class TestSpottingInvalidConsumerStub extends SpottingInvalidConsumer {
	@Getter
	private TransactionNo transactionNo;
	private static TestSpottingInvalidConsumerStub instance = new TestSpottingInvalidConsumerStub();

	private TestSpottingInvalidConsumerStub() {

	}

	public static TestSpottingInvalidConsumerStub getInstance() {
		return instance;
	}

	@Override
	public void onMessage(Message message) {
		this.transactionNo = super.transactionNoFrom(message);
	}
}