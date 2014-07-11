package com.springtour.otg.infrastructure.channel.cmb;

import java.math.BigDecimal;

import com.springtour.otg.domain.model.notification.Charge;
import com.springtour.otg.domain.model.transaction.Transaction;
import com.springtour.otg.domain.shared.Money;
import com.springtour.otg.infrastructure.channel.ThrowableExternalTransactionQueryResult;

public class ThrowableExternalTransactionQueryResultConverter {
    public static ThrowableExternalTransactionQueryResult convertToExternalTransactionQueryResult(
        String[] decryptedData, Transaction transaction) {
        String tradeStatus = decryptedData[2];
        String settleAmount = decryptedData[3];
        String extTxnNo = transaction.getTransactionNo().getNumber();//查询时招行不会返回外部流水号，故放入本地流水号
        ThrowableExternalTransactionQueryResult result =
                new ThrowableExternalTransactionQueryResult(convert(tradeStatus), Money.valueOf(new BigDecimal(
                        settleAmount)), extTxnNo);
        return result;

    }

    /**
     * 0－已结帐，1－已撤销，2－部分结帐，3－退款，4－未结帐，5-无效状态，6－未知状态
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
