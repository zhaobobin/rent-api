package com.rent.dao.order.impl;

import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.order.AccountPeriodRemarkDao;
import com.rent.mapper.order.AccountPeriodRemarkMapper;
import com.rent.model.order.AccountPeriodRemark;
import org.springframework.stereotype.Repository;

/**
 * 账期备注
 * @author zhaowenchao
 */
@Repository
public class AccountPeriodRemarkDaoImpl extends AbstractBaseDaoImpl<AccountPeriodRemark, AccountPeriodRemarkMapper> implements AccountPeriodRemarkDao {


}
