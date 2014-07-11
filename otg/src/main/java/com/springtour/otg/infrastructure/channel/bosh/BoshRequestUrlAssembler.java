/*
 * Copyright(c) 2012-2014 SpringTour.Ltd. All Rights Reserved.
 */
package com.springtour.otg.infrastructure.channel.bosh;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import lombok.Setter;

import com.springtour.otg.application.util.Configurations;
import com.springtour.otg.domain.model.transaction.Transaction;
import com.springtour.otg.infrastructure.logging.LoggerFactory;

/**
 * 此处为类说明
 * @author 010586
 * @date 2014-4-4
 */
public class BoshRequestUrlAssembler {

    @Setter
    private Configurations configurations;

    @Setter
    private BoshEncryption encryption;

    Logger log = LoggerFactory.getLogger();

    /**
     * 此处为类方法说明
     * @param
     * @return
     * @throws Exception
     ******************************************************************************* 
     * @creator ：010586
     * @date ：2014-4-4
     * @memo ：
     ** 
     */
    public String assembleUrl(Transaction transaction, String returnUrl, String ip) {
        // 上海银行交易金额保留到角分，故需在我们交易金额的基础上乘以100
        BigDecimal orderAmount = transaction.getAmount().getAmount().multiply(new BigDecimal(100)).setScale(0);
        StringBuilder url = new StringBuilder(configurations.getBoshRequestUrl());
        url.append("?");
        Map<String, String> parms = new HashMap<String, String>();
        parms.put("merchantID", transaction.getMerchantCode());
        parms.put("merOrderNum", transaction.getTransactionNo().getNumber());
        parms.put("merOrderAmt", orderAmount.toString());
        parms.put("curType", BoshConstants.CNY);
        parms.put("orderDate", new SimpleDateFormat("yyyyMMdd").format(transaction.getWhenRequested()));
        parms.put("orderTime", new SimpleDateFormat("HHmmss").format(transaction.getWhenRequested()));
        parms.put("merNotifyUrl", configurations.getBoshNotifyUrl());
        parms.put("merNotifySign", BoshConstants.NOTIFY_SIGN);
        parms.put("merGetGoodsUrl", returnUrl);
        parms.put("merGetGoodsSign", BoshConstants.GOODS_SIGN);
        String signDataStr =
                transaction.getMerchantCode() + "|" + transaction.getTransactionNo().getNumber() + "|"
                        + orderAmount.toString() + "|" + BoshConstants.CNY;
        encryption.encryptionForMap(transaction, signDataStr, parms);
        url.append(BoshUtils.joinMapValue(parms, '&'));
        return url.toString().substring(0, url.toString().length() - 1);
    }

}
