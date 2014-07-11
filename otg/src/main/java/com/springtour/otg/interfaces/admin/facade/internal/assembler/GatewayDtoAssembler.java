/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.interfaces.admin.facade.internal.assembler;

import com.springtour.otg.domain.model.channel.Gateway;
import com.springtour.otg.domain.model.channel.Gateways;
import com.springtour.otg.interfaces.admin.facade.dto.GatewayDto;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;

/**
 *
 * @author 006874
 */
public class GatewayDtoAssembler {

    public static List<GatewayDto> toGatewayDtoList(List<Gateway> gateways) {

        List<GatewayDto> gatewayDtos = new ArrayList<GatewayDto>();

        if (CollectionUtils.isEmpty(gateways)) {
            return Collections.EMPTY_LIST;
        }
        for (Gateway gateway : gateways) {
            gatewayDtos.add(toGatewayDto(gateway));
        }
        return gatewayDtos;
    }

    private static GatewayDto toGatewayDto(Gateway gateway) {
        GatewayDto dto = new GatewayDto();
        dto.setCode(gateway.getCode());
        dto.setDialect(gateway.getDialect());
        dto.setPriority(gateway.getPriority());
        dto.setName(Gateways.getName(gateway.getCode()));
        return dto;
    }

    public List<Gateway> fromDto(List<GatewayDto> dtos) {
        if (dtos == null) {
            return Collections.EMPTY_LIST;
        } else {
            List<Gateway> gateways = new ArrayList<Gateway>();
            for (GatewayDto dto : dtos) {
                gateways.add(fromDto(dto));
            }
            return gateways;
        }

    }

    public Gateway fromDto(GatewayDto dto) {
        if (dto == null) {
            return null;
        } else {
            return new Gateway(Gateways.of(dto.getCode()), dto.getDialect(), dto.getPriority());
        }

    }
}
