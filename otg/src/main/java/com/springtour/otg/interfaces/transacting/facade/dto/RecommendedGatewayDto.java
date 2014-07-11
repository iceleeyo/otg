package com.springtour.otg.interfaces.transacting.facade.dto;

import java.io.Serializable;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.StandardToStringStyle;

public class RecommendedGatewayDto implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 网关优先级
     */
    private int priority;
    /**
     * 渠道
     */
    private String channel;
    /**
     * 网关
     */
    private String gateway;
    
    private String gatewayName;

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getGatewayName() {
        return gatewayName;
    }

    public void setGatewayName(String gatewayName) {
        this.gatewayName = gatewayName;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this,
                StandardToStringStyle.SHORT_PREFIX_STYLE);
    }
}
