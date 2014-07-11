package com.springtour.otg.infrastructure.payment;

public interface MakeAutoPaymentService {
	/**
	 * 完成支付,生产一笔支付记录
	 * </p>
	 * 
	 * 实现者应考虑去重复处理，对于同一交易流水号只作一次支付
	 * 
	 * @param orderId
	 *            订单Id
	 * @param amount
	 *            支付金额(精确到两位小数:xx.xx)
	 * @param currency
	 *            币种:cny
	 * @param transactionNo
	 *            交易流水号:2011041400000102
	 * @param channelId
	 *            支付渠道编号
	 * @param whenResponsed
	 *            支付时间(yyyy-MM-dd HH:mm:ss)
	 * @param merchantCode
	 *            商户号
	 * @param chargeFor
	 *            收费原因
	 * @return 0 成功
	 */
	String makePayment(String orderId, String amount, String currency,
			String transactionNo, String channelId, String whenResponsed,
			String merchantCode, String chargeFor);
}
