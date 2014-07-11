/**
 * 
 */
package com.springtour.otg.infrastructure.channel.icbc;

import java.math.BigDecimal;

/**
 * @author Future
 *
 */
public class IcbcConstants {
	/**
	 * 接口名称
	 */
	public static final String INTERFACE_NAME = "ICBC_PERBANK_B2C";
	/**
	 * 接口版本
	 */
	public static final String INTERFACE_VERSION = "1.0.0.11";
	/**
	 * XML头
	 */
	public static final String REQ_XML_HEAD = "<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?>";
	/**
	 * 工行交易成功应答
	 */
	public static final String ICBC_RESPONSE_SUC = "1";
	/**
	 * 工行金额以分为单位
	 */
	public static final BigDecimal ICBC_MONEY = BigDecimal.valueOf(100);
}
