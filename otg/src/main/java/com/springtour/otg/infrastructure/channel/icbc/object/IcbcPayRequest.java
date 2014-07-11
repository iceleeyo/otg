/**
 * 
 */
package com.springtour.otg.infrastructure.channel.icbc.object;

import lombok.Data;

import com.springtour.otg.infrastructure.channel.icbc.IcbcConstants;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author Future
 * 
 */
@Data
@XStreamAlias("B2CReq")
public class IcbcPayRequest {
	@XStreamAlias("interfaceName")
	/**
	 * 接口名称
	 */
	private String interfaceName = IcbcConstants.INTERFACE_NAME;
	@XStreamAlias("interfaceVersion")
	/**
	 * 接口版本号
	 */
	private String interfaceVersion = IcbcConstants.INTERFACE_VERSION;

	private IcbcOrderInfo orderInfo;

	private IcbcCustom custom;

	private IcbcMessage message;
}
