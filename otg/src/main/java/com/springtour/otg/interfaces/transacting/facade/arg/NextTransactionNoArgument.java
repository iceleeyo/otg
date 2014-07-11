package com.springtour.otg.interfaces.transacting.facade.arg;

import java.io.Serializable;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.StandardToStringStyle;

public class NextTransactionNoArgument implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 支付渠道标识
     */
    private String channelId;

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }
    
    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this,
                StandardToStringStyle.SHORT_PREFIX_STYLE);
    }
}
