package com.rent.dao.order.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.constant.SplitBillRecordConstant;
import com.rent.common.dto.order.AccountPeriodItemReqDto;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.order.ChannelSplitBillDao;
import com.rent.mapper.order.ChannelSplitBillMapper;
import com.rent.model.order.ChannelSplitBill;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

/**
 * SplitBillDao
 *
 * @author zhao
 * @Date 2020-08-11 09:59
 */
@Repository
public class ChannelSplitBillDaoImpl extends AbstractBaseDaoImpl<ChannelSplitBill, ChannelSplitBillMapper> implements ChannelSplitBillDao {

    @Override
    public void updateSettled(Long accountPeriodId) {
        ChannelSplitBill channelSplitBill = new ChannelSplitBill();
        channelSplitBill.setStatus(SplitBillRecordConstant.STATUS_SETTLED);
        update(channelSplitBill,new QueryWrapper<ChannelSplitBill>().eq("account_period_id",accountPeriodId));
    }

    @Override
    public Page<ChannelSplitBill> queryPage(AccountPeriodItemReqDto request) {
        Page<ChannelSplitBill> page = page(new Page<>(request.getPageNumber(), request.getPageSize()),
                new QueryWrapper<ChannelSplitBill>()
                        .eq(StringUtils.isNotEmpty(request.getShopId()),"uid",request.getShopId())
                        .eq(StringUtils.isNotEmpty(request.getOrderId()),"order_id",request.getOrderId())
                        .between(request.getStartTime()!=null,"create_time",request.getStartTime(),request.getEndTime())
                        .orderByDesc("create_time")
        );
        return page;
    }

    @Override
    public Page<ChannelSplitBill> queryPage(Integer pageNumber, Integer pageSize, Long accountPeriodId) {
        Page<ChannelSplitBill> page = page(new Page<>(pageNumber, pageSize),
                new QueryWrapper<ChannelSplitBill>()
                        .eq("account_period_id",accountPeriodId)
                        .orderByDesc("create_time")
        );
        return page;
    }

    @Override
    public ChannelSplitBill getByOrderId(String orderId, Integer period) {
        return getOne(new QueryWrapper<ChannelSplitBill>().eq("order_id",orderId).eq(period!=null,"period",period));
    }
}
