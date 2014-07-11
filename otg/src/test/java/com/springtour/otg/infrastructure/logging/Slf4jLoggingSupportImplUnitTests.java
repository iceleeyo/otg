package com.springtour.otg.infrastructure.logging;

import org.apache.log4j.*;
import org.jmock.*;
import org.junit.*;

import com.springtour.test.*;

public class Slf4jLoggingSupportImplUnitTests extends AbstractJMockUnitTests {
	private Logger log = context.mock(Logger.class);
	private LoggingSupport loggingSupport = new Slf4jLoggingSupportImplTestStub();
	final String entry = "entry";

	@Test
	public void logsWhenNotifyRequestGivenNullArgs() throws Throwable {
		final String text = "Thread id=" + Thread.currentThread().getId() + ","
				+ entry + ",args=no args";
		final Object[] args = null;

		context.checking(new Expectations() {
			{

				oneOf(log).info(text);
			}
		});

		loggingSupport.notifyRequest(entry, args);
	}

	@Test
	public void logsWhenNotifyRequestGivenEmptyArgs() throws Throwable {
		final String text = "Thread id=" + Thread.currentThread().getId() + ","
				+ entry + ",args=no args";
		final Object[] args = new Object[] {};

		context.checking(new Expectations() {
			{

				oneOf(log).info(text);
			}
		});

		loggingSupport.notifyRequest(entry, args);
	}

	@Test
	public void logsWhenNotifyRequestGivenSingleArg() throws Throwable {
		final String text = "Thread id=" + Thread.currentThread().getId() + ","
				+ entry + ",args={1}";
		final Object[] args = new Object[] { "1" };

		context.checking(new Expectations() {
			{

				oneOf(log).info(text);
			}
		});

		loggingSupport.notifyRequest(entry, args);
	}

	@Test
	public void logsWhenNotifyRequestGivenArgsButContainsNull()
			throws Throwable {
		final String text = "Thread id=" + Thread.currentThread().getId() + ","
				+ entry + ",args={null,null}";
		final Object[] args = new Object[] { null, null };

		context.checking(new Expectations() {
			{

				oneOf(log).info(text);
			}
		});

		loggingSupport.notifyRequest(entry, args);
	}

	@Test
	public void logsWhenNotifyRequestGivenSeveralArgs() throws Throwable {
		final String text = "Thread id=" + Thread.currentThread().getId() + ","
				+ entry + ",args={1,2,3}";
		final Object[] args = new Object[] { "1", "2", "3" };

		context.checking(new Expectations() {
			{

				oneOf(log).info(text);
			}
		});

		loggingSupport.notifyRequest(entry, args);
	}

	@Test
	public void logsWhenNotifyResponseGivenNullResponse() throws Throwable {
		final String text = "Thread id=" + Thread.currentThread().getId() + ","
				+ entry + ",returning=no returning";
		final Object rs = null;

		context.checking(new Expectations() {
			{

				oneOf(log).info(text);
			}
		});

		loggingSupport.notifyResponse(entry, rs);
	}

	@Test
	public void logsWhenNotifyResponseGivenResponse() throws Throwable {
		final String text = "Thread id=" + Thread.currentThread().getId() + ","
				+ entry + ",returning=1";
		final Object rs = "1";

		context.checking(new Expectations() {
			{

				oneOf(log).info(text);
			}
		});

		loggingSupport.notifyResponse(entry, rs);
	}

	@Test
	public void logsWhenNotifyCatchingGivenApplicationException()
			throws Throwable {
		final String text = "Thread id=" + Thread.currentThread().getId() + ","
				+ entry + ",error=1";
		final Exception throwable = new Exception("1");

		context.checking(new Expectations() {
			{

				oneOf(log).error(text, throwable);
			}
		});

		loggingSupport.notifyCatching(entry, throwable);
	}

	@Test
	public void logsWhenNotifyCatchingGivenApplicationExceptionAndContextMsg()
			throws Throwable {
		final String text = "Thread id=" + Thread.currentThread().getId() + ","
				+ entry + ",error=abc,1";
		final Exception throwable = new Exception("1");
		final String contextMsg = "abc";

		context.checking(new Expectations() {
			{

				oneOf(log).error(text, throwable);
			}
		});

		loggingSupport.notifyCatching(entry, contextMsg, throwable);
	}

	@Test
	public void logsWhenNotifyMessageGivenResponse() throws Throwable {
		final String message = "message";
		final String text = "Thread id=" + Thread.currentThread().getId() + ","
				+ message;

		context.checking(new Expectations() {
			{

				oneOf(log).info(text);
			}
		});

		loggingSupport.notifyMessage(message);
	}

	public class Slf4jLoggingSupportImplTestStub extends
			Slf4jLoggingSupportImpl {
		@Override
		protected Logger getLogger() {
			return log;
		}
	}
}
