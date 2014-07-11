package com.springtour.otg.domain.model.channel;

import org.apache.commons.lang.builder.StandardToStringStyle;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 网关
 * 
 */
public class Gateway {

    private String code;
    private String dialect;
    private int priority;

    public Gateway(Gateways gateway) {
        this.code = gateway.code();
        this.dialect = code;
    }

    public Gateway(Gateways gateway, String dialect, int priority) {
        this.code = gateway.code();
        this.dialect = dialect;
        this.priority = priority;
    }

    public Gateway() {
    }

    public boolean sameCodeAs(String code) {
        return (code != null) && (this.code.equals(code));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, StandardToStringStyle.SHORT_PREFIX_STYLE).append(code).toString();
    }

    public String getCode() {
        return code;
    }

    public String getDialect() {
        return dialect;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDialect(String dialect) {
        this.dialect = dialect;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
