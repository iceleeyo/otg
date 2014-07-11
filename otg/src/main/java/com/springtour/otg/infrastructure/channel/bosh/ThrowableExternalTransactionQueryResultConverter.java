/*
 * Copyright(c) 2012-2014 SpringTour.Ltd. All Rights Reserved.
 */
package com.springtour.otg.infrastructure.channel.bosh;

import java.math.BigDecimal;

import com.springtour.otg.domain.model.notification.Charge;
import com.springtour.otg.domain.shared.Money;
import com.springtour.otg.infrastructure.channel.ThrowableExternalTransactionQueryResult;

/**
 * 此处为类说明
 * @author 010586
 * @date 2014-4-11
 */
public class ThrowableExternalTransactionQueryResultConverter {

    public static ThrowableExternalTransactionQueryResult convertToExternalTransactionQueryResult(String[] decryptedData) {
        String[] orderResult = decryptedData[0].split("\\|");
        String tradeStatus = orderResult[1];
        String orderAmount = orderResult[2];
        String extTxnNo = orderResult[0];
        ThrowableExternalTransactionQueryResult result =
                new ThrowableExternalTransactionQueryResult(convert(tradeStatus), Money.valueOf(new BigDecimal(
                        orderAmount).divide(new BigDecimal(100))), extTxnNo);
        return result;

    }

    //tradeStatus 0： 成功,1： 失败,2： 处理中,3： 可疑
    private static Charge convert(String tradeStatus) {
        if ("0".equals(tradeStatus)) {
            return Charge.SUCCESS;
        }
        return null;
    }
}
