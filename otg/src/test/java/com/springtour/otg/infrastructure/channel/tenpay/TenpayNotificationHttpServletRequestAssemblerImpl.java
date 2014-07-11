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
public class TenpayNotificationHttpServletRequestAssemblerImpl implements NotificationHttpServletRequestAssembler {

    @Override
    public HttpServletRequest assemble(TransactionDto transaction) {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setMethod("POST");
        request.setRequestURI("/otg/tenpay/receive.htm");
        request.setParameter("MerId", "");
        request.setParameter("transaction_id", "1231313");
        request.setParameter("out_trade_no", transaction.getTransactionNo());
        request.setParameter("total_fee", String.valueOf(transaction.getAmount().multiply(TenpayConstants.AMOUNT_CONVERTION_SCALE)));
        request.setParameter("GateId", "");
        request.setParameter("trade_mode", "1");
        request.setParameter("trade_state", "0");
        request.setParameter("sign", "12313");
        return request;
    }
    @Override
    public MockHttpServletRequest assemble(Transaction transaction) {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setMethod("POST");
        request.setRequestURI("/otg/tenpay/receive.htm");
        request.setParameter("MerId", transaction.getMerchantCode());
        request.setParameter("transaction_id", "1231313");
        request.setParameter("out_trade_no", transaction.getTransactionNo().getNumber());
        request.setParameter("total_fee", String.valueOf(transaction.getAmount().getAmount().multiply(TenpayConstants.AMOUNT_CONVERTION_SCALE)));
        request.setParameter("GateId", transaction.gatewayChannelDialect());
        request.setParameter("trade_mode", "1");
        request.setParameter("trade_state", "0");
        request.setParameter("sign", "12313");
        return request;
    }
}
