package com.springtour.otg.domain.service;

import com.springtour.otg.application.exception.*;
import com.springtour.otg.domain.model.transaction.CardInfo;
import com.springtour.otg.domain.model.transaction.Transaction;
import com.springtour.otg.domain.shared.IdentityType;
import java.util.List;

public interface RequestWithCardInfoService {

    String request(Transaction transaction, CardInfo cardInfo) throws UnavailableChannelException, CannotMapIdentityTypeException, CannotLaunchSecurityProcedureException;

    List<IdentityType> availableIdentityTypes(String channelId) throws UnavailableChannelException, CannotMapIdentityTypeException;
}
