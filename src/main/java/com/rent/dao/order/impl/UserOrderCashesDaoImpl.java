package com.rent.dao.order.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.rent.common.dto.components.dto.AlipayTradeDiscountDto;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.order.UserOrderCashesDao;
import com.rent.mapper.order.UserOrderCashesMapper;
import com.rent.model.order.UserOrderCashes;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * UserOrderCashesDao
 *
 * @author xiaoyao
 * @Date 2020-06-15 17:08
 */
@Repository
public class UserOrderCashesDaoImpl extends AbstractBaseDaoImpl<UserOrderCashes, UserOrderCashesMapper>
    implements UserOrderCashesDao {

    @Override
    public boolean checkExitsByOrderId(String orderId) {
        UserOrderCashes example = new UserOrderCashes();
        example.setOrderId(orderId);
        return count(new QueryWrapper<>(example)) > 0;
    }

    @Override
    public UserOrderCashes selectOneByOrderId(String orderId) {
        return getOne(new QueryWrapper<>(UserOrderCashes.builder()
            .orderId(orderId)
            .build())
            .last("limit 1")
        );
    }

    @Override
    public Map<String, UserOrderCashes> queryListByOrderIds(List<String> orderIdList) {
        QueryWrapper<UserOrderCashes> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("order_id", orderIdList);
        List<UserOrderCashes> list = this.list(queryWrapper);
        if (CollectionUtil.isEmpty(list)) {
            return Maps.newHashMap();
        }
        return list.stream()
            .collect(Collectors.toMap(UserOrderCashes::getOrderId, Function.identity(), (key1, key2) -> key2));
    }

    @Override
    public List<UserOrderCashes> queryByOrderIds(List<String> orderIdList) {
        QueryWrapper<UserOrderCashes> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("order_id", orderIdList);
        List<UserOrderCashes> list = this.list(queryWrapper);
        if (CollectionUtil.isEmpty(list)) {
            return Lists.newArrayList();
        }
        return list;
    }

    @Override
    public Boolean repairOrderCashes(AlipayTradeDiscountDto tradeDiscountDto) {
        UserOrderCashes cashes = this.selectOneByOrderId(tradeDiscountDto.getOrderId());
        if(null != cashes){
            //押金减冻结金额是分期的金额
            BigDecimal totalAmount = cashes.getFreezePrice().subtract(cashes.getDeposit());
            //判断cashes的支付金额是否和回调支付前的金额相等
            if(totalAmount.compareTo(tradeDiscountDto.getTotalAmount()) == 0){
                //修复分期金额
                this.updateById(cashes);
            }
        }
        return Boolean.TRUE;
    }

    @Override
    public BigDecimal getAllOrdersTotolRent(List<String> orderIds) {
        List<UserOrderCashes> cashes = this.list(new QueryWrapper<>(new UserOrderCashes())
                .in("`order_id`", orderIds)
                .groupBy("order_id")
        );
        if(CollectionUtils.isNotEmpty(cashes)){
            BigDecimal totolRent = cashes.stream().map(UserOrderCashes::getTotalRent).reduce(BigDecimal.ZERO, BigDecimal::add);
            return totolRent;
        }
        return null;
    }


}
