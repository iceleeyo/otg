package com.springtour.otg.domain.model.merchant;

import java.util.List;

/**
 * 商户仓库
 * 
 * @author Hippoom
 * 
 */
public interface MerchantRepository {
	/**
	 * 查找商户
	 * 
	 * @param channelId
	 *            支付渠道标识
	 * @param orgId
	 *            组织标识
	 * @return
	 */
	Merchant find(String channelId, String orgId);

	/**
	 * 查找有效商户
	 * 
	 * @param channelId
	 * @param merchantCode
	 * @return
	 */
	Merchant findEnabledByChannelAndCode(String channelId, String merchantCode);

	void store(Merchant merchant);

	Merchant find(String merchantId);

	long count(String channelId, String orgId);

	List<Merchant> list(String channelId, String orgId, long firstResult,
			int maxResults);
}
