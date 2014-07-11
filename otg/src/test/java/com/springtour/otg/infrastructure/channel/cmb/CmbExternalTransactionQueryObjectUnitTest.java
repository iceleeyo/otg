/*
 * Copyright(c) 2012-2014 SpringTour.Ltd. All Rights Reserved.
 * 
 */
package com.springtour.otg.infrastructure.channel.cmb;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import com.springtour.otg.application.util.Configurations;
import com.springtour.otg.domain.model.channel.Channel;
import com.springtour.otg.domain.model.channel.Gateways;
import com.springtour.otg.domain.model.merchant.Merchant;
import com.springtour.otg.domain.model.notification.Charge;
import com.springtour.otg.domain.model.partner.Partner;
import com.springtour.otg.domain.model.transaction.OrderIdentity;
import com.springtour.otg.domain.model.transaction.Transaction;
import com.springtour.otg.domain.model.transaction.TransactionNo;
import com.springtour.otg.domain.shared.Money;
import com.springtour.otg.infrastructure.channel.ExternalTransactionQueryResult;


/**
 * 此处为类说明
 * @author 010586
 * @date 2014-3-22
 */
public class CmbExternalTransactionQueryObjectUnitTest {
    private CmbExternalTransactionQueryObject target;
    private Configurations configurations = new Configurations();
    
    @Before
    public void init() {
        target = new CmbExternalTransactionQueryObject("21");
        configurations.setCmbBranchId("0021");
        target.setConfigurations(configurations);
    }
    
    @Test
    public void testQueryBy() throws ParseException{
        TransactionNo transactionNo = new TransactionNo("1403277991");
        OrderIdentity orderId = new OrderIdentity("0", "3120557",
                "SO-140315-1564");
        Channel channel = new Channel("21");
        Merchant merchant = new Merchant("上海春秋国旅", channel, "61",
                "000056", "Aa12345678901234");
//        final Date creatDate = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date creatDate = sdf.parse("20140327");
        Transaction transaction = new Transaction(transactionNo, new Partner(),
                Money.valueOf(new BigDecimal("250")), creatDate, orderId,
                merchant, channel, Gateways.CMB.code());
        ExternalTransactionQueryResult result = target.queryBy(transaction);
        assertEquals(Charge.SUCCESS, result.chargedFromSupplier());
        assertThat(result.amountFromSupplier().getAmount(), Matchers.comparesEqualTo(new BigDecimal(0.1).setScale(2, RoundingMode.HALF_UP)));
    }
    
}
