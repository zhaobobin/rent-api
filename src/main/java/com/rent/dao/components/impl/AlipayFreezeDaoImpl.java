package com.rent.dao.components.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.common.enums.components.EnumAliPayStatus;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.components.AlipayFreezeDao;
import com.rent.mapper.components.AlipayFreezeMapper;
import com.rent.model.components.AlipayFreeze;
import org.springframework.stereotype.Repository;

/**
 * AlipayFreezeDao
 *
 * @author xiaoyao
 * @Date 2020-06-24 16:11
 */
@Repository
public class AlipayFreezeDaoImpl extends AbstractBaseDaoImpl<AlipayFreeze, AlipayFreezeMapper>
    implements AlipayFreezeDao {

    @Override
    public AlipayFreeze selectOneByOutRequestNo(String outRequestNo) {
        return this.getOne(new QueryWrapper<>(AlipayFreeze.builder()
            .outRequestNo(outRequestNo)
            .build()));
    }

    @Override
    public AlipayFreeze selectOneByOrderId(String orderId, EnumAliPayStatus status) {
        return this.getOne(new QueryWrapper<>(AlipayFreeze.builder()
            .orderId(orderId)
            .status(status)
            .build()));
    }
}
