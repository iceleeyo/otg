package com.springtour.otg.domain.shared;

import com.springtour.otg.application.exception.UnavailableCurrencyException;
import com.springtour.shared.ValueObject;
import java.math.BigDecimal;
import java.math.RoundingMode;

import lombok.EqualsAndHashCode;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.StandardToStringStyle;

@EqualsAndHashCode
public class Money implements ValueObject<Money> {

	private static final String DEFAULT_CURRENCY = "CNY";
	private static final int AMOUNT_SCALE = 2;
	private BigDecimal amount;
	private WrapCurrency currency;

	public static Money valueOf(BigDecimal amount, String currency)
			throws UnavailableCurrencyException {
		return new Money(amount, currency);
	}

	public static Money valueOf(BigDecimal amount)
			throws UnavailableCurrencyException {
		return new Money(amount, DEFAULT_CURRENCY);
	}

	private Money(BigDecimal amount, String currency)
			throws UnavailableCurrencyException {
		// 取两位小数，四舍五入，这主要是为了在向支付渠道发送请求时保持一个统一的格式，大多数支付渠道都支持两位小数
		this.amount = scale(amount);
		this.currency = WrapCurrency.valueOf(currency);
	}

	private BigDecimal scale(BigDecimal amount) {
		return amount.setScale(AMOUNT_SCALE, RoundingMode.HALF_UP);
	}

	public Money negate() {
		return Money.valueOf(this.getAmount().negate(), this.getCurrencyCode());
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.reflectionToString(this,
				StandardToStringStyle.SHORT_PREFIX_STYLE);
	}

	@Override
	public boolean sameValueAs(final Money other) {
		return this.equals(other);
	}

	public BigDecimal getAmount() {
		return scale(amount);// 为了保证EqualsAndHashCode不受BigDecimal.equals()的影响，这里都做四舍五入
	}

	public WrapCurrency getCurrency() {
		return currency;
	}

	public String getCurrencyCode() {
		return currency.getCode();
	}

	/**
     * 
     */
	public Money() {
	}

	public void setAmount(BigDecimal amount) {
		this.amount = scale(amount);
	}

	public void setCurrency(WrapCurrency currency) {
		this.currency = currency;
	}
}
