 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.infrastructure.channel.springcard;

import com.springtour.otg.application.exception.AbstractCheckedApplicationException;
import com.springtour.otg.application.exception.CannotLaunchSecurityProcedureException;
import com.springtour.otg.application.util.Configurations;
import com.springtour.otg.application.util.Md5Encrypt;
import com.springtour.otg.domain.model.transaction.Transaction;
import com.springtour.otg.infrastructure.channel.AbstractRequestWithoutCardAdapter;
import com.springtour.otg.infrastructure.time.Clock;
import java.text.SimpleDateFormat;
import java.util.Map;

/**
 *
 * @author Future
 */
public class SpringCardRequestWithoutCardInfoAdapterImpl extends AbstractRequestWithoutCardAdapter {
    private Configurations configurations;
    private Clock clock;

    public SpringCardRequestWithoutCardInfoAdapterImpl(String channelId) {
        super(channelId);
    }

    @Override
    public String request(Transaction transaction, String returnUrl, Map customParams) throws CannotLaunchSecurityProcedureException {
        try {
            String merDate = new SimpleDateFormat("yyyyMMdd").format(clock.now());
            String sign = this.sign(transaction, returnUrl, merDate);
            return this.generateUrl(transaction, returnUrl, sign, merDate);
        } catch (Exception e) {
            throw new CannotLaunchSecurityProcedureException(e.getMessage(), e);
        }
    }

    private String sign(Transaction transaction, String returnUrl, String merDate) {
        StringBuilder sb = new StringBuilder();
        sb.append(transaction.getMerchant().getCode());
        sb.append(transaction.getTransactionNo().getNumber());
        sb.append(transaction.getAmount().getAmount().toString());
        sb.append(merDate);
        sb.append(SpringCardConstants.REQUEST_TYPE);
        sb.append(returnUrl);
        sb.append(transaction.getMerchant().getKey());
        return Md5Encrypt.md5(sb.toString());
    }

    private String generateUrl(Transaction transaction, String returnUrl, String sign, String merDate) {
        StringBuilder sb = new StringBuilder();
        sb.append(configurations.getSpringcardRequestUrl()).append("?");
        sb.append("MerCode=").append(transaction.getMerchant().getCode());
        sb.append("&BillNo=").append(transaction.getTransactionNo().getNumber());
        sb.append("&Amount=").append(transaction.getAmount().getAmount().toString());
        sb.append("&MerDate=").append(merDate);
        sb.append("&PayType=").append(SpringCardConstants.REQUEST_TYPE);
        sb.append("&MerUrl=").append(returnUrl);
        sb.append("&backStageUrl=").append(configurations.springcardNotifyUrl());
        sb.append("&SignMD5=").append(sign);
        return sb.toString();
    }

    public void setClock(Clock clock) {
        this.clock = clock;
    }

    public void setConfigurations(Configurations configurations) {
        this.configurations = configurations;
    }
}
