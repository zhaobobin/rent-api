package com.rent.dao.components.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.common.enums.components.EnumAntChainLogStatus;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.components.AntChainInsuranceLogDao;
import com.rent.mapper.components.AntChainInsuranceLogMapper;
import com.rent.model.components.AntChainInsuranceLog;
import org.springframework.stereotype.Repository;


/**
 * @author zhaowenchao
 */
@Repository
public class AntChainInsuranceLogDaoImpl extends AbstractBaseDaoImpl<AntChainInsuranceLog, AntChainInsuranceLogMapper> implements AntChainInsuranceLogDao {

    @Override
    public AntChainInsuranceLog getLastSuccessByOrderId(String orderId) {
        return getOne(new QueryWrapper<AntChainInsuranceLog>()
                .eq("order_id",orderId)
                .eq("status", EnumAntChainLogStatus.SUCCESS)
                .orderByDesc("id")
                .last("limit 1"));
    }
}
