package com.springtour.otg.infrastructure.persistence;

import java.util.*;

import lombok.*;

import com.springtour.otg.domain.model.transaction.*;
import com.springtour.util.*;

/**
 * 
 * @author jker
 * 
 *         2011-5-5
 */
@EqualsAndHashCode
public class TransactionCriteria {

	/**
	 * 交易流水号
	 */
	private String transactionNoEq;
	/**
	 * 状态
	 */
	private String stateEq;
	/**
	 * 核对状态
	 */
	@Getter
	private String checkingStateEq;
	/**
	 * 请求时间大于
	 */
	private Date whenRequestedGt;
	/**
	 * 请求时间小于
	 */
	private Date whenRequestedLt;
	private String orderIdEq;
	private String applicationEq;
	private String orderNoEq;
	/**
	 * 商户号
	 */
	private String merchantCodeEq;
	/**
	 * 支付渠道标识
	 */
	private String channelIdEq;
	@Getter
	private List<String> channelIdIn;
	@Getter
	private String transactionTypeEq;
	private int firstResult;
	private int maxResults;
	@Getter
	private boolean unpaged = false;
	/**
	 * 退款交易中的原交易流水号
	 */
	@Getter
	private String referenceTxnNumberEq;
	
	/**
     * 
     * 对接伙伴
     */
	@Getter
    private String partnerEq;
	
	public static class Builder {

		/**
		 * 交易流水号
		 */
		private String transactionNoEq;
		/**
		 * 状态
		 */
		private String stateEq;
		/**
		 * 请求时间大于
		 */
		private Date whenRequestedGt;
		/**
		 * 请求时间小于
		 */
		private Date whenRequestedLt;
		private String orderIdEq;
		private String applicationEq;
		private String orderNoEq;
		/**
		 * 商户号
		 */
		private String merchantCodeEq;
		/**
		 * 支付渠道标识
		 */
		private String channelIdEq;
		private Set<String> channels;
		private String transactionTypeEq;
		private int firstResult = 1;
		private int maxResults = 50;
		private boolean unpaged = false;
		private String checkingStateEq;
		/**
		 * 退款交易中的原交易流水号
		 */
		private String referenceTxnNumberEq;
		/**
		 * 
		 * 对接伙伴
		 * @param 
		 * @return
		 *******************************************************************************
		 * @creator ：lenovo  
		 * @date ：2014-6-25     
		 * @memo ：   
		 **
		 */
		private String partnerEq;

		public Builder transactionNoEq(String transactionNo) {
			this.transactionNoEq = transactionNo;
			return this;
		}

		public Builder stateEq(String state) {
			this.stateEq = state;
			return this;
		}

		public Builder whenRequestedGt(Date whenRequested) {
			this.whenRequestedGt = whenRequested;
			return this;
		}

		public Builder whenRequestedLt(Date whenRequested) {
			this.whenRequestedLt = whenRequested;
			return this;
		}

		public Builder orderIdEq(String application, String orderId,
				String orderNo) {
			this.orderIdEq = orderId;
			this.applicationEq = application;
			this.orderNoEq = orderNo;
			return this;
		}

		public Builder merchantCodeEq(String merchantCode) {
			this.merchantCodeEq = merchantCode;
			return this;
		}

		public Builder channelIdEq(String channelId) {
			this.channelIdEq = channelId;
			return this;
		}

		public Builder firstResult(int firstResult) {
			this.firstResult = firstResult;
			return this;
		}

		public Builder maxResults(int maxResults) {
			this.maxResults = maxResults;
			return this;
		}

		public Builder eq(CheckingState checkingState) {
			this.checkingStateEq = checkingState.getCode();
			return this;
		}

		public Builder through(Set<String> channels) {
			this.channels = channels;
			return this;
		}

		public Builder unpaged() {
			this.unpaged = true;
			return this;
		}

		public Builder transactionTypeEq(String transactionTypeEq) {
			this.transactionTypeEq = transactionTypeEq;
			return this;
		}
		
		public Builder referenceTxnNumberEq(String referenceTxnNumberEq){
			this.referenceTxnNumberEq = referenceTxnNumberEq;
			return this;
		}
		
		public Builder partnerEq(String partnerEq) {
		    this.partnerEq = partnerEq;
		    return this;
		}

		public TransactionCriteria build() {
			return new TransactionCriteria(this);
		}

	}

	public TransactionCriteria(Builder builder) {
		this.channelIdEq = builder.channelIdEq;
		this.firstResult = builder.firstResult;
		this.maxResults = builder.maxResults;
		this.merchantCodeEq = builder.merchantCodeEq;
		this.orderIdEq = builder.orderIdEq;
		this.stateEq = builder.stateEq;
		this.transactionNoEq = builder.transactionNoEq;
		this.whenRequestedGt = builder.whenRequestedGt;
		this.whenRequestedLt = builder.whenRequestedLt;
		this.applicationEq = builder.applicationEq;
		this.orderIdEq = builder.orderIdEq;
		this.orderNoEq = builder.orderNoEq;
		this.checkingStateEq = builder.checkingStateEq;
		this.channelIdIn = Arrays.asList(ObjectUtils.nullSafe(builder.channels,
				new HashSet<String>()).toArray(new String[] {}));
		this.unpaged = builder.unpaged;
		this.transactionTypeEq=builder.transactionTypeEq;
		this.referenceTxnNumberEq = builder.referenceTxnNumberEq;
		this.partnerEq = builder.partnerEq;
	}

	public String getStateEq() {
		return stateEq;
	}

	public Date getWhenRequestedGt() {
		return whenRequestedGt;
	}

	public Date getWhenRequestedLt() {
		return whenRequestedLt;
	}

	public String getMerchantCodeEq() {
		return merchantCodeEq;
	}

	public String getChannelIdEq() {
		return channelIdEq;
	}

	public int getFirstResult() {
		return firstResult;
	}

	public int getMaxResults() {
		return maxResults;
	}

	public String getTransactionNoEq() {
		return transactionNoEq;
	}

	public String getOrderIdEq() {
		return orderIdEq;
	}

	public String getApplicationEq() {
		return applicationEq;
	}

	public String getOrderNoEq() {
		return orderNoEq;
	}
}
