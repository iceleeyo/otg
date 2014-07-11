package com.springtour.otg.infrastructure.channel;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;

import com.springtour.otg.domain.model.merchant.Merchant;
import com.springtour.otg.domain.model.transaction.TransactionNo;
import com.springtour.otg.integration.AbstractOtgApplicationContextIntegrationTests;

public class ChannelDispatcherIntegrationTests extends AbstractOtgApplicationContextIntegrationTests{
	@Resource(name = "otg.ChannelDispatcher")
	private ChannelDispatcher target;
	private Merchant merchant = new Merchant();
	private String transactionNoSequence = "00000";
	private Date whenRequested = new Date();
	private String channelId = "17";
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	
	@Test
	public void testCmbcTransactionNoGenerator(){
		merchant.setCode("305010601390507");
		TransactionNo transactionNo = target.nextTransactionNo(channelId, merchant, transactionNoSequence, whenRequested);
		
		Assert.assertEquals(transactionNo.getNumber(), "90507"+sdf.format(whenRequested)
                +  transactionNoSequence + channelId);
	}
	
	@Test
	public void testCommonTransactionNoGenerator(){
		channelId = "05";
		TransactionNo transactionNo = target.nextTransactionNo(channelId, merchant, transactionNoSequence, whenRequested);
		
		Assert.assertEquals(transactionNo.getNumber(), sdf.format(whenRequested)
                + "000000" + channelId);
	}
}
