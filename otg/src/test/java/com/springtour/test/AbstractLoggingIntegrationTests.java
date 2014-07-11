package com.springtour.test;

import javax.annotation.*;

import org.jmock.*;
import org.jmock.integration.junit4.*;
import org.jmock.lib.legacy.*;
import org.junit.*;
import org.omg.CORBA.portable.*;
import org.springframework.test.annotation.*;

import com.springtour.otg.infrastructure.logging.*;
import com.springtour.otg.integration.*;

public abstract class AbstractLoggingIntegrationTests extends
		AbstractOtgIntegrationTests {

	protected Mockery context = new JUnit4Mockery() {
		{
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};

	private LoggingAdvices loggingAdvices;

	private LoggingSupport loggingSupport = context.mock(LoggingSupport.class);

	@Override
	public void onSetUp() throws Exception {
		super.onSetUp();

		loggingAdvices = (LoggingAdvices) super.getApplicationContext()
				.getBean("otg.LoggingAdvices");

		loggingAdvices.setLoggingSupport(loggingSupport);
	}

	@DirtiesContext
	// for replacing loggingSupport
	@Test(expected = Exception.class)
	public void notifiesLoggingWhenAMethodThrowsUncheckedInvoked()
			throws Throwable {
		context.checking(new Expectations() {
			{
				oneOf(loggingSupport).notifyRequest(entry(), args());

				oneOf(loggingSupport).notifyCatching(with(equal(entry())),
						with(any(Exception.class)));
			}
		});

		invokesAMethodWillCertainlyThrowAnException();
		context.assertIsSatisfied();
	}

	protected abstract void invokesAMethodWillCertainlyThrowAnException();

	protected abstract Object[] args();

	protected abstract String entry();
}
