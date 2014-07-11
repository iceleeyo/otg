package com.springtour.otg.infrastructure.channel.cmbc;

import java.util.Map;

import lombok.Setter;

import com.springtour.otg.application.exception.CannotLaunchSecurityProcedureException;
import com.springtour.otg.application.exception.UnavailableChannelException;
import com.springtour.otg.domain.model.transaction.Transaction;
import com.springtour.otg.infrastructure.channel.AbstractRequestWithoutCardAdapter;

public class CmbcRequestWithoutCardServiceImpl extends
		AbstractRequestWithoutCardAdapter {

	public CmbcRequestWithoutCardServiceImpl(String channel) {
		super(channel);
	}

	@Setter
	private CmbcRequestUrlAssembler cmbcRequestUrlAssembler;

	@Override
	public String request(Transaction transaction, String returnUrl,
			Map customParams) throws UnavailableChannelException,
			CannotLaunchSecurityProcedureException {
		try {
			return cmbcRequestUrlAssembler
				.assembleUrl(transaction,returnUrl);
		} catch (Exception e) {
			throw new CannotLaunchSecurityProcedureException(e.getMessage(), e);
		}
	}

}
