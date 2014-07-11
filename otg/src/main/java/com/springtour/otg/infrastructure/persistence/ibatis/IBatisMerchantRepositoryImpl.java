package com.springtour.otg.infrastructure.persistence.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.springtour.otg.application.exception.DuplicateMerchantException;
import com.springtour.otg.domain.model.merchant.Merchant;
import com.springtour.otg.domain.model.merchant.MerchantRepository;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

public class IBatisMerchantRepositoryImpl extends SqlMapClientDaoSupport
		implements MerchantRepository {
	private final String nameSpace = "com/springtour/otg/infrastructure/persistence/ibatis/MerchantRepositorySqlMap";

	@Override
	public Merchant find(String channelId, String orgId) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("channelId", channelId);
		param.put("orgId", orgId);
		return (Merchant) getSqlMapClientTemplate().queryForObject(
				nameSpace + ".findByChannelAndOrg", param);
	}

	@Override
	public Merchant findEnabledByChannelAndCode(String channelId,
			String merchantCode) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("channelId", channelId);
		param.put("merchantCode", merchantCode);
		return (Merchant) getSqlMapClientTemplate().queryForObject(
				nameSpace + ".findKeyByCode", param);
	}

	@Override
	public void store(Merchant merchant) {
		if (null == merchant.getId()) {
			save(merchant);
		} else {
			update(merchant);
		}
	}

	private void update(Merchant merchant) {
		int rowAffected = getSqlMapClientTemplate().update(
				nameSpace + ".updateAggregateRoot", merchant);
		if (rowAffected != 1) {
			throw new ObjectOptimisticLockingFailureException(
					merchant.getClass(), merchant.getId().toString());
		}
	}

	private void save(Merchant merchant) {
		try {
			getSqlMapClientTemplate().insert(nameSpace + ".saveAggregateRoot",
					merchant);
		} catch (DataIntegrityViolationException e) {
			throw new DuplicateMerchantException(String.valueOf(merchant
					.getChannel().getId()), merchant.getOrgId());
		}
	}

	@Override
	public Merchant find(String merchantId) {
		return (Merchant) getSqlMapClientTemplate().queryForObject(
				nameSpace + ".findById", merchantId);
	}

	@Override
	public long count(String channelId, String orgId) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("channelId", channelId);
		params.put("orgId", orgId);
		return (Long) getSqlMapClientTemplate().queryForObject(
				nameSpace + ".count", params);
	}

	@Override
	public List<Merchant> list(String channelId, String orgId,
			long firstResult, int maxResults) {
		Map params = new HashMap();
		params.put("channelId", channelId);
		params.put("orgId", orgId);
		params.put("firstResult", firstResult);
		params.put("maxResults", Long.valueOf(maxResults));
		return getSqlMapClientTemplate().queryForList(nameSpace + ".list",
				params);
	}
	
	
}
