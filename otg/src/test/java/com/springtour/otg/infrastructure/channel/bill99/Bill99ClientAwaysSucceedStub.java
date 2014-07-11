/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.infrastructure.channel.bill99;

import com.springtour.otg.application.exception.CannotMapIdentityTypeException;
import com.springtour.otg.domain.model.transaction.CardInfo;
import com.springtour.otg.domain.model.transaction.Transaction;
import com.springtour.otg.domain.shared.IdentityType;
import com.springtour.otg.infrastructure.channel.AbstractRequestWithCardInfoAdapter;
import com.springtour.otg.application.exception.StatusCode;

/**
 *
 * @author Future
 */
public class Bill99ClientAwaysSucceedStub extends AbstractRequestWithCardInfoAdapter{

    @Override
    public String request(Transaction transaction, CardInfo cardInfo) {
        return StatusCode.SUCCESS;
    }
    
    public Bill99ClientAwaysSucceedStub(String channel) {
        super(channel);
    }

    @Override
    public String dialect(IdentityType identityType) throws CannotMapIdentityTypeException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
