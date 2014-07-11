package com.springtour.otg.infrastructure.persistence.ibatis;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import com.springtour.otg.domain.model.notification.Notification;
import com.springtour.otg.domain.model.notification.NotificationRepository;
import com.springtour.otg.domain.model.transaction.TransactionNo;

public class IBatisNotificationRepositoryImpl extends SqlMapClientDaoSupport
        implements NotificationRepository {

    private final String nameSpace = "com/springtour/otg/infrastructure/persistence/ibatis/NotificationRepositorySqlMap";

    @Override
    public String nextSequence() {
        return (String) getSqlMapClientTemplate().queryForObject(
                nameSpace + ".nextSequence");
    }

    @Override
    public void store(Notification notification) {
        getSqlMapClientTemplate().insert(nameSpace + ".save", notification);
    }

    @Override
    public Notification find(String sequence) {
        return (Notification) getSqlMapClientTemplate().queryForObject(nameSpace + ".findBySequence", sequence);
    }

    @Override
    public List<Notification> find(TransactionNo transactionNo) {
        return getSqlMapClientTemplate().queryForList(nameSpace + ".findByTxnNo", transactionNo.getNumber());
    }
}
