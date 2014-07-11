package com.springtour.otg.interfaces.transacting.facade.rq;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.StandardToStringStyle;

/**
 * 
 * 请求交易的Request
 * 
 */
public class AvailableGatewaysRq extends GenericRq {

    private static final long serialVersionUID = 1L;
    /**
     * 合作伙伴标识（必填）
     */
    private String partnerId;

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }
}
