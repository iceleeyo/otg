package com.springtour.otg.infrastructure.channel.cmbc;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.springtour.otg.domain.model.merchant.Merchant;
import com.springtour.otg.domain.model.transaction.TransactionNo;
import com.springtour.test.AbstractJMockUnitTests;

public class CmbcTransactionNoGeneratorImplUnitTests extends AbstractJMockUnitTests {
	private CmbcTransactionNoGeneratorImpl target = new CmbcTransactionNoGeneratorImpl("17");
	
	@Test
	public void returnsATransactionNoWhenGeneratesNextTransactionNoWith(){
		Merchant merchant = new Merchant();
		merchant.setCode("305010601390507");
		final String channelId = "17";
		final String nextTransactionNoSeq = "5";
		Date date = new Date();
		TransactionNo nextTransactionNo = target.nextTransactionNo(channelId, merchant, nextTransactionNoSeq, date);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        Assert.assertEquals(nextTransactionNo.getNumber(), "90507"+sdf.format(date)
                + "0000" + nextTransactionNoSeq + channelId);
	}

}
