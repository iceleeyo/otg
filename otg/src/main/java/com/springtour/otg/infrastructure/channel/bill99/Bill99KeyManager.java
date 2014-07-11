/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.infrastructure.channel.bill99;

/**
 *
 * @author 001595
 */
public class Bill99KeyManager {
    private static final String SPERATOR = ",";
    private static final int KEY_INDEX = 1;
    private static final int MERCHANT_INDEX = 0;
    
    public String key(String merchantKey) {
        return merchantKey.split(SPERATOR)[KEY_INDEX];
    }

    public String merchantId(String merchantKey) {
        return merchantKey.split(SPERATOR)[MERCHANT_INDEX];
    }
}
