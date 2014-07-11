/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.application.exception;

import com.springtour.otg.domain.model.transaction.Transaction;

/**
 *
 * @author 006874
 */
public class CannotMakePaymentException extends AbstractCheckedApplicationExceptionWithStatusCode {

    public CannotMakePaymentException(Transaction transaction) {
        super(transaction.toString());
    }

    public CannotMakePaymentException(Transaction transaction, String statusCode) {
        super(transaction.toString() + ",status code=" + statusCode);
    }

    public CannotMakePaymentException(Transaction transaction, Throwable throwable) {
        super(transaction.toString(), throwable);
    }

    @Override
    public String getStatusCode() {
        return StatusCode.CANNOT_MAKE_PAYMENT;
    }
}
