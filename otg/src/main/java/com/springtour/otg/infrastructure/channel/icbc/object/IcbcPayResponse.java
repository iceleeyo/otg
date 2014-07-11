package com.springtour.otg.infrastructure.channel.icbc.object;

import lombok.Data;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@Data
@XStreamAlias("B2CRes")
public class IcbcPayResponse {
	@XStreamAlias("interfaceName")
	/**
	 * 接口名称
	 */
	private String interfaceName = "";
	@XStreamAlias("interfaceVersion")
	/**
	 * 接口版本号
	 */
	private String interfaceVersion = "";
	
	private IcbcResponseOrderInfo orderInfo;
	
	private IcbcResponseCustom custom;
	
	private IcbcBank bank;
}
