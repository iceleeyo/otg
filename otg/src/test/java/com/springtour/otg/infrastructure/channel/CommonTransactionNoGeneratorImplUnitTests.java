package com.springtour.otg.infrastructure.channel;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.springtour.otg.domain.model.merchant.Merchant;
import com.springtour.otg.domain.model.transaction.TransactionNo;
import com.springtour.otg.infrastructure.channel.CommonTransactionNoGeneratorImpl;
import com.springtour.test.AbstractJMockUnitTests;

public class CommonTransactionNoGeneratorImplUnitTests extends AbstractJMockUnitTests {
	private CommonTransactionNoGeneratorImpl target = new CommonTransactionNoGeneratorImpl();
	
	@Test
	public void returnsATransactionNoWhenGeneratesNextTransactionNoWith(){
		Merchant merchant = new Merchant();
		final String channelId = "09";
		final String nextTransactionNoSeq = "5";
		Date date = new Date();
		TransactionNo nextTransactionNo = target.nextTransactionNo(channelId, merchant, nextTransactionNoSeq, date);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        Assert.assertEquals(nextTransactionNo.getNumber(), sdf.format(date)
                + "00000" + nextTransactionNoSeq + channelId);
	}

}
