/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.interfaces.transacting.facade.dto;

import java.io.Serializable;
import java.util.List;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.StandardToStringStyle;

/**
 *
 * @author 001595
 */
public class AvailableChannelDto implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 渠道标识
     */
    private String channelId;
    /**
     * 渠道所支持的币种列表
     */
    private List<String> availableCurrencies;
    /**
     * 渠道所支持的网关列表
     */
    private List<String> gateways;

    public List<String> getAvailableCurrencies() {
        return availableCurrencies;
    }

    public void setAvailableCurrencies(List<String> availableCurrencies) {
        this.availableCurrencies = availableCurrencies;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public List<String> getGateways() {
        return gateways;
    }

    public void setGateways(List<String> gateways) {
        this.gateways = gateways;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this,
                StandardToStringStyle.SHORT_PREFIX_STYLE);
    }
}
