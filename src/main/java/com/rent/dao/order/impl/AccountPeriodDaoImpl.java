package com.rent.dao.order.impl;

import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.order.AccountPeriodDao;
import com.rent.mapper.order.AccountPeriodMapper;
import com.rent.model.order.AccountPeriod;
import org.springframework.stereotype.Repository;

/**
 * AccountPeriodDao
 *
 * @author xiaoyao
 * @Date 2020-06-16 10:09
 */
@Repository
public class AccountPeriodDaoImpl extends AbstractBaseDaoImpl<AccountPeriod, AccountPeriodMapper> implements
    AccountPeriodDao {


}
