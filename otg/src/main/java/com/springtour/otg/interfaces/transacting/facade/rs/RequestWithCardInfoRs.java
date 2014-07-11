package com.springtour.otg.interfaces.transacting.facade.rs;

import com.springtour.otg.interfaces.transacting.facade.dto.TransactionDto;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.StandardToStringStyle;

public class RequestWithCardInfoRs extends NewTransactionRs {

    public RequestWithCardInfoRs(String success, TransactionDto transaction) {
        super(success, transaction);
    }
    public RequestWithCardInfoRs(String statusCode) {
        super(statusCode);
    }
}
