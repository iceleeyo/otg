package com.springtour.otg.interfaces.transacting.facade.internal;

import lombok.Setter;

import com.springtour.otg.application.CheckingBatch;
import com.springtour.otg.interfaces.transacting.facade.AutoCheckingBatchFacade;
public class AutoCheckingBatchFacadeImpl implements AutoCheckingBatchFacade{
	@Setter
	private CheckingBatch checkingBatch;
	@Override
	public void run() {
		checkingBatch.run();
	}
}
