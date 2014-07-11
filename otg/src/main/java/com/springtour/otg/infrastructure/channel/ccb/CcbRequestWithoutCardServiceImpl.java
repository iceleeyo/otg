package com.springtour.otg.infrastructure.channel.ccb;

import java.util.Map;

import lombok.Setter;

import com.springtour.otg.application.exception.CannotLaunchSecurityProcedureException;
import com.springtour.otg.application.exception.UnavailableChannelException;
import com.springtour.otg.domain.model.transaction.Transaction;
import com.springtour.otg.infrastructure.channel.AbstractRequestWithoutCardAdapter;

public class CcbRequestWithoutCardServiceImpl extends
		AbstractRequestWithoutCardAdapter {

	@Setter
	private AbstractCcbUrlAssembler ccbRequestUrlAssembler;

	public CcbRequestWithoutCardServiceImpl(String channel) {
		super(channel);
	}

	/**
	 * 建行直连无需配置 returnUrl和NotifyUrl, 在建行端配置
	 */
	@Override
	public String request(Transaction transaction, String returnUrl,
			Map customParams) throws UnavailableChannelException,
			CannotLaunchSecurityProcedureException {
		try {
			return ccbRequestUrlAssembler
					.assembleUrl(transaction, customParams);
		} catch (Exception e) {
			throw new CannotLaunchSecurityProcedureException(e.getMessage(), e);
		}
	}



}
