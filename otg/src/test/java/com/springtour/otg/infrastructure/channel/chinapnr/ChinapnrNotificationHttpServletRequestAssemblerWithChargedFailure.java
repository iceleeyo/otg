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
public class ChinapnrNotificationHttpServletRequestAssemblerWithChargedFailure extends ChinapnrNotificationHttpServletRequestAssemblerImpl {

    @Override
    public MockHttpServletRequest assemble(Transaction transaction) {
        MockHttpServletRequest request = super.assemble(transaction);
        request.setParameter("RespCode", "85");
        return request;
    }
}
