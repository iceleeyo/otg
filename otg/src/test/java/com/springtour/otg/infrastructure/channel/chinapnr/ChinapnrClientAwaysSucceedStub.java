/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.infrastructure.channel.chinapnr;

import com.springtour.otg.application.exception.CannotMapIdentityTypeException;
import com.springtour.otg.domain.model.transaction.CardInfo;
import com.springtour.otg.domain.model.transaction.Transaction;
import com.springtour.otg.domain.shared.IdentityType;
import com.springtour.otg.infrastructure.channel.AbstractRequestWithCardInfoAdapter;
import com.springtour.otg.application.exception.StatusCode;

/**
 *
 * @author 006874
 */
public class ChinapnrClientAwaysSucceedStub extends AbstractRequestWithCardInfoAdapter {



    @Override
    public String request(Transaction transaction, CardInfo cardInfo) {
       return StatusCode.SUCCESS;
    }

    public ChinapnrClientAwaysSucceedStub(String channel ){
        super(channel);
    }

    @Override
    public String dialect(IdentityType identityType) throws CannotMapIdentityTypeException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
