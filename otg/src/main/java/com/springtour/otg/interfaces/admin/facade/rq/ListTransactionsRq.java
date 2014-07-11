/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.interfaces.admin.facade.rq;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author 001595
 */
public class ListTransactionsRq extends GenericRq {

    private String transactionNoEq;
    private String stateEq;
    private String whenRequestedGt;
    private String whenRequestedLt;
    private String orderIdEq;
    private String applicationEq;
    private String orderNoEq;
    private String merchantCodeEq;
    private String channelIdEq;
    @Getter
    @Setter
    private String transactionTypeEq;
    @Getter
    @Setter
    private String partnerEq;
    private int firstResult;
    private int maxResults;

    public String getApplicationEq() {
        return applicationEq;
    }

    public void setApplicationEq(String applicationEq) {
        this.applicationEq = applicationEq;
    }

    public String getChannelIdEq() {
        return channelIdEq;
    }

    public void setChannelIdEq(String channelIdEq) {
        this.channelIdEq = channelIdEq;
    }

    public int getFirstResult() {
        return firstResult;
    }

    public void setFirstResult(int firstResult) {
        this.firstResult = firstResult;
    }

    public int getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }

    public String getMerchantCodeEq() {
        return merchantCodeEq;
    }

    public void setMerchantCodeEq(String merchantCodeEq) {
        this.merchantCodeEq = merchantCodeEq;
    }

    public String getOrderIdEq() {
        return orderIdEq;
    }

    public void setOrderIdEq(String orderIdEq) {
        this.orderIdEq = orderIdEq;
    }

    public String getOrderNoEq() {
        return orderNoEq;
    }

    public void setOrderNoEq(String orderNoEq) {
        this.orderNoEq = orderNoEq;
    }

    public String getStateEq() {
        return stateEq;
    }

    public void setStateEq(String stateEq) {
        this.stateEq = stateEq;
    }

    public String getTransactionNoEq() {
        return transactionNoEq;
    }

    public void setTransactionNoEq(String transactionNoEq) {
        this.transactionNoEq = transactionNoEq;
    }

    public String getWhenRequestedGt() {
        return whenRequestedGt;
    }

    public void setWhenRequestedGt(String whenRequestedGt) {
        this.whenRequestedGt = whenRequestedGt;
    }

    public String getWhenRequestedLt() {
        return whenRequestedLt;
    }

    public void setWhenRequestedLt(String whenRequestedLt) {
        this.whenRequestedLt = whenRequestedLt;
    }
}
