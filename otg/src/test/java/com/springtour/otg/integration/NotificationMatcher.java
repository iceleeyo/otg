/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.integration;

import com.springtour.otg.domain.model.notification.Notification;
import com.springtour.otg.domain.model.transaction.TransactionNo;
import com.springtour.otg.domain.shared.Money;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 *
 * @author 001595
 */
public class NotificationMatcher extends AggregateMatcher<Notification> {

    @Override
    protected String toString(Notification aggregate) {
        return new ReflectionToStringBuilder(aggregate, ToStringStyle.SIMPLE_STYLE).setExcludeFieldNames(
                new String[]{"txnNo", "amount"}).append(toString(aggregate.getTxnNo())).
                append(toString(aggregate.getAmount())).toString();
    }

    private String toString(TransactionNo txnNo) {
        return new ReflectionToStringBuilder(txnNo, ToStringStyle.SIMPLE_STYLE).toString();
    }

    private String toString(Money money) {
        return new ReflectionToStringBuilder(money, ToStringStyle.SIMPLE_STYLE).toString();
    }
}
