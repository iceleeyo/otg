package com.springtour.otg.domain.model.channel;

import com.springtour.otg.application.exception.UnavailableCurrencyException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.springtour.otg.domain.model.transaction.TransactionNo;
import com.springtour.otg.domain.shared.WrapCurrency;
import java.util.Arrays;
import java.util.List;

@RunWith(JMock.class)
public class ChannelMockTests {

    private Mockery context = new JUnit4Mockery() {

        {
            setImposteriser(ClassImposteriser.INSTANCE);
        }
    };
    private Channel target;
    private String channelId = "12";
    private String code_cmb = "cmb";
    private String dialect_cmb = "T1";
    private String code_ccb = "CCB";
    private String dialect_ccb = "T2";

    @Before
    public void setUp() throws UnavailableCurrencyException {
        target = new Channel(channelId);
        target.setGateways(Arrays.asList(new Gateway(Gateways.CMB, dialect_cmb,0), new Gateway(Gateways.CCB, dialect_ccb,1)));
        target.setAvailableCurrencies(WrapCurrency.valueOf("CNY").getCode() + "," + WrapCurrency.valueOf("USD").getCode());
    }

    @Test
    public void returnsAGatewayWhenCodeMatches() {
        Gateway actual = target.getGateway(code_ccb);

        Assert.assertEquals(dialect_ccb, actual.getDialect());
    }

    @Test
    public void returnsNullWhenCodeMismatches() {
        Gateway actual = target.getGateway("spdb");

        Assert.assertTrue(actual == null);
    }

    @Test
    public void returnsNullGivenNull() {
        Gateway actual = target.getGateway(null);

        Assert.assertTrue(actual == null);
    }

    @Test
    public void returnsTrueWhenCurrencyMatched() throws UnavailableCurrencyException {
        Assert.assertTrue(target.support(WrapCurrency.valueOf("USD")));
    }

    @Test
    public void returnsTrueWhenCurrencyMicmatched() throws UnavailableCurrencyException {
        Assert.assertTrue(!target.support(WrapCurrency.valueOf("GBP")));
    }

    @Test
    public void supportsAGateway() throws UnavailableCurrencyException {
        Assert.assertTrue(target.support("CCB"));
        Assert.assertTrue(!target.support("CMBC"));
        Assert.assertTrue(!target.support("aaa"));
        final String gateway = null;
        Assert.assertTrue(!target.support(gateway));
    }

    @Test
    public void availableCurrencies() throws UnavailableCurrencyException {
        System.out.println(target.getAvailableCurrencies());
        Assert.assertEquals("CNY,USD", target.getAvailableCurrencies());
        List<WrapCurrency> availableCurrencies = target.availableCurrencies();
        Assert.assertTrue(availableCurrencies.size() == 2);
        Assert.assertTrue(WrapCurrency.valueOf("CNY").sameValueAs(availableCurrencies.get(0)));
        Assert.assertTrue(WrapCurrency.valueOf("USD").sameValueAs(availableCurrencies.get(1)));
    }
}
