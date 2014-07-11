/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.infrastructure.channel;

import com.springtour.otg.domain.model.transaction.Transaction;
import com.springtour.otg.interfaces.transacting.facade.dto.TransactionDto;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author 001595
 */
public interface NotificationHttpServletRequestAssembler {

    HttpServletRequest assemble(TransactionDto transaction);
    
    HttpServletRequest assemble(Transaction transaction);
}
