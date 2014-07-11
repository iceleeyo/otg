/**
 * 
 */
package com.springtour.otg.infrastructure.channel.icbc.object;

import java.util.Date;
import java.util.List;

import lombok.Data;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

/**
 * @author Future
 * 
 */
@Data
@XStreamAlias("orderInfo")
public class IcbcOrderInfo {
	@XStreamAlias("orderDate")
	@XStreamConverter(IcbcDateConverter.class)
	/**
	 * 交易日期时间
	 */
	private Date orderDate;
	@XStreamAlias("curType")
	/**
	 * 支付币种
	 */
	private String curType = "001";
	@XStreamAlias("merID")
	/**
	 * 商户代码
	 */
	private String merId = "";
	@XStreamAlias("subOrderInfoList")
	private List<IcbcSubOrderInfo> subOrderInfos;
}
