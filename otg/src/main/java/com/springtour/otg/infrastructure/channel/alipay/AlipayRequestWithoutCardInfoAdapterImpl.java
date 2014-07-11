/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.infrastructure.channel.alipay;

import com.springtour.otg.application.exception.CannotLaunchSecurityProcedureException;
import com.springtour.otg.domain.model.transaction.Transaction;
import com.springtour.otg.infrastructure.channel.AbstractRequestWithoutCardAdapter;
import java.util.Map;

/**
 *
 * @author Future
 */
public class AlipayRequestWithoutCardInfoAdapterImpl extends AbstractRequestWithoutCardAdapter {

    private AlipayHandlingService alipayHandlingService;
    private String notifyUrl;

    public AlipayRequestWithoutCardInfoAdapterImpl(String channel) {
        super(channel);
    }

    public void setAlipayHandlingService(AlipayHandlingService alipayHandlingService) {
        this.alipayHandlingService = alipayHandlingService;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    @Override
    public String request(Transaction transaction, String returnUrl, Map customParams) throws CannotLaunchSecurityProcedureException {
        try {
            return this.alipayHandlingService.payConvert(transaction, transaction.getMerchant().getKey(),
                    notifyUrl, returnUrl);
        } catch (Exception ex) {
            throw new CannotLaunchSecurityProcedureException(ex.getMessage(), ex);
        }
    }
}
