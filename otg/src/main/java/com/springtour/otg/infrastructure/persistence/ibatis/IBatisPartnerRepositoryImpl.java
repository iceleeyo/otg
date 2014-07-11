package com.springtour.otg.infrastructure.persistence.ibatis;

import com.springtour.otg.domain.model.partner.Partner;
import com.springtour.otg.domain.model.partner.PartnerRepository;
import com.springtour.otg.domain.model.partner.RecommendedGateway;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class IBatisPartnerRepositoryImpl extends SqlMapClientDaoSupport
        implements PartnerRepository {

    private final String nameSpace = "com/springtour/otg/infrastructure/persistence/ibatis/PartnerRepositorySqlMap";

    @Override
    public void store(Partner partner) {
        if (partner.isPersistent()) {
            update(partner);
        } else {
            save(partner);
        }
        partner.resetPersistent();
        partner.resetUpdated();
    }

    private void save(Partner partner) {
        getSqlMapClientTemplate().insert(nameSpace + ".save",
                partner);
        updateRecommendedGateways(partner);
    }

    private void update(Partner partner) {
        if (partner.isUpdated()) {
            updateRecommendedGateways(partner);
            int rowAffected = getSqlMapClientTemplate().update(
                    nameSpace + ".update", partner);//这步放在所有其他SQL之后用来验证version
            if (rowAffected != 1) {
                throw new ObjectOptimisticLockingFailureException(partner.getClass(), partner.getId());
            }
        } else {
            //无更新
        }
    }
    
    private void updateRecommendedGateways(Partner partner) {
        if (partner.isRecommendedGatewaysUpdated()) {//采用先删除后插入的方式更新
                getSqlMapClientTemplate().delete(
                        nameSpace + ".removeRecommendedGateways", partner);
                for (RecommendedGateway rg : partner.getRecommendedGateways()) {
                    Map params = new HashMap();
                    params.put("partnerId", partner.getId());
                    params.put("recommendedGateway", rg);
                    getSqlMapClientTemplate().insert(
                            nameSpace + ".saveRecommendedGateways", params);
                }
            }
    }

    @Override
    public Partner find(String partnerId) {
        return (Partner) getSqlMapClientTemplate().queryForObject(nameSpace + ".findById", partnerId);
    }

    @Override
    public List<Partner> listAll() {
        return getSqlMapClientTemplate().queryForList(nameSpace + ".findAll");
    }
}
