/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.interfaces.admin.facade.rs;

import com.springtour.otg.interfaces.admin.facade.dto.TransactionDto;
import java.util.List;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.StandardToStringStyle;

/**
 *
 * @author 001595
 */
public class ListTransactionsRs extends GenericRs {

    private List<TransactionDto> transactions;
    private long total;

    public ListTransactionsRs(String statusCode, List<TransactionDto> transactions, long total) {
        super(statusCode);
        this.transactions = transactions;
        this.total = total;
    }

    public ListTransactionsRs(String statusCode, String message) {
        super(statusCode, message);
    }

    public long getTotal() {
        return total;
    }

    public List<TransactionDto> getTransactions() {
        return transactions;
    }@Override
    public String toString() {
        return super.toString();//查询rs不记录查询结果，否则结果集太大
    }
}
