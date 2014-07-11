/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.interfaces.admin.facade.internal.assembler;

import com.springtour.otg.domain.model.channel.Channel;
import com.springtour.otg.interfaces.admin.facade.dto.ChannelDto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 006874
 */
public class ChannelDtoAssembler {

    public static ChannelDto toChannelDto(Channel channel) {
        ChannelDto dto = new ChannelDto();
        dto.setAvailableCurrencies(channel.getAvailableCurrencies());
        dto.setGateways(GatewayDtoAssembler.toGatewayDtoList(channel.getGateways()));
        dto.setId(channel.getId());
        dto.setName(channel.getName());
        return dto;
    }

    public static List<ChannelDto> toDtos(List<Channel> channels) {
        List<ChannelDto> result = new ArrayList<ChannelDto>();
        for (Channel channel : channels) {
            result.add(toChannelDto(channel));
        }
        return result;
    }
}
