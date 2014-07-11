package com.springtour.otg.interfaces.transacting.facade.rs;

import com.springtour.otg.interfaces.transacting.facade.dto.TransactionDto;

public class RequestWithoutCardInfoRs extends NewTransactionRs {

    /**
     * 渠道商交易请求地址
     */
    private String requestUrl;

    public RequestWithoutCardInfoRs(String success, TransactionDto transaction,
            String requestUrl) {
        super(success, transaction);
        this.requestUrl = requestUrl;
    }
    
    public RequestWithoutCardInfoRs(String statusCode) {
        super(statusCode);
    }

    public String getRequestUrl() {
        return requestUrl;
    }
}
