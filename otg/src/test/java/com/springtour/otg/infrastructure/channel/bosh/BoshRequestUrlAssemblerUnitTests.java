/*
 * Copyright(c) 2012-2014 SpringTour.Ltd. All Rights Reserved.
 * 
 */
package com.springtour.otg.infrastructure.channel.bosh;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.springtour.otg.application.util.Configurations;
import com.springtour.otg.domain.model.channel.Channel;
import com.springtour.otg.domain.model.channel.Gateways;
import com.springtour.otg.domain.model.merchant.Merchant;
import com.springtour.otg.domain.model.partner.Partner;
import com.springtour.otg.domain.model.transaction.OrderIdentity;
import com.springtour.otg.domain.model.transaction.Transaction;
import com.springtour.otg.domain.model.transaction.TransactionNo;
import com.springtour.otg.domain.shared.Money;


/**
 * 此处为类说明
 * @author 010586
 * @date 2014-4-4
 */
public class BoshRequestUrlAssemblerUnitTests {
    private BoshRequestUrlAssembler target;
    private Configurations configurations;
    private BoshEncryption encryption;
    @Before
    public void init(){
        target = new BoshRequestUrlAssembler();
        configurations = new Configurations();
        encryption = new BoshEncryption();
        encryption.setConfigurations(configurations);
        configurations.setGlobalApplicationUrl("http://180.168.15.70:7001/");
        configurations.setBoshNotifyUrl("otg/upop/receive.htm");
        target.setConfigurations(configurations);
        target.setEncryption(encryption);
    }
    @Test
    public void test() {
        TransactionNo transactionNo = new TransactionNo("2014042448850533");
        OrderIdentity orderId = new OrderIdentity("9", "8003654",
                "8003654");
        Channel channel = new Channel("33");
        Merchant merchant = new Merchant("上海春秋国旅", channel, "61",
                "315310018000169", "000000");
        final Date creatDate = Calendar.getInstance().getTime();
        Transaction transaction = new Transaction(transactionNo, new Partner(),
                Money.valueOf(new BigDecimal("1")), creatDate, orderId,
                merchant, channel, Gateways.BOSH.code());
        String returnUrl = "http://localhost:7001/otgtest/boshTest.jsp";
        System.out.println(target.assembleUrl(transaction , returnUrl, "192.168.1.186"));
    }
}
