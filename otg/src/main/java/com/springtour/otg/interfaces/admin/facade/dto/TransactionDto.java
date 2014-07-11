package com.springtour.otg.interfaces.admin.facade.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.StandardToStringStyle;

public class TransactionDto {

    private String transactionNo;
    private BigDecimal amount;
    private String currency;
    private String partnerId;
    private String partnerName;
    private String application;
    private String orderId;
    private String orderNo;
    private String merchantCode;
    private String merchantName;
    private String channelId;
    private String channelName;
    private String gateway;
    private String stateName;
    private String whenRequested;
    private String whenHandled;
    private String whenConcluded;
    private NotificationDto notificationDto;
    private String checkingState;
    private String updateTime;
    @Getter
    @Setter
    private String transactionTypeName;
    @Getter
    @Setter
    private String referenceTxnNo;

    
    public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getCheckingState() {
		return checkingState;
	}

	public void setCheckingState(String checkingState) {
		this.checkingState = checkingState;
	}

	public NotificationDto getNotificationDto() {
        return notificationDto;
    }

    public void setNotificationDto(NotificationDto notificationDto) {
        this.notificationDto = notificationDto;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
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

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getTransactionNo() {
        return transactionNo;
    }

    public void setTransactionNo(String transactionNo) {
        this.transactionNo = transactionNo;
    }

    public String getWhenConcluded() {
        return whenConcluded;
    }

    public void setWhenConcluded(String whenConcluded) {
        this.whenConcluded = whenConcluded;
    }

    public String getWhenHandled() {
        return whenHandled;
    }

    public void setWhenHandled(String whenHandled) {
        this.whenHandled = whenHandled;
    }

    public String getWhenRequested() {
        return whenRequested;
    }

    public void setWhenRequested(String whenRequested) {
        this.whenRequested = whenRequested;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this,
                StandardToStringStyle.SHORT_PREFIX_STYLE);
    }
}
