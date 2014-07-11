package com.springtour.otg.domain.model.transaction;

import lombok.EqualsAndHashCode;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.StandardToStringStyle;

/**
 * 请求交易的订单的标识
 * 
 * @author Hippoom
 * 
 */
@EqualsAndHashCode
public class OrderIdentity {

	/**
	 * 请求交易的应用
	 * 
	 */
	private String application;
	/**
	 * 请求交易的订单id
	 */
	private String orderId;
	/**
	 * 冗余的请求交易的订单号
	 */
	private String orderNo;

	/**
	 * for persistence
	 */
	public OrderIdentity() {
	}

	public static OrderIdentity valueOf(String application, String orderId,
			String orderNo) {
		return new OrderIdentity(application, orderId, orderNo);
	}

	public OrderIdentity(String application, String orderId, String orderNo) {
		this.orderId = orderId;
		this.orderNo = orderNo;
		this.application = application;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.reflectionToString(this,
				StandardToStringStyle.SHORT_PREFIX_STYLE);
	}
}
