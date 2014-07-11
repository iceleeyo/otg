package com.springtour.otg.interfaces.admin.web.command;


public class ListTransactionsCommand {
	private String transactionNoEq;
    private String stateEq;
    private String whenRequestedGt;
    private String whenRequestedLt;
    private String orderIdEq;
    private String applicationEq;
    private String orderNoEq;
    private String merchantCodeEq;
    private String channelIdEq;
    private String partnerId;
    private int pageNo; 
    private int pageSize;
    public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public String getApplicationEq() {
        return applicationEq;
    }

    public void setApplicationEq(String applicationEq) {
        this.applicationEq = applicationEq;
    }

    public String getChannelIdEq() {
        return channelIdEq;
    }

    public void setChannelIdEq(String channelIdEq) {
        this.channelIdEq = channelIdEq;
    }

    public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getMerchantCodeEq() {
        return merchantCodeEq;
    }

    public void setMerchantCodeEq(String merchantCodeEq) {
        this.merchantCodeEq = merchantCodeEq;
    }

    public String getOrderIdEq() {
        return orderIdEq;
    }

    public void setOrderIdEq(String orderIdEq) {
        this.orderIdEq = orderIdEq;
    }

    public String getOrderNoEq() {
        return orderNoEq;
    }

    public void setOrderNoEq(String orderNoEq) {
        this.orderNoEq = orderNoEq;
    }

    public String getStateEq() {
        return stateEq;
    }

    public void setStateEq(String stateEq) {
        this.stateEq = stateEq;
    }

    public String getTransactionNoEq() {
        return transactionNoEq;
    }

    public void setTransactionNoEq(String transactionNoEq) {
        this.transactionNoEq = transactionNoEq;
    }

    public String getWhenRequestedGt() {
        return whenRequestedGt;
    }

    public void setWhenRequestedGt(String whenRequestedGt) {
        this.whenRequestedGt = whenRequestedGt;
    }

    public String getWhenRequestedLt() {
        return whenRequestedLt;
    }

    public void setWhenRequestedLt(String whenRequestedLt) {
        this.whenRequestedLt = whenRequestedLt;
    }
    
    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

}
