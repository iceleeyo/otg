package com.springtour.otg.infrastructure.channel.ccb;

public class CcbConstants {

	/**
	 * 币种
	 */
	public static final String CURCODE = "01";
	/**
	 * 交易码
	 */
	public static final String TXCODE = "520100";
	
	public static final String TXCODE_CREDIT_CARD ="410800";
	/**
	 * 接口类型 0- 非钓鱼接口 1- 防钓鱼接口
	 */
	public static final String TYPE = "1";
	/**
	 * 网关类型 <br/>
	 * W0Z1或W0Z2：仅显示帐号支付标签 <br/>
	 * W1Z0或W2Z0：仅显示网银客户支付标签 <br/>
	 * W1Z1或W2Z1：两个均显示，选中网银客户支付标签<br/>
	 * W1Z2：两个均显示，选中帐号支付标签<br/>
	 * W0Z0或W2Z2或其他：两个均显示，选中网银客户支付标签
	 */
	public static final String GATEWAY = "W1Z1";
	
	public static final String GATEWAY_DEFAULT_CARD = "0";
	/**
	 * 成功－Y，失败－N
	 */
	public static final String CHARGED_SUCCESS="Y";

}
