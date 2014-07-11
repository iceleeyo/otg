package com.springtour.otg.infrastructure.channel.cmbc;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.springtour.otg.domain.model.merchant.Merchant;
import com.springtour.otg.domain.model.transaction.TransactionNo;
import com.springtour.otg.infrastructure.channel.AbstractTransactionNoGenerator;

public class CmbcTransactionNoGeneratorImpl extends AbstractTransactionNoGenerator{
	
	public CmbcTransactionNoGeneratorImpl(String channel) {
		super(channel);
	}
	
	private final static String SEQ_PATTERN = "000000";
	private final static String CHANNEL_PATTERN = "00";

	@Override
	public TransactionNo nextTransactionNo(String channelId, Merchant merchant, String transactionNoSequence,
			Date whenRequested) {
		//取商户号后五位
		String merchantCode = merchant.getCode().toString();
		merchantCode = merchantCode.substring(merchantCode.length()-5);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		DecimalFormat seqDf = new DecimalFormat(SEQ_PATTERN);
		DecimalFormat channelDf = new DecimalFormat(CHANNEL_PATTERN);
		StringBuilder transactionNo = new StringBuilder();
		transactionNo.append(merchantCode);
		transactionNo.append(sdf.format(whenRequested));
		transactionNo.append(seqDf.format(Long.valueOf(transactionNoSequence)).substring(1));
		transactionNo.append(channelDf.format(Integer.valueOf(channelId)));

		return new TransactionNo(transactionNo.toString());
	}
}
