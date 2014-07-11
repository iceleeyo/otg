package com.springtour.otg.infrastructure.channel.icbc.object;

import lombok.Data;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@Data
@XStreamAlias("in")
public class IcbcQueryRequest {
	@XStreamAlias("orderNum")
	private String orderNum;
	@XStreamAlias("tranDate")
	private String tranDate;
	@XStreamAlias("ShopCode")
	private String shopCode;
	@XStreamAlias("ShopAccount")
	private String shopAccount;
	
}
