/**
 * 
 */
package com.springtour.otg.infrastructure.channel.bill99gateway;
import java.util.Map;

import com.springtour.otg.application.exception.CannotLaunchSecurityProcedureException;
import com.springtour.otg.application.exception.UnavailableChannelException;
import com.springtour.otg.domain.model.transaction.Transaction;
import com.springtour.otg.infrastructure.channel.AbstractRequestWithoutCardAdapter;

/**
 * @author Future
 * 
 */
public class Bill99GatewayRequestWithoutCardServiceImpl extends
		AbstractRequestWithoutCardAdapter {
	private Bill99GatewayRequestParametersAssemble bill99GatewayRequestParametersAssemble;

	public Bill99GatewayRequestWithoutCardServiceImpl(String channel) {
		super(channel);
	}

	@Override
	public String request(Transaction transaction, String returnUrl,
			Map customParams) throws UnavailableChannelException,
			CannotLaunchSecurityProcedureException {
		try {
			return bill99GatewayRequestParametersAssemble.assembleMsg(
					transaction, returnUrl);
		} catch (Exception e) {
			throw new CannotLaunchSecurityProcedureException(e.getMessage(), e);
		}
	}

	public void setBill99GatewayRequestParametersAssemble(
			Bill99GatewayRequestParametersAssemble bill99GatewayRequestParametersAssemble) {
		this.bill99GatewayRequestParametersAssemble = bill99GatewayRequestParametersAssemble;
	}
}
