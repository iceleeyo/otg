package com.springtour.otg.interfaces.transacting.facade.rq;

import lombok.Getter;
import lombok.Setter;

public class RequestWithoutCardInfoRq extends NewTransactionRq {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 渠道商通知合作伙伴交易结果的URL地址（选填）
	 */
	@Getter
	@Setter
	private String returnUrl;
	/**
	 * 支付发起人IP地址（必填）
	 */
	@Getter
	@Setter
	private String surferIp;
	/**
	 * 分期支付期数（选填）
	 */
	@Getter
	@Setter
	private String installment;

}
