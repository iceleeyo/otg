package com.springtour.otg.interfaces.transacting.facade.rs;

import java.io.Serializable;
import org.apache.commons.lang.builder.StandardToStringStyle;
import org.apache.commons.lang.builder.ToStringBuilder;

public abstract class GenericRs implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 状态码（不可空）
     */
    private String statusCode;

    public GenericRs(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusCode() {
        return statusCode;
    }

    @Override
    public String toString() {
        //这里只输出statusCode，子类负责输出其他信息，对于查询rs，不要输出查询结果，否则数据太大，所以不要使用reflectionToString
        return new ToStringBuilder(this, StandardToStringStyle.SHORT_PREFIX_STYLE).append(statusCode).toString();
    }
}
