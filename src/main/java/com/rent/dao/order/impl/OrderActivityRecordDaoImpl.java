    
package com.rent.dao.order.impl;

import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.order.OrderActivityRecordDao;
import com.rent.mapper.order.OrderActivityRecordMapper;
import com.rent.model.order.OrderActivityRecord;
import org.springframework.stereotype.Repository;

/**
 * OrderActivityRecordDao
 *
 * @author youruo
 * @Date 2021-01-26 10:59
 */
@Repository
public class OrderActivityRecordDaoImpl extends AbstractBaseDaoImpl<OrderActivityRecord, OrderActivityRecordMapper> implements OrderActivityRecordDao{


}
