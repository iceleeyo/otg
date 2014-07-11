package com.springtour.otg.infrastructure.persistence.ibatis;

import com.springtour.otg.domain.model.channel.Channel;
import com.springtour.otg.domain.model.channel.ChannelRepository;
import com.springtour.otg.domain.model.channel.Gateway;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class IBatisChannelRepositoryImpl extends SqlMapClientDaoSupport
        implements ChannelRepository {

    private final String nameSpace = "com/springtour/otg/infrastructure/persistence/ibatis/ChannelRepositorySqlMap";

    @Override
    public Channel find(String channelId) {
        return (Channel) super.getSqlMapClientTemplate().queryForObject(nameSpace + ".findById", channelId);
    }

    @Override
    public List<Channel> listAll() {
        return super.getSqlMapClientTemplate().queryForList(nameSpace + ".findAll");
    }

    @Override
    public void store(Channel channel) {
        if (channel.isPersistent()) {
            update(channel);
        } else {
            save(channel);
        }
        channel.resetPersistent();
        channel.resetUpdated();
    }

    private boolean isPersistent(Channel channel) {
        return find(channel.getId()) != null;
    }

    private void save(Channel channel) {
        getSqlMapClientTemplate().update(nameSpace + ".save", channel);
        updateGateways(channel);
    }

    private void update(Channel channel) {

        if (channel.isUpdated()) {
            if (channel.isGatewaysUpdated()) {//采用先删除后插入的方式更新
                updateGateways(channel);
            }
            int rowAffected = getSqlMapClientTemplate().update(nameSpace + ".update", channel);//这步放在所有其他SQL之后用来验证version
            if (rowAffected != 1) {
                throw new ObjectOptimisticLockingFailureException(channel.getClass(), channel.getId());
            }
        } else {
            //无更新
        }
    }

    private void updateGateways(Channel channel) {
        getSqlMapClientTemplate().delete(
                nameSpace + ".removeGateways", channel);
        for (Gateway g : channel.getGateways()) {
            Map params = new HashMap();
            params.put("channelId", channel.getId());
            params.put("gateway", g);
            params.put("priority", g.getPriority());
            getSqlMapClientTemplate().insert(
                    nameSpace + ".saveGateways", params);
        }
    }
}
