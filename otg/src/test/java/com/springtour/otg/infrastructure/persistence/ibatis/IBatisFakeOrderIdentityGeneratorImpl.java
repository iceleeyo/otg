package com.springtour.otg.infrastructure.persistence.ibatis;

import com.springtour.otg.FakeOrderIdentityGenerator;
import com.springtour.otg.OtgTestScenarios;
import com.springtour.otg.domain.model.transaction.OrderIdentity;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class IBatisFakeOrderIdentityGeneratorImpl extends SqlMapClientDaoSupport
        implements FakeOrderIdentityGenerator {
    //借用Notification的sequence
    private final String nameSpace = "com/springtour/otg/infrastructure/persistence/ibatis/NotificationRepositorySqlMap";

    @Override
    public OrderIdentity newOrderIdentity() {
        String orderId = (String) getSqlMapClientTemplate().queryForObject(
                nameSpace + ".nextSequence");
        return OrderIdentity.valueOf(OtgTestScenarios.AN_ALWAYS_SUCCEED_PAYMENT_APP, orderId, "ABC" + orderId);
    }
}
