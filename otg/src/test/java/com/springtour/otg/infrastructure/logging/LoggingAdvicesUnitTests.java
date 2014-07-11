package com.springtour.otg.infrastructure.logging;

import static org.junit.Assert.*;

import org.jmock.*;
import org.junit.*;
import org.springframework.aop.framework.*;

import com.springtour.otg.application.exception.*;
import com.springtour.test.*;

public class LoggingAdvicesUnitTests extends AbstractJMockUnitTests {

	private SimpleTypeSample simpleTypeSampleTarget = new SimpleTypeSample();
	private SimpleTypeSample simpleTypeSampleProxy;

	private LoggingSupport loggingSupport = context.mock(LoggingSupport.class);

	private LoggingAdvices loggingAdvices = new LoggingAdvices();

	@Before
	public void injects() {
		loggingAdvices.setLoggingSupport(loggingSupport);
	}

	@Before
	public void enhancesTargetByAspectJ() {
		this.simpleTypeSampleProxy = anEnhancedSimpleTypeTarget();
	}

	private SimpleTypeSample anEnhancedSimpleTypeTarget() {
		ProxyFactory factory = new ProxyFactory(simpleTypeSampleTarget);
		factory.addAdvice(loggingAdvices);
		return (SimpleTypeSample) factory.getProxy();
	}

	@Test
	public void notifiesLoggingWhenAMethodReturnsArgInvokedGivenASimpleTypeSample()
			throws Exception {

		final String arg = "arg";

		context.checking(new Expectations() {
			{
				oneOf(loggingSupport)
						.notifyRequest(
								SimpleTypeSample.class.getName()
										+ ".aMethodReturnsArg",
								new Object[] { arg });

				oneOf(loggingSupport)
						.notifyResponse(
								SimpleTypeSample.class.getName()
										+ ".aMethodReturnsArg", arg);
			}
		});

		String returning = simpleTypeSampleProxy.aMethodReturnsArg(arg);
		assertEquals(returning, arg);
	}

	@Test
	public void notifiesLoggingWhenAVoidMethodInvokedGivenASimpleTypeSample()
			throws Exception {

		final String arg = "arg";

		context.checking(new Expectations() {
			{
				oneOf(loggingSupport).notifyRequest(
						SimpleTypeSample.class.getName() + ".aVoidMethod",
						new Object[] { arg });

				oneOf(loggingSupport)
						.notifyResponse(
								SimpleTypeSample.class.getName()
										+ ".aVoidMethod", null);
			}
		});

		simpleTypeSampleProxy.aVoidMethod(arg);
	}

	@Test(expected = RuntimeException.class)
	public void notifiesLoggingWhenAMethodThrowsRuntimeExceptionInvokedGivenASimpleTypeSample()
			throws Exception {

		final String arg = "arg";

		context.checking(new Expectations() {
			{
				oneOf(loggingSupport).notifyRequest(
						SimpleTypeSample.class.getName()
								+ ".aMethodThrowsRuntimeExceptionWithArg",
						new Object[] { arg });

				oneOf(loggingSupport).notifyCatching(
						with(equal(SimpleTypeSample.class.getName()
								+ ".aMethodThrowsRuntimeExceptionWithArg")),
						with(any(RuntimeException.class)));
			}
		});

		simpleTypeSampleProxy.aMethodThrowsRuntimeExceptionWithArg(arg);
	}

	@Test(expected = SimpleApplicationException.class)
	public void notifiesLoggingWhenAMethodThrowsApplicationExceptionInvokedGivenASimpleTypeSample()
			throws Exception {

		final String arg = "arg";

		context.checking(new Expectations() {
			{
				oneOf(loggingSupport).notifyRequest(
						SimpleTypeSample.class.getName()
								+ ".aMethodThrowsApplicationExceptionWithArg",
						new Object[] { arg });

				oneOf(loggingSupport)
						.notifyCatching(
								with(equal(SimpleTypeSample.class.getName()
										+ ".aMethodThrowsApplicationExceptionWithArg")),
								with(any(SimpleApplicationException.class)));
			}
		});

		simpleTypeSampleProxy.aMethodThrowsApplicationExceptionWithArg(arg);
	}

	public static class SimpleTypeSample {
		public String aMethodReturnsArg(String arg) {
			return arg;
		}

		public String aMethodThrowsRuntimeExceptionWithArg(String arg) {
			throw new RuntimeException(arg);
		}

		public String aMethodThrowsApplicationExceptionWithArg(String arg) {
			throw new SimpleApplicationException(arg);
		}

		public void aVoidMethod(String arg) {
		}
	}

}
