/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.infrastructure.channel.bill99;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author 005073
 */
public class Bill99KeyManagerUnitTests {

    private Bill99KeyManager target;

    @Test
    public void returnsTerminalIdAndKey() throws Exception {
        target = new Bill99KeyManager();
        final String key = "merchantId,key";
        Assert.assertEquals("merchantId", target.merchantId(key));
        Assert.assertEquals("key", target.key(key));
    }
}
