package com.springtour.otg.interfaces.transacting.facade.internal;

import javax.xml.rpc.ServiceException;

import org.springframework.remoting.jaxrpc.ServletEndpointSupport;

import com.springtour.otg.interfaces.transacting.facade.ChinapnrVoicePayTransactingServiceFacade;
import com.springtour.otg.interfaces.transacting.facade.arg.ChinapnrVoicePayRequestArgument;
import com.springtour.otg.interfaces.transacting.facade.result.ChinapnrVoicePayRequestResult;

public class AxisChinapnrVoicePayTransactingServiceFacadeImplTarget extends
		ServletEndpointSupport implements
		ChinapnrVoicePayTransactingServiceFacade {
	private ChinapnrVoicePayTransactingServiceFacade chinapnrVoicePayTransactingServiceFacade;

	protected void onInit() throws ServiceException {
		chinapnrVoicePayTransactingServiceFacade = (ChinapnrVoicePayTransactingServiceFacade) getApplicationContext()
				.getBean("otg.ChinapnrVoicePayTransactingServiceFacadeImpl");
	}

	@Override
	public ChinapnrVoicePayRequestResult request(
			ChinapnrVoicePayRequestArgument arg) {
		return chinapnrVoicePayTransactingServiceFacade.request(arg);
	}

	@Override
	public String request(String transactionNo, String cardInfo) {
		return chinapnrVoicePayTransactingServiceFacade.request(transactionNo,
				cardInfo);
	}

}
