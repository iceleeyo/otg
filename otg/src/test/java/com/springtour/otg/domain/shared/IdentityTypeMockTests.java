package com.springtour.otg.domain.shared;

import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMock.class)
public class IdentityTypeMockTests {

    private Mockery context = new JUnit4Mockery() {

        {
            setImposteriser(ClassImposteriser.INSTANCE);
        }
    };
    private IdentityType target;

    @Test
    public void returnsInstancesByCode() throws Exception {
        Assert.assertTrue(IdentityType.IDENTITY == IdentityType.of("identity"));
        Assert.assertTrue(IdentityType.PASSPORT == IdentityType.of("passport"));
        Assert.assertTrue(IdentityType.OTHERS == IdentityType.of("others"));
    }
    
    @Test
    public void returnsNullGivenAUnrecognizedCode() throws Exception {
        Assert.assertTrue(null == IdentityType.of("abc"));
        Assert.assertTrue(null == IdentityType.of(null));
    }
    
    @Test
    public void sameCodeAs() throws Exception {
        Assert.assertTrue(IdentityType.IDENTITY.sameCodeAs("identity"));
        Assert.assertTrue(IdentityType.PASSPORT.sameCodeAs("passport"));
        Assert.assertTrue(!IdentityType.IDENTITY.sameCodeAs("passport"));
        Assert.assertTrue(!IdentityType.IDENTITY.sameCodeAs(null));
    }
}
