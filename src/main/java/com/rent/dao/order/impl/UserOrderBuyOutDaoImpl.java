package com.rent.dao.order.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.common.dto.backstage.ExportBuyOutOrderReq;
import com.rent.common.dto.export.BuyOutOrderExportDto;
import com.rent.common.enums.order.EnumOrderBuyOutStatus;
import com.rent.common.util.StringUtil;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.order.UserOrderBuyOutDao;
import com.rent.mapper.order.UserOrderBuyOutMapper;
import com.rent.model.order.UserOrderBuyOut;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * UserOrderBuyOutDao
 *
 * @author xiaoyao
 * @Date 2020-06-23 14:50
 */
@Repository
public class UserOrderBuyOutDaoImpl extends AbstractBaseDaoImpl<UserOrderBuyOut, UserOrderBuyOutMapper>
    implements UserOrderBuyOutDao {

    @Override
    public UserOrderBuyOut selectOneByOrderId(String orderId) {
        return getOne(new QueryWrapper<>(UserOrderBuyOut.builder()
            .orderId(orderId)
            .build()).orderByDesc("id").last("limit 1"), false);
    }

    @Override
    public UserOrderBuyOut selectOneByOrderIdAndStatus(String orderId, EnumOrderBuyOutStatus status) {
        return getOne(new QueryWrapper<>(UserOrderBuyOut.builder()
            .orderId(orderId)
            .state(status)
            .build()), false);
    }

    @Override
    public UserOrderBuyOut selectOneByBuyOutOrderId(String buyOutOrderId) {
        return getOne(new QueryWrapper<>(UserOrderBuyOut.builder()
            .buyOutOrderId(buyOutOrderId)
            .build()));
    }

    @Override
    public List<UserOrderBuyOut> queryUnSplitList() {
        return list(new QueryWrapper<UserOrderBuyOut>().eq("state",EnumOrderBuyOutStatus.FINISH)
                .isNull("delete_time").isNull("split_bill_time"));
    }

    @Override
    public Boolean updateStateSplitBill(Long id) {
        UserOrderBuyOut userOrderBuyOut = new UserOrderBuyOut().setId(id).setSplitBillTime(new Date());
        updateById(userOrderBuyOut);
        return Boolean.TRUE;
    }

    @Override
    public List<String> getOrderIdList(Date begin, Date end, String status) {
        List<UserOrderBuyOut> userOrderBuyOutList = list(new QueryWrapper<UserOrderBuyOut>()
                .select("buy_out_order_id")
                .between(begin!=null && end != null,"create_time",begin,end)
                .eq(StringUtil.isNotEmpty(status),"state",status)
        );
        return userOrderBuyOutList.stream().map(UserOrderBuyOut::getBuyOutOrderId).collect(Collectors.toList());
    }

    @Override
    public List<BuyOutOrderExportDto> getBuyOutOrder(ExportBuyOutOrderReq req) {
        return baseMapper.getBuyOutOrder(req);
    }
}
