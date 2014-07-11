package com.springtour.otg.interfaces.admin.facade.rs;

import org.apache.commons.lang.builder.StandardToStringStyle;
import org.apache.commons.lang.builder.ToStringBuilder;

public abstract class GenericRs {

    /**
     * 状态码
     */
    private String statusCode;
    private String message;

    public GenericRs(String statusCode) {
        this.statusCode = statusCode;
    }

    public GenericRs(String statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getStatusCode() {
        return statusCode;
    }

    @Override
    public String toString() {
        //这里只输出statusCode，子类负责输出其他信息，对于查询rs，不要输出查询结果，否则数据太大，所以不要使用reflectionToString
        return new ToStringBuilder(this, StandardToStringStyle.SHORT_PREFIX_STYLE).append(statusCode).append(message).toString();
    }
}
