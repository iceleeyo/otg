package com.springtour.otg.application;

import java.util.List;

import com.springtour.otg.application.exception.DuplicateMerchantException;
import com.springtour.otg.domain.model.merchant.Merchant;

public interface MerchantAdminService {
	void register(String name, String channelId, String orgId, String code,
			String key) throws DuplicateMerchantException;

	void updateInfo(String merchantId, String name, String code, String key,
			boolean enabled);

	List<Merchant> list(String channelId, String orgId, long firstResult,
			int maxResults);

	long count(String channelId, String orgId);
}
