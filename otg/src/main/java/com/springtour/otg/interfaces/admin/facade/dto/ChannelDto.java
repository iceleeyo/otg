/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.interfaces.admin.facade.dto;

import java.util.List;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.StandardToStringStyle;

/**
 *
 * @author 006874
 */
public class ChannelDto {

    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private List<GatewayDto> gateways;
    private String availableCurrencies;

    public String getAvailableCurrencies() {
        return availableCurrencies;
    }

    public void setAvailableCurrencies(String availableCurrencies) {
        this.availableCurrencies = availableCurrencies;
    }

    public List<GatewayDto> getGateways() {
        return gateways;
    }

    public void setGateways(List<GatewayDto> gateways) {
        this.gateways = gateways;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this,
                StandardToStringStyle.SHORT_PREFIX_STYLE);
    }
}
