package com.rent.dao.components.impl;

import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.components.AlipayGateWayDao;
import com.rent.mapper.components.AlipayGateWayMapper;
import com.rent.model.components.AlipayGateWay;
import org.springframework.stereotype.Repository;

/**
 * AlipayFreezeDao
 *
 * @author xiaoyao
 * @Date 2020-06-24 16:11
 */
@Repository
public class AlipayGateWayDaoImpl extends AbstractBaseDaoImpl<AlipayGateWay, AlipayGateWayMapper> implements AlipayGateWayDao {


}
