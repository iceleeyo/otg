/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.domain.model.notification;

import com.springtour.shared.ValueObject;

/**
 * 响应同步方式
 *
 * @author Hippoom
 *
 */
/**
 *
 * @author 001595
 */
public enum SynchronizingMethod {

    /**
     * 异步
     */
    ASYNCHRONOUS("A"),
    /**
     * 同步
     */
    SYNCHRONOUS("S"),
    /**
     * 手动
     */
    MANUAL("M"),
    /**
     * 自动
     */
    CHECK("C");
    /**
     * 代码
     */
    private final String code;

    private SynchronizingMethod(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public boolean sameCodeAs(String code) {
        return code != null && this.code.equals(code);
    }
}
