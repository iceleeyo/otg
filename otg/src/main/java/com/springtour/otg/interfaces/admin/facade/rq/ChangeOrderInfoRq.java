/*
 * Copyright(c) 2012-2014 SpringTour.Ltd. All Rights Reserved.
 */
package com.springtour.otg.interfaces.admin.facade.rq;

import lombok.Getter;
import lombok.Setter;

import com.springtour.otg.application.util.Constants;

/**
 * 此处为类说明
 * @author 010586
 * @date 2014-3-24
 */
public class ChangeOrderInfoRq extends GenericRq {
    /**
     * 交易流水号
     */
    @Setter
    @Getter
    private String transactionNo;

    /**
     * 产品标识（必填）
     */
    @Setter
    @Getter
    private String application;

    /**
     * 订单标识（必填）
     */
    @Setter
    @Getter
    private String orderId;

    /**
     * 订单编号（必填）
     */
    @Setter
    @Getter
    private String orderNo;

    /**
     * 收费原因
     */
    @Setter
    @Getter
    private String chargeFor = Constants.EMPTY;
}
