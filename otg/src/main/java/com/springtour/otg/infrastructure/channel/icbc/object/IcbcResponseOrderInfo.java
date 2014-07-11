/**
 * 
 */
package com.springtour.otg.infrastructure.channel.icbc.object;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import lombok.Data;

/**
 * @author 005073
 * 
 */
@Data
@XStreamAlias("orderInfo")
public class IcbcResponseOrderInfo {
	@XStreamAlias("orderDate")
	/**
	 * 交易日期时间
	 */
	private String orderDate = "";
	@XStreamAlias("curType")
	/**
	 * 支付币种
	 */
	private String curType = "";
	@XStreamAlias("merID")
	/**
	 * 商户代码
	 */
	private String merId = "";

	@XStreamAlias("subOrderInfoList")
	List<IcbcResponseSubOrderInfo> subOrderInfos;
}
