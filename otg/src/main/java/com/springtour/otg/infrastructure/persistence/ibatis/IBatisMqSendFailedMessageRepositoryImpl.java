/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.infrastructure.persistence.ibatis;

import com.springtour.otg.infrastructure.messaging.FailedMessage;
import com.springtour.otg.infrastructure.messaging.MqSendFailedMessageRepository;
import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

/**
 *
 * @author Future
 */
public class IBatisMqSendFailedMessageRepositoryImpl extends SqlMapClientDaoSupport implements MqSendFailedMessageRepository {

    private final String SQLMAP_NAMESPACE = "com/springtour/otg/infrastructure/persistence/ibatis/MqSendFailedMessageRepositorySqlMap";

    @Override
    public Object store(FailedMessage message) {
        return getSqlMapClientTemplate().insert(SQLMAP_NAMESPACE + ".store", message);
    }

    @Override
    public List<FailedMessage> loadAll() {
        return getSqlMapClientTemplate().queryForList(SQLMAP_NAMESPACE + ".loadAll");
    }

    @Override
    public int delete(Long id) {
        return getSqlMapClientTemplate().delete(SQLMAP_NAMESPACE + ".delete", id);
    }
}
