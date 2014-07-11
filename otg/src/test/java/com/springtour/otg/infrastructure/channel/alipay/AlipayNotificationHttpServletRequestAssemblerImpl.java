/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.infrastructure.channel.alipay;

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
public class AlipayNotificationHttpServletRequestAssemblerImpl implements NotificationHttpServletRequestAssembler {

    @Override
    public HttpServletRequest assemble(TransactionDto transaction) {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setMethod("POST");
        request.setRequestURI("/otg/alipay/receive.htm");
        request.setParameter("MerId", "");
        request.setParameter("trade_no", "1231313");
        request.setParameter("out_trade_no", transaction.getTransactionNo());
        request.setParameter("total_fee", String.valueOf(transaction.getAmount().setScale(2, RoundingMode.HALF_UP)));
        request.setParameter("GateId", "");
        request.setParameter("RetType", "");
        request.setParameter("trade_status", "TRADE_SUCCESS");
        request.setParameter("sign", "12313");
        return request;
    }

    @Override
    public MockHttpServletRequest assemble(Transaction transaction) {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setMethod("POST");
        request.setRequestURI("/otg/alipay/receive.htm");
        request.setParameter("MerId", transaction.getMerchantCode());
        request.setParameter("trade_no", "1231313");
        request.setParameter("out_trade_no", transaction.getTransactionNo().getNumber());
        request.setParameter("total_fee", String.valueOf(transaction.getAmount().getAmount()));
        request.setParameter("GateId", transaction.gatewayChannelDialect());
        request.setParameter("RetType", "");
        request.setParameter("trade_status", "TRADE_SUCCESS");
        request.setParameter("sign", "12313");
        return request;
    }
}
