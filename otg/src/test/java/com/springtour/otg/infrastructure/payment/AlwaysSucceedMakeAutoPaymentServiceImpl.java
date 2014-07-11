/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.infrastructure.payment;

/**
 *
 * @author 001595
 */
public class AlwaysSucceedMakeAutoPaymentServiceImpl implements MakeAutoPaymentService {

    @Override
    public String makePayment(String orderId, String amount, String currency, String transactionNo, String channelId, String whenResponsed, String merchantCode, String chargeFor) {
        return "0";
    }
}
