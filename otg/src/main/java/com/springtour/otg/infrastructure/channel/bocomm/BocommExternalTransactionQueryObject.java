/*
 * Copyright(c) 2012-2014 SpringTour.Ltd. All Rights Reserved.
 */
package com.springtour.otg.infrastructure.channel.bocomm;

import java.math.BigDecimal;

import lombok.Setter;

import org.apache.log4j.Logger;

import com.bocom.netpay.b2cAPI.BOCOMB2CClient;
import com.bocom.netpay.b2cAPI.BOCOMB2COPReply;
import com.bocom.netpay.b2cAPI.OpResultSet;
import com.springtour.otg.application.util.Configurations;
import com.springtour.otg.domain.model.notification.Charge;
import com.springtour.otg.domain.model.transaction.Transaction;
import com.springtour.otg.domain.shared.Money;
import com.springtour.otg.infrastructure.channel.AbstractChannelExternalTransactionQueryObjectAdapter;
import com.springtour.otg.infrastructure.channel.ExternalTransactionQueryResult;
import com.springtour.otg.infrastructure.channel.ThrowableExternalTransactionQueryResult;
import com.springtour.otg.infrastructure.logging.LoggerFactory;

/**
 * 此处为类说明
 * 
 * @author 010586
 * @date 2014-4-8
 */
public class BocommExternalTransactionQueryObject extends
		AbstractChannelExternalTransactionQueryObjectAdapter {

	@Setter
	private Configurations configurations;

	static Logger log = LoggerFactory.getLogger();

	protected Logger logger() {
		return LoggerFactory.getLogger();
	}

	/**
	 * 此处为构造器说明
	 * 
	 * @author 010586
	 * @param channel
	 */
	public BocommExternalTransactionQueryObject(String channel) {
		super(channel);
	}

	@Override
	public ExternalTransactionQueryResult queryBy(Transaction transaction) {
		String orderAmount = "";
		String tradeStatus = "";
		String extTxnNo = "";
		String msg = "";
		BOCOMB2CClient client = new BOCOMB2CClient();
		String path = this.getClass()
				.getResource(configurations.getBocommXmlFileResourceName())
				.getPath();
		int ret = client.initialize(path); // 该代码只需调用一次

		if (ret != 0) {
			log.error("初始化失败,错误信息：" + client.getLastErr());
			throw new RuntimeException("初始化失败,错误信息：" + client.getLastErr());
		}

		BOCOMB2COPReply rep = client.queryOrder(transaction.getTransactionNo()
				.getNumber());
		
		if (rep == null) {
			throw new IllegalArgumentException("交易错误信息：" + client.getLastErr());
		} else {
			String code = rep.getRetCode(); // 得到交易返回码
			msg = rep.getErrorMessage();
			if ("0".equals(code)) {// 表示交易成功
				OpResultSet oprSet = rep.getOpResultSet();
				logger().info(
						"Boccom responsed encrypted data: " + oprSet
								+ ",  TXN NUMBER ["
								+ transaction.getTransactionNo().getNumber()
								+ "]");
				orderAmount = oprSet.getResultValueByName(0, "amount");
				tradeStatus = oprSet.getResultValueByName(0, "tranState");// 支付交易状态
				extTxnNo = oprSet.getResultValueByName(0, "bankSerialNo");// 银行流水号
			}
		}
		return new ThrowableExternalTransactionQueryResult(convert(tradeStatus,
				msg), Money.valueOf(new BigDecimal(orderAmount)), extTxnNo);
	}
	
	//tradeStatus:0未支付、1已支付、2已撤销、3已部分退货、4退货处理中、5已全额退货
	private static Charge convert(String tradeStatus, String msg) {
		if ("1".equals(tradeStatus)) {
			return Charge.SUCCESS;
		} else {
			log.error("交易失败，失败原因：" + msg);
			return null;
		}
	}
}
