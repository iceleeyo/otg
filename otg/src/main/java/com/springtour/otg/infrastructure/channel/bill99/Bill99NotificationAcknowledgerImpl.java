/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.infrastructure.channel.bill99;

import com.springtour.otg.domain.model.notification.Notification;
import com.springtour.otg.domain.model.transaction.Transaction;
import com.springtour.otg.domain.model.transaction.TransactionRepository;
import com.springtour.otg.infrastructure.channel.NotificationAcknowledger;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author 005073
 */
public class Bill99NotificationAcknowledgerImpl implements NotificationAcknowledger {

    @Override
    public void acknowledge(HttpServletRequest request, HttpServletResponse response, Notification notification) throws IOException {
        if (notification == null) {
            return;
        }
        Transaction transaction = transactionRepository.find(notification.getTxnNo());
        String ack = GenerateRequestData.generatePurchaseTr4XmlData(transaction.getMerchantCode(), transaction.getMerchant().getKey(), notification.getExtTxnNo());
        response.getWriter().write(ack);
    }
    private TransactionRepository transactionRepository;

    public void setTransactionRepository(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }
}
