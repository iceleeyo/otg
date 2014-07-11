package com.springtour.otg.infrastructure.channel.icbc.object;

import lombok.Data;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@Data
@XStreamAlias("out")
public class IcbcQueryResponseInfo {
	@XStreamAlias("tranSerialNum")
	private String tranSerialNum;
	@XStreamAlias("tranStat")
	private String tranStat;
	@XStreamAlias("bankRem")
	private String bankRem;
	@XStreamAlias("amount")
	private String amount;
	@XStreamAlias("currType")
	private String currType;
	@XStreamAlias("tranTime")
	private String tranTime;
	@XStreamAlias("ShopAccount")
	private String shopAccount;
	@XStreamAlias("PayeeName")
	private String payeeName;
	@XStreamAlias("JoinFlag")
	private String joinFlag;
	@XStreamAlias("MerJoinFlag")
	private String merJoinFlag;
	@XStreamAlias("CustJoinFlag")
	private String custJoinFlag;
	@XStreamAlias("CustJoinNum")
	private String custJoinNum;
	@XStreamAlias("CertID")
	private String certID;
}
