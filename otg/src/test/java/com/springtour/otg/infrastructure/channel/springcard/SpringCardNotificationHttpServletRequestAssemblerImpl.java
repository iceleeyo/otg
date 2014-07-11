/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.infrastructure.channel.springcard;

import com.springtour.otg.domain.model.transaction.Transaction;
import com.springtour.otg.infrastructure.channel.NotificationHttpServletRequestAssembler;
import com.springtour.otg.interfaces.transacting.facade.dto.TransactionDto;
import java.math.RoundingMode;
import javax.servlet.http.HttpServletRequest;
import org.springframework.mock.web.MockHttpServletRequest;

/**
 *
 * @author Future
 */
public class SpringCardNotificationHttpServletRequestAssemblerImpl implements NotificationHttpServletRequestAssembler {

    @Override
    public HttpServletRequest assemble(TransactionDto transaction) {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setMethod("POST");
        request.setRequestURI("/otg/springcard/receive.htm");
        request.setParameter("BillNo", transaction.getTransactionNo());
        request.setParameter("BillAmount", String.valueOf(transaction.getAmount().setScale(2, RoundingMode.HALF_UP)));
        request.setParameter("SUCC", "Y");
        request.setParameter("SignMD5", "12313");
        return request;
    }

    @Override
    public MockHttpServletRequest assemble(Transaction transaction) {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setMethod("POST");
        request.setRequestURI("/otg/springcard/receive.htm");
        request.setParameter("BillNo", transaction.getTransactionNo().getNumber());
        request.setParameter("BillAmount", String.valueOf(transaction.getAmount().getAmount()));
        request.setParameter("SUCC", "Y");
        request.setParameter("SignMD5", "12313");
        return request;
    }
}
