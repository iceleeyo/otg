package com.springtour.otg.interfaces.transacting.facade.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TransactionDto implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 交易流水号
     */
    private String transactionNo;
    /**
     * 交易金额
     */
    private BigDecimal amount;
    /**
     * 交易币种
     */
    private String currency;
    /**
     * 合作伙伴标识
     */
    private String partnerId;
    /**
     * 合作伙伴名称
     */
    private String partnerName;
    /**
     * 商户id
     */
    private String merchantId;
    /**
     * 商户名称
     */
    private String merchantName;
    /**
     * 渠道商标识
     */
    private String channelId;
    /**
     * 渠道商名称
     */
    private String channelName;
    /**
     * 网关
     */
    private String gateway;
    /**
     * 交易状态
     */
    private String state;
    /**
     * 交易请求时间
     */
    private Date whenRequested;

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

    public String getTransactionNo() {
        return transactionNo;
    }

    public void setTransactionNo(String transactionNo) {
        this.transactionNo = transactionNo;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getWhenRequested() {
        return whenRequested;
    }

    public void setWhenRequested(Date whenRequested) {
        this.whenRequested = whenRequested;
    }
}
