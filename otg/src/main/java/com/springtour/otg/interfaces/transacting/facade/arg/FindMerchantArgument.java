package com.springtour.otg.interfaces.transacting.facade.arg;

import java.io.Serializable;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.StandardToStringStyle;

@SuppressWarnings("serial")
public class FindMerchantArgument implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 支付渠道标识
     */
    private String channelId;
    /**
     * 组织标识
     */
    private String orgId;

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }
    
    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this,
                StandardToStringStyle.SHORT_PREFIX_STYLE);
    }
}
