package com.rent.dao.components.impl;

import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.components.AlipayTradePagePayDao;
import com.rent.mapper.components.AlipayTradePagePayMapper;
import com.rent.model.components.AlipayTradePagePay;
import org.springframework.stereotype.Repository;

/**
 * @author zhaowenchao
 */
@Repository
public class AlipayTradeAppPageDaoImpl extends AbstractBaseDaoImpl<AlipayTradePagePay, AlipayTradePagePayMapper>implements AlipayTradePagePayDao {

}
