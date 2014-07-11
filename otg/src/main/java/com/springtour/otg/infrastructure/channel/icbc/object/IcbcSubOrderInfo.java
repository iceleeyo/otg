/**
 * 
 */
package com.springtour.otg.infrastructure.channel.icbc.object;

import lombok.Data;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author Future
 *
 */
@Data
@XStreamAlias("subOrderInfo")
public class IcbcSubOrderInfo {
	@XStreamAlias("orderid")
	/**
	 * 流水
	 */
	private String transactionId = "";
	@XStreamAlias("amount")
	/**
	 * 交易金额
	 */
	private String amount = "";
	@XStreamAlias("installmentTimes")
	/**
	 * 分期付款期数
	 */
	private String installmentTimes = "1";
	@XStreamAlias("merAcct")
	/**
	 * 商户账号
	 */
	private String merAcct = "";
	@XStreamAlias("goodsID")
	/**
	 * 商品编号
	 */
	private String goodsId = "";
	@XStreamAlias("goodsName")
	/**
	 * 商品名称
	 */
	private String goodsName = "Spring Tour Product";
	@XStreamAlias("goodsNum")
	/**
	 * 商品数量
	 */
	private String goodsNum = "";
	@XStreamAlias("carriageAmt")
	/**
	 * 已含运费金额
	 */
	private String carriageAmt = "";
}
