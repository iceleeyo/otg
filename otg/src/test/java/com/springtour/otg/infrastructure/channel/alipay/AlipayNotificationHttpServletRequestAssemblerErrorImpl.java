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
public class AlipayNotificationHttpServletRequestAssemblerErrorImpl implements NotificationHttpServletRequestAssembler{

    private String amount;
    private String tradeNo = "12312312312";
    private String outTradeNo;
    private String sign = "12313";
    
    @Override
    public HttpServletRequest assemble(TransactionDto transaction) {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setMethod("POST");
        request.setRequestURI("/otg/chinapnr/receive.htm");
        request.setParameter("MerId", "");
        request.setParameter("trade_no", tradeNo);
        request.setParameter("out_trade_no", outTradeNo);
        request.setParameter("total_fee", amount);
        request.setParameter("GateId", "");
        request.setParameter("RetType", "");
        request.setParameter("trade_status", "TRADE_SUCCESS");
        request.setParameter("sign", sign);
        return request;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public MockHttpServletRequest assemble(Transaction transaction) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
