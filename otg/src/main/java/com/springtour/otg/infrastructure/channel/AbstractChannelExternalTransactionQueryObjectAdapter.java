package com.springtour.otg.infrastructure.channel;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;

public abstract class AbstractChannelExternalTransactionQueryObjectAdapter
		implements ExternalTransactionQueryObject {

	@Getter
	private String channel;

	public AbstractChannelExternalTransactionQueryObjectAdapter(String channel) {
		this.channel = channel;
	}

	public Set<String> supportedChannels() {
		HashSet<String> channels = new HashSet<String>();
		channels.add(channel);
		return channels;
	}
}
