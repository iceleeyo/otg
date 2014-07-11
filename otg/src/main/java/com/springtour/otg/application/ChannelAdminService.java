/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.application;

import java.util.List;

import com.springtour.otg.domain.model.channel.Gateway;

/**
 *
 * @author 001595
 */
public interface ChannelAdminService  {

    void updateGateways(String channelId, List<Gateway> gateways);

    void updateAvailableCurrencies(String channelId, String availableCurrencies);
}
