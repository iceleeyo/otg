package com.springtour.otg.infrastructure.stub;

import javax.jms.Message;

import lombok.Getter;

import com.springtour.otg.domain.model.transaction.TransactionNo;
import com.springtour.otg.infrastructure.messaging.CheckingConsumer;

public class TestCheckingConsumerStub extends CheckingConsumer {
	@Getter
	private TransactionNo transactionNo;
	private static TestCheckingConsumerStub instance = new TestCheckingConsumerStub();
	
	private TestCheckingConsumerStub(){	
	}
	
	public static TestCheckingConsumerStub getInstance(){
		return instance;
	}

	@Override
	public void onMessage(Message message) {
		this.transactionNo = super.transactionNoFrom(message);
	}
}