package com.rent.dao.components.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.common.enums.components.EnumAliPayStatus;
import com.rent.common.enums.components.EnumPayType;
import com.rent.common.enums.components.EnumTradeType;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.components.AlipayTradeSerialDao;
import com.rent.dao.components.YfbTradeSerialDao;
import com.rent.mapper.components.AlipayTradeSerialMapper;
import com.rent.mapper.components.YfbTradeSerialMapper;
import com.rent.model.components.AlipayTradeSerial;
import com.rent.model.components.YfbTradeSerial;
import org.springframework.stereotype.Repository;

/**
 * AlipayTradeSerialDao
 *
 * @author xiaoyao
 * @Date 2020-06-24 16:11
 */
@Repository
public class YfbTradeSerialDaoImpl extends AbstractBaseDaoImpl<YfbTradeSerial, YfbTradeSerialMapper>
        implements YfbTradeSerialDao {

    @Override
    public YfbTradeSerial selectOneBySerialNo(String serialNo, EnumAliPayStatus aliPayStatus) {
        return this.getOne(new QueryWrapper<>(YfbTradeSerial.builder()
                .serialNo(serialNo)
                .status(aliPayStatus)
                .build()));
    }

    @Override
    public YfbTradeSerial selectOneBySerialNoByOrderId(String orderId, EnumAliPayStatus aliPayStatus, EnumTradeType tradeType, EnumPayType payType) {
        return this.getOne(new QueryWrapper<>(YfbTradeSerial.builder()
                .orderId(orderId)
                .status(aliPayStatus)
                .tradeType(tradeType)
                .payType(payType)
                .build()).orderByDesc("create_time").last("limit 1"));
    }

    @Override
    public YfbTradeSerial selectOneBySerialNo(String serialNo) {
        return this.getOne(new QueryWrapper<>(YfbTradeSerial.builder()
                .serialNo(serialNo)
                .build()));
    }
}
