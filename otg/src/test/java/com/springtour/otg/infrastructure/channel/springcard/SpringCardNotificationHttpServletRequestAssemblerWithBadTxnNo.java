/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.infrastructure.channel.springcard;

import com.springtour.otg.domain.model.transaction.Transaction;
import org.springframework.mock.web.MockHttpServletRequest;

/**
 *
 * @author Future
 */
public class SpringCardNotificationHttpServletRequestAssemblerWithBadTxnNo extends SpringCardNotificationHttpServletRequestAssemblerImpl {

    

    @Override
    public MockHttpServletRequest assemble(Transaction transaction) {
        MockHttpServletRequest request = super.assemble(transaction);
        request.setParameter("BillNo", "12313");
        return request;
    }
}
