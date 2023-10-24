package com.rent.dao.components.impl;

import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.components.OrderCenterSyncLogDao;
import com.rent.mapper.components.OrderCenterSyncLogMapper;
import com.rent.model.components.OrderCenterSyncLog;
import org.springframework.stereotype.Repository;

/**
 * OrderCenterSyncLogDao
 *
 * @author xiaoyao
 * @Date 2020-06-24 16:12
 */
@Repository
public class OrderCenterSyncLogDaoImpl extends AbstractBaseDaoImpl<OrderCenterSyncLog, OrderCenterSyncLogMapper>
    implements OrderCenterSyncLogDao {

}
