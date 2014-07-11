package com.springtour.otg.infrastructure.channel.alipaywap;

import java.util.Map;

import lombok.Setter;

import com.springtour.otg.application.exception.CannotLaunchSecurityProcedureException;
import com.springtour.otg.application.exception.UnavailableChannelException;
import com.springtour.otg.domain.model.transaction.Transaction;
import com.springtour.otg.infrastructure.channel.AbstractRequestWithoutCardAdapter;

public class AlipayWapRequestWithoutCardServiceImpl extends
		AbstractRequestWithoutCardAdapter {

	public AlipayWapRequestWithoutCardServiceImpl(String channel) {
		super(channel);
	}

	@Setter
	private AlipayWapRequestUrlAssembler assembler;

	@Override
	public String request(Transaction transaction, String returnUrl,
			Map customParams) throws UnavailableChannelException,
			CannotLaunchSecurityProcedureException {
		String ip = (String) customParams.get("surferIp");
		return assembler.assembleUrl(transaction, returnUrl, ip);
	}

}
