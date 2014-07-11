/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.infrastructure.channel.alipay;

import com.springtour.otg.domain.model.transaction.Transaction;
import org.springframework.mock.web.MockHttpServletRequest;

/**
 *
 * @author Future
 */
public class AlipayNotificationHttpServletRequestAssemblerWithBadTxnNo extends AlipayNotificationHttpServletRequestAssemblerImpl {

    @Override
    public MockHttpServletRequest assemble(Transaction transaction) {
        MockHttpServletRequest request = super.assemble(transaction);
        request.setParameter("out_trade_no", "12312315dad");
        return request;
    }
}
