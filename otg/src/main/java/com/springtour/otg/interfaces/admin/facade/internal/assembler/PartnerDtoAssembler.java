/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.interfaces.admin.facade.internal.assembler;

import com.springtour.otg.domain.model.partner.Partner;
import com.springtour.otg.interfaces.admin.facade.dto.PartnerDto;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author 006874
 */
public class PartnerDtoAssembler {
    
    private PartnerDto toDto(Partner partner) {
        PartnerDto dto = new PartnerDto();
        dto.setId(partner.getId());
        dto.setName(partner.getName());
        return dto;
    }
    
    public List<PartnerDto> toDtos(List<Partner> partners) {
        if (partners == null) {
            return Collections.EMPTY_LIST;
        }
        List<PartnerDto> dtos = new ArrayList<PartnerDto>();
        for (Partner partner : partners) {
            dtos.add(toDto(partner));
        }
        return dtos;
    }
}
