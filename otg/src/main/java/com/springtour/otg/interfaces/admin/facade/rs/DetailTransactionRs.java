/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.interfaces.admin.facade.rs;

import com.springtour.otg.interfaces.admin.facade.dto.TransactionDto;

/**
 *
 * @author 006874
 */
public class DetailTransactionRs extends GenericRs {

    private TransactionDto txnDto;

    public DetailTransactionRs(String statusCode, TransactionDto txnDto) {
        super(statusCode);
        this.txnDto = txnDto;
    }

    public DetailTransactionRs(String statusCode, String message) {
        super(statusCode, message);
    }

    public TransactionDto getTxnDto() {
        return txnDto;
    }
    @Override
    public String toString() {
        return super.toString();//查询rs不记录查询结果，否则结果集太大
    }
}
