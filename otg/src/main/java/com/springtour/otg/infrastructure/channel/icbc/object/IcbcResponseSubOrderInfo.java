/**
 * 
 */
package com.springtour.otg.infrastructure.channel.icbc.object;

import lombok.Data;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author 005073
 * 
 */
@XStreamAlias("subOrderInfo")
@Data
public class IcbcResponseSubOrderInfo {
	@XStreamAlias("orderid")
	/**
	 * 支付流水
	 */
	private String transactionId = "";
	@XStreamAlias("amount")
	/**
	 * 支付金额
	 */
	private String amount = "";
	@XStreamAlias("installmentTimes")
	/**
	 * 分期付款期数
	 */
	private String installmentTimes = "";
	@XStreamAlias("merAcct")
	/**
	 * 商户账号
	 */
	private String merAcct = "";
	@XStreamAlias("tranSerialNo")
	/**
	 * 银行指令序号
	 */
	private String tranSerialNo = "";

}
