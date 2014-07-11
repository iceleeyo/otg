package com.springtour.otg.infrastructure.channel;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.springtour.otg.domain.model.merchant.Merchant;
import com.springtour.otg.domain.model.transaction.TransactionNo;
import com.springtour.otg.domain.model.transaction.TransactionNoGenerator;

public class CommonTransactionNoGeneratorImpl implements TransactionNoGenerator {
	private final static String DATE_PATTERN = "yyyyMMdd";
    private final static String SEQ_PATTERN = "000000";
    private final static String CHANNEL_PATTERN = "00";
    
    @Override
	public TransactionNo nextTransactionNo(String channelId, Merchant merchant, String transactionNoSequence,
            Date whenRequested) {
    	
        final SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
        final DecimalFormat seqDf = new DecimalFormat(SEQ_PATTERN);
        final DecimalFormat channelDf = new DecimalFormat(CHANNEL_PATTERN);
        StringBuilder transactionNo = new StringBuilder().append(
                sdf.format(whenRequested)).append(
                seqDf.format(Long.valueOf(transactionNoSequence))).append(
                channelDf.format(Long.valueOf(channelId)));

        return new TransactionNo(transactionNo.toString());
    }
}