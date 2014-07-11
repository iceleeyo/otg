/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.domain.model.merchant;

import com.springtour.otg.domain.model.channel.*;

/**
 *
 * @author 001595
 */
public class MerchantFixture {

    public static final String DEFAULT_CODE = "510149";
    public static final String DEFAULT_KEY = "MerPrK510149.key";
    public static final String DEFAULT_NAME = "ORM";
    public static final String DEFAULT_ORG_ID = "61";
    public static final Channel DEFAULT_CHANNEL = new ChannelFixture().build();
    private String code = DEFAULT_CODE;
    private String key = DEFAULT_KEY;
    private String name = DEFAULT_NAME;
    private String orgId = DEFAULT_ORG_ID;
    private Channel channel = DEFAULT_CHANNEL;

    public Merchant build() {
        Merchant merchant = new Merchant(name, channel, orgId, code,
                key);
        return merchant;
    }
}
