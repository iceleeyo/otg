package com.springtour.otg.interfaces.transacting.facade.rq;

import java.io.Serializable;
import java.math.BigDecimal;

import com.springtour.otg.application.util.Constants;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * 请求交易的Request
 * 
 */
public class NewTransactionRq extends GenericRq {

	private static final long serialVersionUID = 1L;
	/**
	 * 合作伙伴标识（必填）
	 */
	private String partnerId;
	/**
	 * 支付渠道标识（必填）
	 */
	private String channelId;
	/**
	 * 网关标识（必填）
	 */
	private String gateway;
	/**
	 * 组织标识，本次交易将付款给哪个组织（必填）
	 */
	private String orgId;
	/**
	 * 产品标识（必填）
	 */
	private String application;
	/**
	 * 订单标识（必填）
	 */
	private String orderId;
	/**
	 * 订单编号（必填）
	 */
	private String orderNo;
	/**
	 * 交易金额，保留两位小数（必填）
	 */
	private BigDecimal amount;
	/**
	 * 交易金额币种（必填）
	 */
	private String currency;
	/**
	 * 收费原因
	 */
	private String chargeFor = Constants.EMPTY;

	public String getChargeFor() {
		return chargeFor;
	}

	public void setChargeFor(String chargeFor) {
		this.chargeFor = chargeFor;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getGateway() {
		return gateway;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
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

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

}
