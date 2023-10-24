package com.rent.dao.order.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.common.dto.order.ChannelOrdersExportDto;
import com.rent.common.dto.order.resquest.ChannelUserOrdersReqDto;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.order.ChannelUserOrdersDao;
import com.rent.mapper.order.ChannelUserOrdersMapper;
import com.rent.model.order.ChannelUserOrders;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ChannelSplitBillDao
 *
 * @author xiaotong
 * @Date 2020-08-11 09:59
 */
@Repository
public class ChannelUserOrdersDaoImpl extends AbstractBaseDaoImpl<ChannelUserOrders, ChannelUserOrdersMapper> implements ChannelUserOrdersDao {

    @Override
    public ChannelUserOrders getByOrderId(String orderId) {
        return getOne(new QueryWrapper<ChannelUserOrders>().eq("order_id",orderId).last("limit 1"));
    }

    @Override
    public List<ChannelOrdersExportDto> channelRentOrder(ChannelUserOrdersReqDto request) {
        return baseMapper.channelRentOrder(request);
    }
}
