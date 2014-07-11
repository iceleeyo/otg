package com.springtour.otg.interfaces.transacting.facade;

import com.springtour.otg.interfaces.transacting.facade.arg.ChinapnrVoicePayRequestArgument;
import com.springtour.otg.interfaces.transacting.facade.result.ChinapnrVoicePayRequestResult;

public interface ChinapnrVoicePayTransactingServiceFacade {
	ChinapnrVoicePayRequestResult request(ChinapnrVoicePayRequestArgument arg);

	String request(String transactionNo, String cardInfo);
}
