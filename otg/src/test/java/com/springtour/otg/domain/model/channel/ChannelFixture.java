/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.domain.model.channel;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author 001595
 */
public class ChannelFixture {

    public static final String DEFAULT_CHANNEL = Channels.CHINAPNR.getId();
    public static final String DEFAULT_AVAILABLE_CURRENCIES = "CNY";
    public static final List<Gateway> DEFAULT_GATEWAYS = Arrays.asList(new Gateway(Gateways.CCB, "T1", 0), new Gateway(Gateways.CITIC, "T5", 1));
    public static final String DEFAULT_GATEWAY = "CCB";
    private String id = DEFAULT_CHANNEL;
    private String availableCurrencies = DEFAULT_AVAILABLE_CURRENCIES;
    private List<Gateway> gateways = DEFAULT_GATEWAYS;

    public Channel build() {
        Channel channel = new Channel(id);
        channel.setName("汇付天下");
        channel.updateGateways(gateways);
        channel.updateAvailableCurrencies(availableCurrencies);
        return channel;
    }
}
