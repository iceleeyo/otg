package com.springtour.otg.infrastructure.channel.bosh;

import java.util.Map;

import lombok.Setter;

import com.springtour.otg.application.exception.CannotLaunchSecurityProcedureException;
import com.springtour.otg.application.exception.UnavailableChannelException;
import com.springtour.otg.domain.model.transaction.Transaction;
import com.springtour.otg.infrastructure.channel.AbstractRequestWithoutCardAdapter;

public class BoshRequestWithoutCardServiceImpl extends AbstractRequestWithoutCardAdapter {

    public BoshRequestWithoutCardServiceImpl(String channel) {
        super(channel);
    }

    @Setter
    private BoshRequestUrlAssembler assembler;

    @Override
    public String request(Transaction transaction, String returnUrl, Map customParams)
        throws UnavailableChannelException, CannotLaunchSecurityProcedureException {
        String ip = (String) customParams.get("surferIp");
        return assembler.assembleUrl(transaction, returnUrl, ip);
    }

}
