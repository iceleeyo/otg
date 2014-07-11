package com.springtour.otg.infrastructure.channel.alipay;

public enum AlipaySynchronizingStatus {

	requested("WAIT_BUYER_PAY"), // 交易创建，等待买家付款

	quitedOrFullRefunded("TRADE_CLOSED"), // 在指定时间段内未支付时关闭的交易/在交易完成全额退款成功时关闭的交易。

	chargedSuccess("TRADE_SUCCESS"), // 交易成功，且可对该交易做操作，如：多级分润、退款等。

	chargedButFailed("TRADE_PENDING"), // 等待卖家收款（买家付款后，如果卖家账号被冻结）。

	concluded("TRADE_FINISHED"); // 交易成功且结束，即不可再做任何操作

	private String value;

	public static AlipaySynchronizingStatus newInstance(String value) {
		if (requested.getValue().equals(value)) {
			return requested;
		} else if (quitedOrFullRefunded.getValue().equals(value)) {
			return quitedOrFullRefunded;
		} else if (chargedSuccess.getValue().equals(value)) {
			return chargedSuccess;
		} else if (chargedButFailed.getValue().equals(value)) {
			return chargedButFailed;
		} else if (concluded.getValue().equals(value)) {
			return concluded;
		} else {
			return null;
		}
	}

	private AlipaySynchronizingStatus(String value) {
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
}
