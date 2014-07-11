package com.springtour.otg.interfaces.admin.facade.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.StandardToStringStyle;

public class NotificationDto {

    private String sequence;
    private String whenReceived;
    private String transactionNo;
    private String extTxnNo;
    private BigDecimal amount;
    private String currencyCode;
    private String charged;
    private String message;
    private String signature;
    private String synchronizingMethod;
    @Getter
    @Setter
    private String cardInfo;
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCharged() {
        return charged;
    }

    public void setCharged(String charged) {
        this.charged = charged;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getExtTxnNo() {
        return extTxnNo;
    }

    public void setExtTxnNo(String extTxnNo) {
        this.extTxnNo = extTxnNo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getSynchronizingMethod() {
        return synchronizingMethod;
    }

    public void setSynchronizingMethod(String synchronizingMethod) {
        this.synchronizingMethod = synchronizingMethod;
    }

    public String getTransactionNo() {
        return transactionNo;
    }

    public void setTransactionNo(String transactionNo) {
        this.transactionNo = transactionNo;
    }

    public String getWhenReceived() {
        return whenReceived;
    }

    public void setWhenReceived(String whenReceived) {
        this.whenReceived = whenReceived;
    }

     @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this,
                StandardToStringStyle.SHORT_PREFIX_STYLE);
    }
}
