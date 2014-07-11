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
@XStreamAlias("custom")
@Data
public class IcbcResponseCustom {
	@XStreamAlias("verifyJoinFlag")
	/**
	 * 检验联名标志
	 */
	private String verifyJoinFlag;
	@XStreamAlias("JoinFlag")
	/**
	 * 客户联名标志
	 */
	private String joinFlag;
	@XStreamAlias("UserNum")
	/**
	 * 联名会员号
	 */
	private String userNum;
}
