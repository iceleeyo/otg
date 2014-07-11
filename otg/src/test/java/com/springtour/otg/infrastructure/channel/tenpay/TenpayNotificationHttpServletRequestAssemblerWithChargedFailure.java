/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.infrastructure.channel.tenpay;

import com.springtour.otg.domain.model.transaction.Transaction;
import com.springtour.otg.infrastructure.channel.NotificationHttpServletRequestAssembler;
import com.springtour.otg.interfaces.transacting.facade.dto.TransactionDto;
import javax.servlet.http.HttpServletRequest;
import org.springframework.mock.web.MockHttpServletRequest;

/**
 *
 * @author 006874
 */
public class TenpayNotificationHttpServletRequestAssemblerWithChargedFailure extends TenpayNotificationHttpServletRequestAssemblerImpl {

    @Override
    public MockHttpServletRequest assemble(Transaction transaction) {
        MockHttpServletRequest request = super.assemble(transaction);
        request.setParameter("trade_state", "1");
        return request;
    }
}
