package com.springtour.otg.interfaces.transacting.facade.rq;

import java.math.BigDecimal;
import org.apache.commons.lang.builder.StandardToStringStyle;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 
 * 请求交易的Request
 * 
 */
public class RequestWithCardInfoRq extends GenericRq {
	private NewTransactionRq newTransactionRq = new NewTransactionRq();
	/**
	 * 卡号（必填）
	 */
	private String cardNo;
	/**
	 * 有效期，格式MMyy,如0412（必填）
	 */
	private String expireDate;
	/**
	 * 密码校验码（必填）
	 */
	private String cvv2;
	/**
	 * 持卡人全名（必填）
	 */
	private String cardHolderFullname;
	/**
	 * 证件号码（必填）
	 */
	private String cardHolderIdNo;
	/**
	 * 证件类型（必填） 身份证：IDENTITY, 护照：PASSPORT, 其他：OTHERS;
	 */
	private String cardHolderIdType;

	@Override
	public String toString() {
		// 不记录卡信息
		return new ToStringBuilder(this,
				StandardToStringStyle.SHORT_PREFIX_STYLE).append(
				newTransactionRq).toString();
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}

	public String getCvv2() {
		return cvv2;
	}

	public void setCvv2(String cvv2) {
		this.cvv2 = cvv2;
	}

	public void setCardHolderFullname(String cardHolderFullname) {
		this.cardHolderFullname = cardHolderFullname;
	}

	public String getCardHolderFullname() {
		return cardHolderFullname;
	}

	public String getCardHolderIdNo() {
		return cardHolderIdNo;
	}

	public void setCardHolderIdNo(String cardHolderIdNo) {
		this.cardHolderIdNo = cardHolderIdNo;
	}

	public String getCardHolderIdType() {
		return cardHolderIdType;
	}

	public void setCardHolderIdType(String cardHolderIdType) {
		this.cardHolderIdType = cardHolderIdType;
	}

	public String getChannelId() {
		return newTransactionRq.getChannelId();
	}

	public void setChannelId(String channelId) {
		this.newTransactionRq.setChannelId(channelId);
	}

	public String getGateway() {
		return newTransactionRq.getGateway();
	}

	public void setGateway(String gateway) {
		this.newTransactionRq.setGateway(gateway);
	}

	public String getOrgId() {
		return newTransactionRq.getOrgId();
	}

	public void setOrgId(String orgId) {
		this.newTransactionRq.setOrgId(orgId);
	}

	public String getApplication() {
		return newTransactionRq.getApplication();
	}

	public void setApplication(String application) {
		this.newTransactionRq.setApplication(application);
	}

	public String getOrderId() {
		return newTransactionRq.getOrderId();
	}

	public void setOrderId(String orderId) {
		this.newTransactionRq.setOrderId(orderId);
	}

	public String getOrderNo() {
		return newTransactionRq.getOrderNo();
	}

	public void setOrderNo(String orderNo) {
		this.newTransactionRq.setOrderNo(orderNo);
	}

	public BigDecimal getAmount() {
		return newTransactionRq.getAmount();
	}

	public void setAmount(BigDecimal amount) {
		this.newTransactionRq.setAmount(amount);
	}

	public String getCurrency() {
		return newTransactionRq.getCurrency();
	}

	public void setCurrency(String currency) {
		this.newTransactionRq.setCurrency(currency);
	}

	public String getPartnerId() {
		return newTransactionRq.getPartnerId();
	}

	public void setPartnerId(String partnerId) {
		this.newTransactionRq.setPartnerId(partnerId);
	}

}
