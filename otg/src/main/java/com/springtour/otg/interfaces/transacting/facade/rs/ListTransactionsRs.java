/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.interfaces.transacting.facade.rs;

import com.springtour.otg.interfaces.transacting.facade.dto.TransactionDto;
import java.util.List;

/**
 *
 * @author 001595
 */
public class ListTransactionsRs extends GenericRs {

    private List<TransactionDto> transactions;

    public ListTransactionsRs(String statusCode, List<TransactionDto> transactions) {
        super(statusCode);
        this.transactions = transactions;
    }

    public ListTransactionsRs(String statusCode) {
        super(statusCode);
    }

    public List<TransactionDto> getTransactions() {
        return transactions;
    }
    
    @Override
    public String toString() {
        return super.toString();//查询rs不记录查询结果，否则结果集太大
    }
}
