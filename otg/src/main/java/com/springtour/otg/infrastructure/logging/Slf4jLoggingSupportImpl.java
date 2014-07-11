package com.springtour.otg.infrastructure.logging;

import static com.springtour.otg.application.util.Constants.*;

import org.apache.log4j.*;

public class Slf4jLoggingSupportImpl implements LoggingSupport {
	private static final String NO_ARGS = "no args";
	private static final String NO_RETURNING = "no returning";
	private static final String ARGS = ",args=";
	private static final String RETURNING = ",returning=";
	private static final String ERROR = ",error=";

	private void logError(String entry, String contextMsg, Throwable throwable) {
		StringBuilder message = new StringBuilder();
		message.append(threadId());
		message.append(entry);
		message.append(ERROR);
		message.append(contextMsg);
		message.append(COMMA);
		message.append(throwable.getMessage());
		getLogger().error(message.toString(), throwable);

	}

	private void logError(String entry, Throwable throwable) {
		StringBuilder message = new StringBuilder();
		message.append(threadId());
		message.append(entry);
		message.append(ERROR);
		message.append(throwable.getMessage());
		getLogger().error(message.toString(), throwable);
	}

	/**
	 * 此处是为了能够通过子类化并override的方式进行单元测试
	 * 
	 * @return
	 */
	protected Logger getLogger() {
		return LoggerFactory.getLogger();//TODO 需要修改为支持slf4j
	}

	@Override
	public void notifyResponse(String entry, Object rs) {
		String message = RETURNING + NO_RETURNING;
		if (rs != null) {
			message = RETURNING + rs.toString();
		}
		getLogger().info(threadId() + entry + message);
	}

	@Override
	public void notifyRequest(String entry, Object... rqs) {
		String message = ARGS + NO_ARGS;
		if (rqs != null && rqs.length != 0) {
			StringBuilder rqBuilder = new StringBuilder(ARGS);
			rqBuilder.append(LEFT_BRACE);
			for (Object rq : rqs) {
				rqBuilder.append(rq).append(COMMA);
			}
			message = rqBuilder.toString().substring(0,
					rqBuilder.toString().length() - 1)
					+ RIGHT_BRACE;
		}
		getLogger().info(threadId() + entry + message);
	}

	private String threadId() {
		return "Thread id=" + Thread.currentThread().getId() + COMMA;
	}

	@Override
	public void notifyMessage(String message) {
		getLogger().info(threadId() + message);
	}

	@Override
	public void notifyCatching(String entry, Throwable throwable) {
		logError(entry, throwable);
	}

	@Override
	public void notifyCatching(String entry, String message, Throwable e) {
		logError(entry, message, e);

	}

}
