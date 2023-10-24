package com.rent.dao.order.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.constant.SplitBillRecordConstant;
import com.rent.common.dto.export.AccountPeriodBuyOutDto;
import com.rent.common.dto.export.AccountPeriodRentDto;
import com.rent.common.dto.order.AccountPeriodItemReqDto;
import com.rent.common.util.StringUtil;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.order.SplitBillDao;
import com.rent.mapper.order.SplitBillMapper;
import com.rent.model.order.SplitBill;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * SplitBillDao
 *
 * @author zhao
 * @Date 2020-08-11 09:59
 */
@Repository
public class SplitBillDaoImpl extends AbstractBaseDaoImpl<SplitBill, SplitBillMapper> implements SplitBillDao{

    @Override
    public Page<SplitBill> queryPage(String type, Integer pageNumber, Integer pageSize, List<String> orderIdList, List<String> shopIdList, List<String> uidList, String status) {
        Page<SplitBill> page = page(new Page<>(pageNumber, pageSize),
                new QueryWrapper<SplitBill>()
                        .in(CollectionUtil.isNotEmpty(orderIdList),"order_id",orderIdList)
                        .in(CollectionUtil.isNotEmpty(shopIdList),"shop_id",shopIdList)
                        .in(CollectionUtil.isNotEmpty(uidList),"uid",uidList)
                        .eq(StringUtil.isNotEmpty(status),"status",status)
                        .eq("type",type)
                        .orderByDesc("create_time")
        );
        return page;
    }

    @Override
    public Page<SplitBill> queryPage(String type, Integer pageNumber, Integer pageSize, Long accountPeriodId) {
        Page<SplitBill> page = page(new Page<>(pageNumber, pageSize),
                new QueryWrapper<SplitBill>()
                        .eq("account_period_id",accountPeriodId)
                        .eq("type",type)
                        .orderByDesc("create_time")
        );
        return page;
    }

    @Override
    public Page<SplitBill> queryPage(String type, AccountPeriodItemReqDto request) {
        Page<SplitBill> page = page(new Page<>(request.getPageNumber(), request.getPageSize()),
                new QueryWrapper<SplitBill>()
                        .eq("type",type)
                        .eq(StringUtils.isNotEmpty(request.getShopId()),"shop_id",request.getShopId())
                        .eq(StringUtils.isNotEmpty(request.getOrderId()),"order_id",request.getOrderId())
                        .between(request.getStartTime()!=null,"create_time",request.getStartTime(),request.getEndTime())
                        .orderByDesc("create_time")
        );
        return page;
    }


    @Override
    public List<SplitBill> queryList(String type,List<String> orderIdList, List<String> shopIdList, List<String> uidList, String status) {
        return list( new QueryWrapper<SplitBill>()
                .in(CollectionUtil.isNotEmpty(orderIdList),"order_id",orderIdList)
                .in(CollectionUtil.isNotEmpty(shopIdList),"shop_id",shopIdList)
                .in(CollectionUtil.isNotEmpty(uidList),"uid",uidList)
                .eq(StringUtil.isNotEmpty(status),"status",status)
                .eq("type",type)
                .orderByDesc("create_time"));
    }

    @Override
    public SplitBill getByOrderId(String orderId,Integer period ){
        return getOne(new QueryWrapper<SplitBill>().eq("order_id",orderId).eq(period!=null,"period",period));
    }

    @Override
    public List<SplitBill> getByOrderIdList(String orderId) {
        return list(new QueryWrapper<SplitBill>().eq("order_id",orderId));
    }

    @Override
    public List<SplitBill> getDealingList() {
        return list(new QueryWrapper<SplitBill>().eq("status", SplitBillRecordConstant.STATUS_DEALING));
    }

    @Override
    public void updateSettled(Long accountPeriodId) {
        SplitBill updateModel = new SplitBill();
        updateModel.setStatus(SplitBillRecordConstant.STATUS_SETTLED);
        update(updateModel,new QueryWrapper<SplitBill>().eq("account_period_id",accountPeriodId));
    }

    @Override
    public List<AccountPeriodBuyOutDto> buyOutExport(Long accountPeriodId) {
        return baseMapper.buyOutExport(accountPeriodId);
    }

    @Override
    public List<AccountPeriodRentDto> rentExport(Long accountPeriodId) {
        return baseMapper.rentExport(accountPeriodId);
    }
}
