package com.springtour.otg.infrastructure.logging;

public interface LoggingSupport {
	/**
	 * 通知捕获到异常
	 */
	void notifyCatching(String entry, Throwable throwable);

	/**
	 * 通知捕获到异常（带有上下文语义）
	 */
	void notifyCatching(String entry, String message, Throwable e);

	void notifyResponse(String entry, Object rs);

	void notifyRequest(String entry, Object... rqs);

	void notifyMessage(String message);

}
