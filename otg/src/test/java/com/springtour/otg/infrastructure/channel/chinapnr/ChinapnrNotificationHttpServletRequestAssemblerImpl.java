/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.infrastructure.channel.chinapnr;

import com.springtour.otg.domain.model.transaction.Transaction;
import com.springtour.otg.infrastructure.channel.NotificationHttpServletRequestAssembler;
import com.springtour.otg.interfaces.transacting.facade.dto.TransactionDto;
import java.math.RoundingMode;
import javax.servlet.http.HttpServletRequest;
import org.springframework.mock.web.MockHttpServletRequest;

/**
 *
 * @author 001595
 */
public class ChinapnrNotificationHttpServletRequestAssemblerImpl implements NotificationHttpServletRequestAssembler {

    @Override
    public HttpServletRequest assemble(TransactionDto transaction) {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setMethod("POST");
        request.setRequestURI("/otg/chinapnr/receive.htm");
        request.setParameter("CmdId", ChinapnrConstants.COMMAND_TELPAY);
        request.setParameter("MerId", "");
        request.setParameter("TrxId", "1231313");
        request.setParameter("OrdId", transaction.getTransactionNo());
        request.setParameter("OrdAmt", String.valueOf(transaction.getAmount().setScale(2, RoundingMode.HALF_UP)));
        request.setParameter("GateId", "");
        request.setParameter("RetType", "");
        request.setParameter("RespCode", "00");
        request.setParameter("ChkValue", "12313");
        return request;
    }
    
    @Override
    public MockHttpServletRequest assemble(Transaction transaction) {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setMethod("POST");
        request.setRequestURI("/otg/chinapnr/receive.htm");
        request.setParameter("CmdId", ChinapnrConstants.COMMAND_TELPAY);
        request.setParameter("MerId", transaction.getMerchantCode());
        request.setParameter("TrxId", "1231313");
        request.setParameter("OrdId", transaction.getTransactionNo().getNumber());
        request.setParameter("OrdAmt", String.valueOf(transaction.getAmount().getAmount()));
        request.setParameter("GateId", transaction.gatewayChannelDialect());
        request.setParameter("RetType", "");
        request.setParameter("RespCode", "00");
        request.setParameter("ChkValue", "12313");
        return request;
    }
}
