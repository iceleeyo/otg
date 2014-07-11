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
@XStreamAlias("bank")
@Data
public class IcbcBank {
	@XStreamAlias("TranBatchNo")
	/**
	 * 批次号
	 */
	private String tranBatchNo;
	@XStreamAlias("notifyDate")
	/**
	 * 返回通知日期时间
	 */
	private String notifyDate;
	@XStreamAlias("tranStat")
	/**
	 * 订单处理状态
	 */
	private String tranStat;
	@XStreamAlias("comment")
	/**
	 * 错误描述
	 */
	private String comment;
}
