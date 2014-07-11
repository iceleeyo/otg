package com.springtour.otg.domain.model.channel;

import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMock.class)
public class GatewaysMockTests {

    private Mockery context = new JUnit4Mockery() {

        {
            setImposteriser(ClassImposteriser.INSTANCE);
        }
    };
    private Gateways target;

    /**
     * 5
     * 
     * @throws Exception
     */
    @Test
    public void returnsACode() throws Exception {
        target = Gateways.ABC;

        Assert.assertEquals("ABC", target.code());
    }

    @Test
    public void returnsAFileName() throws Exception {
        target = Gateways.ABC;

        Assert.assertEquals("abc.gif", target.imageFileName());
    }

    @Test
    public void getsInstances() throws Exception {
        target = Gateways.of("ABC");
        Assert.assertEquals(Gateways.ABC, target);

        target = Gateways.of("abc");
        Assert.assertTrue(target == null);
        
        target = Gateways.of(null);
        Assert.assertTrue(target == null);
    }
}
