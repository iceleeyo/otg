/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.integration;

import com.springtour.otg.domain.model.channel.Channel;
import com.springtour.otg.domain.model.merchant.Merchant;
import com.springtour.otg.domain.model.notification.Notification;
import com.springtour.otg.domain.model.partner.Partner;
import com.springtour.otg.domain.model.transaction.*;
import com.springtour.otg.domain.shared.Money;
import java.util.List;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 *
 * @author 001595
 */
public class TransactionMatcher extends AggregateMatcher<Transaction> {

    @Override
    protected String toString(Transaction aggregate) {
        return new ReflectionToStringBuilder(aggregate, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames(
                new String[]{"transactionNo", "partner", "orderId", "merchant", "channel",
                    "amount", "handlingActivity"}).append(toString(aggregate.getTransactionNo())).append(
                toString(aggregate.getPartner())).append(toString(aggregate.getOrderId())).append(
                toString(aggregate.getChannel())).append(toString(aggregate.getAmount())).append(toString(aggregate.getHandlingActivity())).toString();
    }

    private String toString(TransactionNo obj) {
        return new ReflectionToStringBuilder(obj, ToStringStyle.SHORT_PREFIX_STYLE).toString();
    }

    private String toString(Partner partner) {
        return "parter[" + partner.getId() + "]";
    }

    private String toString(OrderIdentity orderIdentity) {
        return new ReflectionToStringBuilder(orderIdentity, ToStringStyle.SHORT_PREFIX_STYLE).toString();
    }

    /**
     * 由于merchant不是使用延迟加载的，所以数据加载不全，不做比较，transaction已经冗余了merchantCode即可
     * @param merchant
     * @return 
     */
    private String toString(Merchant merchant) {
        System.out.println("sadfsaf" + merchant);
        return new ReflectionToStringBuilder(ToStringStyle.SHORT_PREFIX_STYLE).append(merchant.getChannel().getId()).append(merchant.getOrgId()).toString();
    }

    private String toString(Channel channel) {
        return "parter[" + channel.getId() + "]";
    }

    private String toString(Money money) {
        return new ReflectionToStringBuilder(money, ToStringStyle.SHORT_PREFIX_STYLE).toString();
    }

    private String toString(HandlingActivity handlingActivity) {
        if (handlingActivity != null) {
            ReflectionToStringBuilder builder = new ReflectionToStringBuilder(handlingActivity,
                    ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames(new String[]{"notification"});
                builder.append(toString(handlingActivity.getNotification()));
            return builder.toString();

        } else {
            return "handlingActivity[null]";
        }
    }

    private String toString(Notification notification) {
        return notification.getSequence();
    }
}
