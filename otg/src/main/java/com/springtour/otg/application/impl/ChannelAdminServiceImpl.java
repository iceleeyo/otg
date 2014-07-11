/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.application.impl;

import com.springtour.otg.application.ChannelAdminService;
import com.springtour.otg.domain.model.channel.Channel;
import com.springtour.otg.domain.model.channel.ChannelRepository;
import com.springtour.otg.domain.model.channel.Gateway;
import java.util.List;

/**
 *
 * @author 001595
 */
public class ChannelAdminServiceImpl implements ChannelAdminService {

    private ChannelRepository channelRepository;

    public void setChannelRepository(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    @Override
    public void updateGateways(String channelId, List<Gateway> gateways) {
        Channel channel = channelRepository.find(channelId);
        channel.updateGateways(gateways);
        channelRepository.store(channel);
    }

    @Override
    public void updateAvailableCurrencies(String channelId, String availableCurrencies) {
        Channel channel = channelRepository.find(channelId);
        channel.updateAvailableCurrencies(availableCurrencies);
        channelRepository.store(channel);
    }
}
