/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.interfaces.admin.facade.rq;

/**
 *
 * @author 006874
 */
public class HandMakeRq extends GenericRq {
   private String transactionNo;
   private String extTxnNo;
   private String charged;

    public String getCharged() {
        return charged;
    }

    public void setCharged(String charged) {
        this.charged = charged;
    }

    public String getExtTxnNo() {
        return extTxnNo;
    }

    public void setExtTxnNo(String extTxnNo) {
        this.extTxnNo = extTxnNo;
    }

    public String getTransactionNo() {
        return transactionNo;
    }

    public void setTransactionNo(String transactionNo) {
        this.transactionNo = transactionNo;
    }
}
