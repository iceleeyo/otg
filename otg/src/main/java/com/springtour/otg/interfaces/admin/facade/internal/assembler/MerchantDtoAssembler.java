package com.springtour.otg.interfaces.admin.facade.internal.assembler;
import com.springtour.otg.domain.model.merchant.Merchant;
import com.springtour.otg.interfaces.admin.facade.dto.MerchantDto;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 商户DTO装配器
 * 
 * @author Hippoom
 * 
 */
public class MerchantDtoAssembler {

    /**
     * 将商户model转为商户DTO
     * 
     * @param merchant
     * @return
     */
    public static MerchantDto toDto(Merchant merchant) {
        MerchantDto dto = new MerchantDto();
        dto.setId(String.valueOf(merchant.getId()));
        dto.setCode(merchant.getCode());
        dto.setKey(merchant.getKey());
        dto.setChannelId(String.valueOf(merchant.getChannel().getId()));
        dto.setOrgId(merchant.getOrgId());
        dto.setName(merchant.getName());
        dto.setEnabled(merchant.isEnabled());
        return dto;
    }

    public static List<MerchantDto> toDtos(List<Merchant> merchants) {
        if (merchants == null) {
            return Collections.EMPTY_LIST;
        }
        List<MerchantDto> dtos = new ArrayList<MerchantDto>();
        for (Merchant merchant : merchants) {
            dtos.add(toDto(merchant));
        }
        return dtos;
    }
}
