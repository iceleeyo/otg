package com.springtour.otg.application.impl;

import java.util.List;

import com.springtour.otg.application.MerchantAdminService;
import com.springtour.otg.domain.model.channel.Channel;
import com.springtour.otg.domain.model.merchant.Merchant;
import com.springtour.otg.domain.model.merchant.MerchantRepository;

public class MerchantAdminServiceImpl implements MerchantAdminService {

	@Override
	public void register(String name, String channelId, String orgId,
			String code, String key) {
		Merchant merchant = new Merchant(name, new Channel(channelId), orgId,
				code, key);
		merchantRepository.store(merchant);
	}

	@Override
	public long count(String channelId, String orgId) {
		return merchantRepository.count(channelId, orgId);
	}

	@Override
	public List<Merchant> list(String channelId, String orgId,
			long firstResult, int maxResults) {
		return merchantRepository.list(channelId, orgId, firstResult,
				maxResults);
	}

	@Override
	public void updateInfo(String merchantId, String name, String code,
			String key, boolean enabled) {
		Merchant merchant = merchantRepository.find(merchantId);
		merchant.updateName(name);
		merchant.updateCode(code);
		merchant.updateKey(key);
		merchant.switchState(enabled);
		merchantRepository.store(merchant);
	}

	private MerchantRepository merchantRepository;

	public void setMerchantRepository(MerchantRepository merchantRepository) {
		this.merchantRepository = merchantRepository;
	}

}
