/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.interfaces.transacting.facade.rq;

/**
 *
 * @author 001595
 */
public class ListTransactionsRq extends GenericRq {

    private String orderIdEq;
    private String applicationEq;

    public String getApplicationEq() {
        return applicationEq;
    }

    public void setApplicationEq(String applicationEq) {
        this.applicationEq = applicationEq;
    }

    public String getOrderIdEq() {
        return orderIdEq;
    }

    public void setOrderIdEq(String orderIdEq) {
        this.orderIdEq = orderIdEq;
    }
}
