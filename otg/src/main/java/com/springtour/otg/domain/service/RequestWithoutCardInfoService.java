package com.springtour.otg.domain.service;

import com.springtour.otg.application.exception.CannotLaunchSecurityProcedureException;
import com.springtour.otg.application.exception.UnavailableChannelException;
import com.springtour.otg.domain.model.transaction.Transaction;
import java.util.Map;

public interface RequestWithoutCardInfoService {

    String request(Transaction transaction, String returnUrl, Map customParams) throws UnavailableChannelException, CannotLaunchSecurityProcedureException;
}
