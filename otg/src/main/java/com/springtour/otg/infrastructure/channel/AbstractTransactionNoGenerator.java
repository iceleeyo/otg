package com.springtour.otg.infrastructure.channel;

import com.springtour.otg.domain.model.transaction.TransactionNoGenerator;

public abstract class AbstractTransactionNoGenerator implements TransactionNoGenerator {
	private String channel;
	
	public AbstractTransactionNoGenerator(String channel) {
        this.channel = channel;
    }
	
	public String getChannel() {
        return channel;
    }
}
