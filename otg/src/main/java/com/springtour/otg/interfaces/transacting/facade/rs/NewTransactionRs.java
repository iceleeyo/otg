package com.springtour.otg.interfaces.transacting.facade.rs;

import com.springtour.otg.interfaces.transacting.facade.dto.TransactionDto;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.StandardToStringStyle;

public class NewTransactionRs extends GenericRs {

    /**
     * 交易数据
     */
    private TransactionDto transaction;

    public NewTransactionRs(String success, TransactionDto transaction) {
        super(success);
        this.transaction = transaction;
    }

    public NewTransactionRs(String statusCode) {
        super(statusCode);
    }

    public TransactionDto getTransaction() {
        return transaction;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this,
                StandardToStringStyle.SHORT_PREFIX_STYLE);
    }
}
