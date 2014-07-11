package com.springtour.otg.application.exception;

import com.springtour.otg.domain.model.transaction.TransactionNo;

/**
 * 重复应答异常
 * 
 * @author Hippoom
 * 
 */
public class DuplicateResponseException extends AbstractCheckedApplicationException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public DuplicateResponseException(TransactionNo transactionNo, String notificationSequence) {
        super("Duplicate notification,transactionNo="
                + transactionNo.getNumber() + ",notification sequance=" + notificationSequence);
    }
}
