package com.springtour.otg.domain.shared;

import com.springtour.otg.application.exception.UnavailableCurrencyException;
import java.util.Currency;
import java.util.Locale;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMock.class)
public class WrapCurrencyMockTests {

    private Mockery context = new JUnit4Mockery() {

        {
            setImposteriser(ClassImposteriser.INSTANCE);
        }
    };
    private WrapCurrency target;

    @Test
    public void returnsACNY() throws Exception {
        target = WrapCurrency.valueOf("CNY");
    }

    @Test(expected = UnavailableCurrencyException.class)
    public void throwsAnExceptionGivenAUnsupportedCurrencyCode() throws Exception {
        target = WrapCurrency.valueOf("cny");
    }

    @Test
    public void sameValueAs() throws Exception {
        Assert.assertTrue(WrapCurrency.valueOf("CNY").sameValueAs(WrapCurrency.valueOf(Currency.getInstance(Locale.CHINA).getCurrencyCode())));
        Assert.assertTrue(!WrapCurrency.valueOf("CNY").sameValueAs(WrapCurrency.valueOf(Currency.getInstance(Locale.US).getCurrencyCode())));
        Assert.assertTrue(!WrapCurrency.valueOf("CNY").sameValueAs(null));
    }

    @Test
    public void sameCodeAs() throws Exception {
        Assert.assertTrue(WrapCurrency.valueOf("CNY").sameCodeAs(Currency.getInstance(Locale.CHINA).getCurrencyCode()));
        Assert.assertTrue(!WrapCurrency.valueOf("CNY").sameCodeAs("cny"));
        Assert.assertTrue(!WrapCurrency.valueOf("CNY").sameCodeAs(Currency.getInstance(Locale.US).getCurrencyCode()));
        Assert.assertTrue(!WrapCurrency.valueOf("CNY").sameCodeAs(null));
    }
}
