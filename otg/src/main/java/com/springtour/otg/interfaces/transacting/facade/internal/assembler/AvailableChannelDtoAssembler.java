package com.springtour.otg.interfaces.transacting.facade.internal.assembler;

import com.springtour.otg.application.exception.UnavailableCurrencyException;
import com.springtour.otg.domain.model.channel.Channel;
import com.springtour.otg.interfaces.transacting.facade.dto.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AvailableChannelDtoAssembler {

    public List<AvailableChannelDto> toDto(List<Channel> channels) throws UnavailableCurrencyException {
        if (channels == null) {
            return Collections.EMPTY_LIST;
        } else {
            List<AvailableChannelDto> dtos = new ArrayList<AvailableChannelDto>();
            for (Channel channel : channels) {
                dtos.add(toDto(channel));
            }
            return dtos;
        }
    }

    public AvailableChannelDto toDto(Channel channel) throws UnavailableCurrencyException {
        if (channel == null) {
            return null;
        } else {
            AvailableChannelDto dto = new AvailableChannelDto();
            dto.setChannelId(channel.getId());
            dto.setAvailableCurrencies(channel.availableCurrencyCodes());
            dto.setGateways(channel.gatewayCodes());
            return dto;
        }

    }
}
