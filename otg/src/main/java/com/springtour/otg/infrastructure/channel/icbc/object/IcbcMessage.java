package com.springtour.otg.infrastructure.channel.icbc.object;

import lombok.Data;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author Future
 *
 */
@Data
@XStreamAlias("message")
public class IcbcMessage {
	@XStreamAlias("creditType")
	/**
	 * 支持订单支付的银行卡种类
	 */
	private String payType = "2";
	@XStreamAlias("notifyType")
	/**
	 * 通知类型
	 */
	private String notifyType = "HS";
	@XStreamAlias("resultType")
	/**
	 * 结果发送类型
	 */
	private String resultType = "1";
	@XStreamAlias("merReference")
	/**
	 * 商户reference
	 */
	private String merReference = "";
	@XStreamAlias("merCustomIp")
	/**
	 * 客户端IP
	 */
	private String merCustomIp = "";
	@XStreamAlias("goodsType")
	/**
	 * 虚拟商品/实物商品标志位
	 */
	private String goodsType = "";
	@XStreamAlias("merCustomID")
	/**
	 * 买家用户号
	 */
	private String merCustomId = "";
	@XStreamAlias("merCustomPhone")
	/**
	 * 买家联系电话
	 */
	private String merCustomPhone = "";
	@XStreamAlias("goodsAddress")
	/**
	 * 收货地址
	 */
	private String goodsAddress = "";
	@XStreamAlias("merOrderRemark")
	/**
	 * 订单备注
	 */
	private String merOrderRemark = "";
	@XStreamAlias("merHint")
	/**
	 * 商城提示
	 */
	private String merHint = "";
	@XStreamAlias("remark1")
	/**
	 * 备注字段1
	 */
	private String remark1 = "";
	@XStreamAlias("remark2")
	/**
	 * 备注字段2
	 */
	private String remark2 = "";
	@XStreamAlias("merURL")
	/**
	 * 返回商户URL
	 */
	private String merUrl = "";
	@XStreamAlias("merVAR")
	/**
	 * 返回商户变量
	 */
	private String merVar = "";
}
