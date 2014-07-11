package com.springtour.otg.interfaces.transacting.facade.internal;

import org.jmock.Expectations;
import org.junit.Before;
import org.junit.Test;

import com.springtour.otg.application.CheckingBatch;
import com.springtour.test.AbstractJMockUnitTests;

public class AutoCheckingBatchFacadeImplUnitTests extends
		AbstractJMockUnitTests {
	private AutoCheckingBatchFacadeImpl target=new AutoCheckingBatchFacadeImpl();
	private CheckingBatch checkingBatch = context.mock(CheckingBatch.class);
	
	@Before
	public void injects() {
		target.setCheckingBatch(checkingBatch);
	}
	
	@Test
	public void testAutoCheckingBatch(){
		
		context.checking(new Expectations() {
			{
				allowing(checkingBatch).run();
			}
		});
		target.run();
	}
}
