package com.rent.dao.components.impl;


import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.components.AntChainShieldLogDao;
import com.rent.mapper.components.AntChainShieldLogMapper;
import com.rent.model.components.AntChainShieldLog;
import org.springframework.stereotype.Repository;

/**
 * @author zhaowenchao
 */
@Repository
public class AntChainShieldLogDaoImpl extends AbstractBaseDaoImpl<AntChainShieldLog, AntChainShieldLogMapper> implements AntChainShieldLogDao {

}
