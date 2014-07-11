package com.springtour.otg.infrastructure.persistence.ibatis;

import com.springtour.otg.infrastructure.time.Clock;
import java.util.Date;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class IBatisClockImpl extends SqlMapClientDaoSupport
        implements Clock {

    private final String nameSpace = "com/springtour/otg/infrastructure/persistence/ibatis/ClockSqlMap";

    @Override
    public Date now() {
        return (Date) super.getSqlMapClientTemplate().queryForObject(nameSpace + ".now");
    }
}
