package com.springtour.otg.interfaces.transacting.facade.internal.assembler;

import com.springtour.otg.domain.model.merchant.Merchant;
import com.springtour.otg.interfaces.transacting.facade.dto.MerchantDTO;

/**
 * 商户DTO装配器
 * 
 * @author Hippoom
 * 
 */
public class MerchantDTOAssembler {
	/**
	 * 将商户model转为商户DTO
	 * 
	 * @param merchant
	 * @return
	 */
	public MerchantDTO toDTO(Merchant merchant) {
		MerchantDTO dto = new MerchantDTO();
		dto.setId(String.valueOf(merchant.getId()));
		dto.setCode(merchant.getCode());
		dto.setKey(merchant.getKey());
		dto.setChannelId(String.valueOf(merchant.getChannel().getId()));
		dto.setOrgId(merchant.getOrgId());
		dto.setName(merchant.getName());
		return dto;
	}
}
