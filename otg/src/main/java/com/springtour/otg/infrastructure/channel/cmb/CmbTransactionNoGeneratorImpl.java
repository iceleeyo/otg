package com.springtour.otg.infrastructure.channel.cmb;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.springtour.otg.domain.model.merchant.Merchant;
import com.springtour.otg.domain.model.transaction.TransactionNo;
import com.springtour.otg.infrastructure.channel.AbstractTransactionNoGenerator;

public class CmbTransactionNoGeneratorImpl extends AbstractTransactionNoGenerator {

    public CmbTransactionNoGeneratorImpl(String channel) {
        super(channel);
    }

    private final static String DATE_PATTERN = "yyMMdd";

    private final static String SEQ_PATTERN = "000000";

    @Override
    public TransactionNo nextTransactionNo(String channelId, Merchant merchant, String transactionNoSequence,
        Date whenRequested) {
        final SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
        final DecimalFormat seqDf = new DecimalFormat(SEQ_PATTERN);
        StringBuilder transactionNo = new StringBuilder();
        transactionNo.append(sdf.format(whenRequested));
        transactionNo.append(seqDf.format(Long.valueOf(transactionNoSequence)).substring(2));
        return new TransactionNo(transactionNo.toString());
    }

}
