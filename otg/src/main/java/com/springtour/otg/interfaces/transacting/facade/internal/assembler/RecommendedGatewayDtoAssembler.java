package com.springtour.otg.interfaces.transacting.facade.internal.assembler;

import com.springtour.otg.domain.model.channel.Gateways;
import com.springtour.otg.domain.model.partner.RecommendedGateway;
import com.springtour.otg.interfaces.transacting.facade.dto.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecommendedGatewayDtoAssembler {
    
    public List<RecommendedGatewayDto> toDto(List<RecommendedGateway> recommendedGateways) {
        if (recommendedGateways == null) {
            return Collections.EMPTY_LIST;
        } else {
            List<RecommendedGatewayDto> dtos = new ArrayList<RecommendedGatewayDto>();
            for (RecommendedGateway gateway : recommendedGateways) {
                RecommendedGatewayDto dto = new RecommendedGatewayDto();
                dto.setChannel(gateway.getChannel());
                dto.setGateway(gateway.getGateway());
                dto.setPriority(gateway.getPriority());
                dto.setGatewayName(Gateways.getName(gateway.getGateway()));
                dtos.add(dto);
            }
            return dtos;
        }
    }
    
        public List<RecommendedGateway> fromDto(List<RecommendedGatewayDto> dtos) {
        if (dtos == null) {
            return Collections.EMPTY_LIST;
        } else {
            List<RecommendedGateway> recommendedGateways = new ArrayList<RecommendedGateway>();
            for (RecommendedGatewayDto dto : dtos) {
                RecommendedGateway recommendedGateway = new RecommendedGateway();
                recommendedGateway.setChannel(dto.getChannel());
                recommendedGateway.setGateway(dto.getGateway());
                recommendedGateway.setPriority(dto.getPriority());
                recommendedGateways.add(recommendedGateway);
            }
            return recommendedGateways;
        }
    }
}
