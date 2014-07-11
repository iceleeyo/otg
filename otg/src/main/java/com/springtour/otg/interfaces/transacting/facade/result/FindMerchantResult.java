package com.springtour.otg.interfaces.transacting.facade.result;

import java.io.Serializable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.StandardToStringStyle;

import com.springtour.otg.interfaces.transacting.facade.dto.MerchantDTO;

public class FindMerchantResult implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 状态码
	 */
	private String statusCode;
	/**
	 * 商户
	 */
	private MerchantDTO merchant;

	/**
	 * @param statusCode
	 * @param merchant
	 */
	public FindMerchantResult(String statusCode, MerchantDTO merchant) {
		this.statusCode = statusCode;
		this.merchant = merchant;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.reflectionToString(this,
				StandardToStringStyle.SHORT_PREFIX_STYLE);
	}

	/**
	 * 
	 * @param statusCode
	 */
	public FindMerchantResult(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public MerchantDTO getMerchant() {
		return merchant;
	}

}
