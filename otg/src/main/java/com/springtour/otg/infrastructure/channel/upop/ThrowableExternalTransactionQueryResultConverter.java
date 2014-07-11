package com.springtour.otg.infrastructure.channel.upop;

import java.math.BigDecimal;

import com.springtour.otg.domain.model.notification.Charge;
import com.springtour.otg.domain.model.transaction.Transaction;
import com.springtour.otg.domain.shared.Money;
import com.springtour.otg.infrastructure.channel.ThrowableExternalTransactionQueryResult;

public class ThrowableExternalTransactionQueryResultConverter {
    public static ThrowableExternalTransactionQueryResult convertToExternalTransactionQueryResult(
        String[] decryptedData, Transaction transaction) {
        String tradeStatus = "";
        String orderAmount = "";
        String extTxnNo = "";
        for (int i = 0; i < decryptedData.length; i++) {
            String[] queryResultArr = decryptedData[i].split("=");
            // 处理商户业务逻辑
            if (queryResultArr.length >= 2 && "queryResult".equals(queryResultArr[0])) {
                tradeStatus = decryptedData[i].substring(queryResultArr[0].length() + 1);
                continue;
            }
            if (queryResultArr.length >= 2 && "cupReserved".equals(queryResultArr[0])) {
                orderAmount = queryResultArr[2].split("&")[0];
                continue;
            }
            if (queryResultArr.length >= 2 && "traceNumber".equals(queryResultArr[0])) {
                extTxnNo = decryptedData[i].substring(queryResultArr[0].length() + 1);
                continue;
            }
        }

        ThrowableExternalTransactionQueryResult result =
                new ThrowableExternalTransactionQueryResult(convert(tradeStatus), Money.valueOf(new BigDecimal(
                        orderAmount).divide(new BigDecimal(100))), extTxnNo);
        return result;

    }

    /**
     * 0：成功； 1：失败； 2：处理中； 3：无此交易 。
     * 
     * @param tradeStatus
     * @return
     */
    private static Charge convert(String tradeStatus) {
        if ("0".equals(tradeStatus)) {
            return Charge.SUCCESS;
        }
        return null;
    }
}
