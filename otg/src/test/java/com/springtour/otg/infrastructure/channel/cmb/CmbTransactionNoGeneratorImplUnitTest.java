package com.springtour.otg.infrastructure.channel.cmb;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.springtour.otg.domain.model.merchant.Merchant;
import com.springtour.otg.domain.model.transaction.TransactionNo;


public class CmbTransactionNoGeneratorImplUnitTest {
	private CmbTransactionNoGeneratorImpl target;
	private String channelId = "21";
	
	@Before
	public void init(){
		target = new CmbTransactionNoGeneratorImpl(channelId);
	}
	
	@Test
	public void test(){
		Merchant merchant = new Merchant();
		final String nextTransactionNoSeq = "5";
		Date whenRequested = new Date();
		
		TransactionNo nextTransactionNo = target.nextTransactionNo(channelId, merchant, nextTransactionNoSeq, whenRequested);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
//		System.out.println(nextTransactionNo);
		Assert.assertEquals(nextTransactionNo.getNumber(), sdf.format(whenRequested)
                + "000" + nextTransactionNoSeq);
	}
}
