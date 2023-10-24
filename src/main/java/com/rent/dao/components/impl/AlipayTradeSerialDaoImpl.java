package com.rent.dao.components.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.common.enums.components.EnumAliPayStatus;
import com.rent.common.enums.components.EnumPayType;
import com.rent.common.enums.components.EnumTradeType;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.components.AlipayTradeSerialDao;
import com.rent.mapper.components.AlipayTradeSerialMapper;
import com.rent.model.components.AlipayTradeSerial;
import org.springframework.stereotype.Repository;

/**
 * AlipayTradeSerialDao
 *
 * @author xiaoyao
 * @Date 2020-06-24 16:11
 */
@Repository
public class AlipayTradeSerialDaoImpl extends AbstractBaseDaoImpl<AlipayTradeSerial, AlipayTradeSerialMapper>
        implements AlipayTradeSerialDao {

    @Override
    public AlipayTradeSerial selectOneBySerialNo(String serialNo, EnumAliPayStatus aliPayStatus) {
        return this.getOne(new QueryWrapper<>(AlipayTradeSerial.builder()
                .serialNo(serialNo)
                .status(aliPayStatus)
                .build()));
    }

    @Override
    public AlipayTradeSerial selectOneBySerialNoByOrderId(String orderId, EnumAliPayStatus aliPayStatus, EnumTradeType tradeType, EnumPayType payType) {
        return this.getOne(new QueryWrapper<>(AlipayTradeSerial.builder()
                .orderId(orderId)
                .status(aliPayStatus)
                .tradeType(tradeType)
                .payType(payType)
                .build()).orderByDesc("create_time").last("limit 1"));
    }

    @Override
    public AlipayTradeSerial selectOneBySerialNo(String serialNo) {
        return this.getOne(new QueryWrapper<>(AlipayTradeSerial.builder()
                .serialNo(serialNo)
                .build()));
    }
}
