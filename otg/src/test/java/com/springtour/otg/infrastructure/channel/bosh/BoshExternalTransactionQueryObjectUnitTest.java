/*
 * Copyright(c) 2012-2014 SpringTour.Ltd. All Rights Reserved.
 * 
 */
package com.springtour.otg.infrastructure.channel.bosh;

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
import com.springtour.otg.infrastructure.channel.NotificationValidator;


/**
 * 此处为类说明
 * @author 010586
 * @date 2014-3-22
 */
public class BoshExternalTransactionQueryObjectUnitTest {
    private BoshExternalTransactionQueryObject target;
    private Configurations configurations = new Configurations();
    private BoshEncryption encryption = new BoshEncryption();
    private BoshNotificationValidatorImpl validator = new BoshNotificationValidatorImpl();
    @Before
    public void init() {
        target = new BoshExternalTransactionQueryObject("33");
        configurations.setGlobalApplicationUrl("http://192.168.1.186");
        configurations.setBoshNotifyUrl("/test.jsp");
        encryption.setConfigurations(configurations);
        validator.setConfigurations(configurations);
        target.setConfigurations(configurations);
        target.setEncryption(encryption);
        target.setValidator(validator);
    }
    
    @Test
    public void testQueryBy() throws ParseException{
        TransactionNo transactionNo = new TransactionNo("2014041141076222");
        OrderIdentity orderId = new OrderIdentity("0", "3120557",
                "SO-140315-1564");
        Channel channel = new Channel("22");
        Merchant merchant = new Merchant("上海春秋国旅", channel, "61",
                "315310018000169", "000000");
//        final Date creatDate = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date creatDate = sdf.parse("20140401130700");
        Transaction transaction = new Transaction(transactionNo, new Partner(),
                Money.valueOf(new BigDecimal("100")), creatDate, orderId,
                merchant, channel, Gateways.BOSH.code());
        ExternalTransactionQueryResult result = target.queryBy(transaction);
        assertEquals(Charge.SUCCESS, result.chargedFromSupplier());
        assertThat(result.amountFromSupplier().getAmount(), Matchers.comparesEqualTo(new BigDecimal(100)));
    }
    
}
