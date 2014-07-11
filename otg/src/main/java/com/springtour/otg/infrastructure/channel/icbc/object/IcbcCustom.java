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
@Data
@XStreamAlias("custom")
public class IcbcCustom {
	@XStreamAlias("verifyJoinFlag")
	/**
	 * 检验联名标志
	 */
	private String verifyJoinFlag = "0"; 
	@XStreamAlias("Language")
	/**
	 * 语言版本
	 */
	private String language = "ZH_CN";
}
