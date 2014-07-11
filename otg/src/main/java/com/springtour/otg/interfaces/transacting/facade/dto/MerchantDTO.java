/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.interfaces.transacting.facade.dto;

import java.io.Serializable;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.StandardToStringStyle;

/**
 *
 * @author 001595
 */
public class MerchantDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 标识;
     */
    private String id;
    /**
     * 商户名
     */
    private String name;
    /**
     * 商户号
     */
    private String code;
    /**
     * 支付渠道标识
     */
    private String channelId;
    /**
     * 商户所属组织
     */
    private String orgId;
    /**
     * 商户密钥
     */
    private String key;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this,
                StandardToStringStyle.SHORT_PREFIX_STYLE);
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
