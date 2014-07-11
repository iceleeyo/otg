/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.domain.shared;

import com.springtour.otg.application.exception.UnavailableCurrencyException;
import com.springtour.shared.ValueObject;
import java.util.Currency;

import lombok.EqualsAndHashCode;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.StandardToStringStyle;

/**
 * 
 * @author 001595
 */
@EqualsAndHashCode
public class WrapCurrency implements ValueObject<WrapCurrency> {

	private String code;

	public static WrapCurrency valueOf(String currency)
			throws UnavailableCurrencyException {
		return new WrapCurrency(currency);
	}

	private WrapCurrency(String currency) throws UnavailableCurrencyException {
		try {
			this.code = Currency.getInstance(currency).getCurrencyCode();
		} catch (Exception e) {
			throw new UnavailableCurrencyException(currency);
		}
	}

	@Override
	public boolean sameValueAs(final WrapCurrency other) {
		return this.equals(other);
	}

	public boolean sameCodeAs(final String code) {
		return (code != null) && this.code.equals(code);
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.reflectionToString(this,
				StandardToStringStyle.SHORT_PREFIX_STYLE);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String currency) {
		this.code = currency;
	}

	public WrapCurrency() {
	}
}
