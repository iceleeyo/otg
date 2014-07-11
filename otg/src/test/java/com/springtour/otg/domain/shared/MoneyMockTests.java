package com.springtour.otg.domain.shared;

import java.math.BigDecimal;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.junit.Assert.*;
import static org.junit.Assert.assertThat;
import java.util.Currency;
import java.util.Locale;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

public class MoneyMockTests {

	@Test
	public void sameValueAs() throws Exception {
		Assert.assertTrue(Money.valueOf(BigDecimal.valueOf(100), "CNY")
				.sameValueAs(
						Money.valueOf(BigDecimal.valueOf(100.00), Currency
								.getInstance(Locale.CHINA).getCurrencyCode())));
		Assert.assertTrue(!Money.valueOf(BigDecimal.valueOf(100), "CNY")
				.sameValueAs(Money.valueOf(BigDecimal.valueOf(100.01), "CNY")));
		Assert.assertTrue(!Money.valueOf(BigDecimal.valueOf(100), "CNY")
				.sameValueAs(Money.valueOf(BigDecimal.valueOf(100), "USD")));
		Assert.assertTrue(!Money.valueOf(BigDecimal.valueOf(100), "CNY")
				.sameValueAs(null));
		Assert.assertEquals(Money.valueOf(BigDecimal.valueOf(100)),
				Money.valueOf(BigDecimal.valueOf(100.00)));
	}

	@Test
	public void negate() {
		Money money = Money.valueOf(BigDecimal.valueOf(100));
		Money newMoney = money.negate();

		assertThat(newMoney.getAmount(), comparesEqualTo(new BigDecimal(-100)));
		assertEquals(money.getCurrency(), newMoney.getCurrency());
	}
}
